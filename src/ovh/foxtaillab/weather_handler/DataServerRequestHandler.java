/*mapapogoda.pl IOT/Outgoing Data Service Handler
    Copyright (C) 2024  Jakub "kreperu" Sowula
    kripkrep@gmail.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.*/

package ovh.foxtaillab.weather_handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DataServerRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange == null) return;
        String method = exchange.getRequestMethod();

        //On GET -> respond with the dataCollective as JSON
        if(method.equals("GET")) {
            exchange.getResponseHeaders().set("content-type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200, Util.dataCollective.length());
            OutputStream os = exchange.getResponseBody();
            os.write(Util.dataCollective.getBytes(StandardCharsets.UTF_8));
            //System.out.println(Util.apiDataCollective);
            os.close();
            exchange.close();
        // On POST -> get data from request body,
        //  replace old data with new data from same station,
        //  update dataCollective
        } else if(method.equals("POST")) {
            // 200 OK
            exchange.sendResponseHeaders(200, 0);
            // 1
            InputStream is = exchange.getRequestBody();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            is.transferTo(baos);
            String dataStr = baos.toString(StandardCharsets.UTF_8);
            if(dataStr.split("\\{\"id_stacji[^}]+stacja[^}]+data_pomiaru[^}]+godzina_pomiaru[^}]+temperatura[^}]+predkosc_wiatru[^}]+kierunek_wiatru[^}]+wilgotnosc_wzgledna[^}]+suma_opadu[^}]+cisnienie[^}]+}").length == 0) {
                Util.iotDataSet.put(dataStr.split("\\{\"id_stacji\":\"|\"")[1], dataStr);
            }
            baos.close();
            is.close();
            exchange.close();
            // 2
            Util.iotData = "[";
            for(String data : Util.iotDataSet.values()) {
                Util.iotData += data + ",";
            }
            char[] iotChArr = Util.iotData.toCharArray();
            iotChArr[Util.iotData.length()-1] = ']';
            Util.iotData = String.copyValueOf(iotChArr);
            //System.out.println(Util.iotData);
            // 3
            DataUpdateTask.deprecateIOTData();
            Util.updateDataCollective();
        // Ignore other methods ¯\_(ツ)_/¯
        } else {
            System.out.println("Unsupported Method!");
            exchange.close();
        }
    }
}
