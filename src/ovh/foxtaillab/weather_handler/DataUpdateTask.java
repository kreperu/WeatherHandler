/*IMGW API request task
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

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

public class DataUpdateTask implements Runnable {
    @Override
    public void run() {
        Util.apiDataCollective = "{";
        try {
            for(int i = 0; i < Util.DATA_TYPE_AMT; i++) {
                URI uri = new URI(Util.API_URL + Util.DATA_TYPES[i]);
                HttpRequest rq = HttpRequest.newBuilder().uri(uri).GET().timeout(Duration.ofSeconds(5)).build();
                HttpClient client = HttpClient.newHttpClient();
                Util.data[i] = client.send(rq, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)).body();
                Util.apiDataCollective += "\"" + Util.DATA_TYPES[i] + "\":" + Util.data[i] + (i < (Util.DATA_TYPE_AMT - 1) ? ", " : "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Util.apiDataCollective += "}";
        //deprecateIOTData();
        Util.updateDataCollective();
    }


    //If Data Point is older than 1 hour -> remove it
    public static void deprecateIOTData() {
        for(Map.Entry<String, String> data : Util.iotDataSet.entrySet()) {
            String[] date = data.getValue().split("\"data_pomiaru\":\"(?=\\d{1,2})|-|\",\"godzina_pomiaru\":\"");
            int hour = Integer.parseInt(data.getValue().split("\"godzina_pomiaru\":\"(?=\\d{1,2})|\",\"temperatura\":\"")[1]);
            if(LocalDateTime.of(Integer.parseInt(date[1]), Integer.parseInt(date[2]), Integer.parseInt(date[3]), hour, 0).isBefore(LocalDateTime.now().minusHours(1))) {
                Util.iotDataSet.remove(data.getKey());
            }
        }
    }
}
