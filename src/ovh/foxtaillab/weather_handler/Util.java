/*mapapogoda.pl Common utilities
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

import java.util.HashMap;

public class Util {
    public static final int PORT = 2052;
    public static final String PATH = "/data/";
    public static final int MAX_CONNECTIONS = 128;
    public static final int DNL_INTERVAL = 3600;
    public static final String API_URL = "https://danepubliczne.imgw.pl/api/data/";
    public static final String[] DATA_TYPES = {"synop", "meteo", "hydro", "warningsmeteo", "warningshydro"};
    public static final int DATA_TYPE_AMT = DATA_TYPES.length;

    public static String[] data = new String[DATA_TYPE_AMT];
    public static String apiDataCollective = "{}";
    public static String iotData = "[{}]";
    public static HashMap<String, String> iotDataSet = new HashMap<>();
    public static String dataCollective = "{}";

    public static void updateDataCollective() {
        Util.dataCollective = Util.apiDataCollective;
        Util.dataCollective = "{\"synop\":[" + Util.iotData.split("\\[|\\]")[1] + "," + Util.dataCollective.split("\\{\"synop\":\\[")[1];
    }
}
