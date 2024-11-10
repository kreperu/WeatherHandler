package ovh.foxtaillab.weather_handler;

import java.util.HashMap;
import java.util.HashSet;

public class Util {
    public static final int PORT = 8000;
    public static final String PATH = "/data/";
    public static final int MAX_CONNECTIONS = 64;
    public static final int DNL_INTERVAL = 3600;
    public static final String SERVER_URL = "89.22.239.123";
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
