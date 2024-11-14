/*mapapogoda.pl Weather Service Handler Startpoint
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

import ovh.foxtaillab.corellator.CorellationsUpdateTask;
import ovh.foxtaillab.weather_handler.*;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Weather Handler  Copyright (C) 2024  Jakub \"kreperu\" Sowula (kripkrep@gmail.com)\n" +
                "    This program comes with ABSOLUTELY NO WARRANTY;\n" +
                "    This is free software, and you are welcome to redistribute it\n" +
                "    under certain conditions;");
        ScheduledExecutorService ses = new ScheduledThreadPoolExecutor(2);
        ses.scheduleAtFixedRate(new DataUpdateTask(), 0, Util.DNL_INTERVAL, TimeUnit.SECONDS);
        ses.scheduleAtFixedRate(new CorellationsUpdateTask(), 10, Util.DNL_INTERVAL, TimeUnit.SECONDS);
        DataServer ds = new DataServer(Util.PORT, Util.MAX_CONNECTIONS, Util.PATH);
        ds.start();
    }
}
