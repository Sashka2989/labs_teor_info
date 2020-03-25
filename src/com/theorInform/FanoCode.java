package com.theorInform;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FanoCode {

    private String otDir;
    private ArrayList<String> simphols = new ArrayList<>();
    double[] count_simphols;
    private Map<String, String> map_code;
    private HashMap <String, Float> mainMap;

    FanoCode(String fileDir, boolean rus) throws IOException {
        int ch;
        FileReader reader;
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
        //next step

        ArrayList<Float> arr = new ArrayList<>(mainMap.values());
        Collections.sort(arr);

        Map<Float, String> tt = new HashMap<>();
        for (float p: arr) {
            tt.put(p, "");
        }


        Fano(0, arr.size() - 1, arr, tt);

        map_code = new HashMap<>();
        for (Map.Entry entry1 : mainMap.entrySet()) {
            for (Map.Entry entry2 : tt.entrySet()) {
                if ((float)entry2.getKey() == (float)entry1.getValue()) {
                    map_code.put(entry1.getKey().toString(), entry2.getValue().toString());
                    break;
                }
            }
        }

        StringBuffer outDir = new StringBuffer(fileDir);
        outDir.delete(outDir.length() - 4, outDir.length());

        FileWriter writer;
        if (rus == true) {
            otDir = outDir.toString() + "_coded_Fano_Rus.txt";
            writer = new FileWriter(otDir);
        } else {
            otDir = outDir.toString() + "_coded_Fano.txt";
            writer = new FileWriter(otDir);
        }

        reader = new FileReader(fileDir);
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

    private void Fano(int L, int R, ArrayList<Float> arr, Map<Float, String> map) {
        int m;
        if (L < R) {
            m = Med(L, R, arr);
            for (int i = L; i <= R; i++) {
                if (i <= m) {
                    map.replace(arr.get(i), map.get(arr.get(i)) + "0");
                } else {
                    map.replace(arr.get(i), map.get(arr.get(i)) + "1");
                }
            }
            Fano(L, m, arr, map);
            Fano(m + 1, R, arr, map);
        }
    }

    private int Med(int L, int R, ArrayList<Float> arr) {
        int m;
        float S_L = 0;
        for (int i = L; i < R; i++) {
            S_L = S_L + arr.get(i);
        }
        float S_R = arr.get(R);
        m = R;
        if (S_L < S_R) {
            m = m - 1;
        }
        while (S_L >= S_R) {
            m = m - 1;
            S_L = S_L - arr.get(m);
            S_R = S_R + arr.get(m);
        }
        return m;
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

    double getMidLenCodeWord() {
        double result = 0;
        for (Map.Entry<String, Float> veroyatn : mainMap.entrySet()) {
            for (Map.Entry<String, String> code : map_code.entrySet()) {
                if(veroyatn.getKey().equals(code.getKey())) {
                    result += veroyatn.getValue() * code.getValue().length();
                }

            }
        }
        return  result;
    }

    public Map<String, String> getCodeTable() {
        return map_code;
    }

}
