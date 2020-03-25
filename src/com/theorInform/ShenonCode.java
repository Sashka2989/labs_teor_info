package com.theorInform;

import java.io.*;
import java.util.*;

public class ShenonCode {
    HashMap<String, Float> mainMap;
    private String otDir;
    private Map<Float, String> tt;
    private Map<String, String> map_code;
    private Map<String, String> codedTable;

    ShenonCode(String fileDir, boolean rus) throws IOException {
        ArrayList<String> simphols = new ArrayList<>();
        codedTable = new HashMap<>();
        double[] count_simphols;
        int ch;
        FileReader reader = new FileReader(fileDir);
        FileInputStream inFile = new FileInputStream(fileDir);
        byte[] str = new byte[inFile.available()];
        inFile.read(str);
        String array = new String(str);
        if (rus == true) {
            array = array.toLowerCase();
            array = array.replaceAll("[^а-яё\\s]", ".");
            array = array.replace("ё", "е");
            array = array.replace("ь", "ъ");
        }


        for (int i = 0; i < array.length(); i++) {
            if (!simphols.contains(String.valueOf((array.charAt(i))))) {
                simphols.add(String.valueOf(array.charAt(i)));
            }
        }
        //System.out.println(simphols);
        count_simphols = new double[simphols.size()];

        for (int i = 0; i < count_simphols.length; i++) {
            count_simphols[i] = 0;
        }
        for (int i = 0; i < simphols.size(); i++) {
            for (int j = 0; j < array.length(); j++) {
                if (simphols.get(i).equals(String.valueOf(array.charAt(j)))) {
                    count_simphols[i]++;
                }
            }
        }
        double allElems = 0;
        for (int i = 0; i < count_simphols.length; i++) {
            allElems += count_simphols[i];
        }
        for (int i = 0; i < count_simphols.length; i++) {
            count_simphols[i] /= allElems;
        }
        mainMap = new HashMap<>();
        for(int i = 0; i < simphols.size(); i++) {
            mainMap.put(simphols.get(i), (float)count_simphols[i]);
        }

        //code

        ArrayList<Float> arr = new ArrayList<>(mainMap.values());
        Collections.sort(arr, Collections.reverseOrder());



        tt = new HashMap<>();
        for (float p: arr) {
            tt.put(p, "");
        }

        tt = Test(arr);
        //System.out.println(tt);


        map_code = new HashMap<>();
        for (Map.Entry entry1 : mainMap.entrySet()) {
            for (Map.Entry entry2 : tt.entrySet()) {
                if ((float)entry2.getKey() == (float)entry1.getValue()) {
                    map_code.put(entry1.getKey().toString(), entry2.getValue().toString());
                    break;
                }
            }
        }
        //System.out.println(map_code);

        StringBuffer outDir = new StringBuffer(fileDir);
        outDir.delete(outDir.length() - 4, outDir.length());
        FileWriter writer;
        if (rus == true) {
            otDir = outDir.toString() + "_coded_Shenon_Rus.txt";
            writer = new FileWriter(otDir);
        } else {
            otDir = outDir.toString() + "_coded_Shenon.txt";
            writer = new FileWriter(otDir);
        }

        while((ch = reader.read()) != -1) {
            if (rus == true) {
                ch = String.valueOf((char)ch).toLowerCase().charAt(0);
                ch = String.valueOf((char)ch).replaceAll("[^а-яё\\s]", ".").charAt(0);
                ch = String.valueOf((char)ch).replace("ё", "е").charAt(0);
                ch = String.valueOf((char)ch).replace("ь", "ъ").charAt(0);
            }
            if (mainMap.containsKey(String.valueOf((char)ch))) {
                writer.write(map_code.get(String.valueOf((char) ch)));
            }
        }
        writer.flush();
        writer.close();

    }

    private static Map<Float, String>  Test(ArrayList<Float> arr)  {
        float i = 0f;
        Map<Float, String> temp = new HashMap<>();

        for (Float entry: arr) {
            int exp = 0;
            int l = 0;
            String str;
            while (true) {
                if (Math.pow(2, exp) - entry <= 0) {
                    l = Math.abs(exp);
                    break;
                }
                exp -= 1;
            }
            str = Bits(i, l);
            i += entry;
            temp.put(entry, str);
        }
        return temp;
    }

    private static String Bits(float i, int l) {
        String str = "";
        float tmp = i;
        for (int j = 0; j < l; j++) {

            tmp = tmp * 2f;
            //System.out.println("tmp do = " + tmp);
            str += String.valueOf(tmp).substring(0, 1);
            tmp = Float.parseFloat(String.valueOf(tmp).substring(1));
            //System.out.println("tmp posle = " + tmp);
        }

        return str;
    }

    public Map<String, Float> getCodeTable() {
        return mainMap;
    }

    public String getOutDir() {
        return otDir;
    }

    public void outCodes() {
        List<String> employeeById = new ArrayList<>(map_code.values());
        Collections.sort(employeeById, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.length() - t1.length();
            }
        });
        for (String nd : employeeById) {
            for(Map.Entry<String, String> entry : map_code.entrySet()) {
                if (entry.getValue().equals(nd)) {
                    System.out.println(entry.getKey() + " : " + entry.getValue());
                }
            }

        }

    }


}
