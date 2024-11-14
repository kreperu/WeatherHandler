/*mapapogoda.pl City-Station Corellator
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
import org.json.JSONStringer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.*;

public class Corellator {
    private final File generated;
    private HashMap<String, Object> operands;

    public Corellator(String inFName, String outFName) {

        try {
            FileInputStream fis = new FileInputStream(inFName);
            String data = new String(fis.readAllBytes());
            operands = (HashMap<String, Object>)(new JSONObject(data).toMap());
        } catch(Exception e) {
            e.printStackTrace();
        }
        generated = new File(outFName);
    }

    public HashMap<String, Integer> corellate(HashSet<String> corellants) {
        HashMap<String, Integer> minima = new HashMap<String, Integer>(operands.size());
        int i = 0;
        for(Map.Entry<String, Object> ent : operands.entrySet()) {
        //Map.Entry<String, Object> ent = Map.entry("Pszów", new String[]{"SL", "50.03994000", "18.39472000"});
            double minD = Integer.MAX_VALUE;
            double d = 0;
            int minEntry = 0;
            int indexCorel = 0;
            for(String elm : corellants) {
                if(elm == null || elm.isEmpty()) continue;
                if(!operands.containsKey(elm))  {
                    System.out.println("Unrecognized city: " + elm);
                    continue;
                }
                //System.out.println(elm);
                double lat = Double.parseDouble(((ArrayList<String>)operands.get(elm)).get(1))-Double.parseDouble(((ArrayList<String>)ent.getValue()).get(1));
                double lon = Double.parseDouble(((ArrayList<String>)operands.get(elm)).get(2))-Double.parseDouble(((ArrayList<String>)ent.getValue()).get(2));
                d = Math.sqrt(lat*lat+lon*lon);
                if(d < minD) {
                    minD = d;
                    minEntry = indexCorel;
                }
                ++indexCorel;
            }
            minima.put(ent.getKey(), minEntry);
            ++i;
            //System.out.println(minEntry);
        }
        return minima;
    }

    public void generateCorellationsFile(HashMap<String, Integer> corellations, boolean webCompatible) {
        try {
            //generated.delete();
            generated.createNewFile();
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            FileWriter wrt = new FileWriter(generated);
            wrt.write((webCompatible ? "var corellations = " : "") + JSONStringer.valueToString(corellations));
            wrt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
