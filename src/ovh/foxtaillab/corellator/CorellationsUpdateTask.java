/*mapapogoda.pl City-Station Corellator Task
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

package ovh.foxtaillab.corellator;

import org.json.JSONObject;
import ovh.foxtaillab.weather_handler.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CorellationsUpdateTask implements Runnable{

    @Override
    public void run() {
        ArrayList<Map<String, String>> data = (ArrayList<Map<String, String>>)(new JSONObject(Util.dataCollective)).toMap().get("synop");
        HashSet<String> stationCities = new HashSet<String>(data.size());
        for(Map<String, String> object : data) stationCities.add(object.get("stacja"));
        HashMap<String, String> cors = Util.DATA_CORELLATOR.corellate(stationCities);
        Util.DATA_CORELLATOR.generateCorellationsFile(cors);
    }
}
