import ovh.foxtaillab.weather_handler.*;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService ses = new ScheduledThreadPoolExecutor(1);
        ses.scheduleAtFixedRate(new DataUpdateTask(), 0, Util.DNL_INTERVAL, TimeUnit.SECONDS);
        DataServer ds = new DataServer(Util.PORT, Util.MAX_CONNECTIONS, Util.PATH);
        ds.start();
    }
}
