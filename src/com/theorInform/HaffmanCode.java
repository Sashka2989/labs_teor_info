package com.theorInform;

import javax.swing.text.html.parser.Entity;
import java.io.*;
import java.util.*;

public class HaffmanCode {

    static class Node implements Comparable<Node> {
        final float sum;
        String code;

        void buildCode(String code) {
            this.code = code;
        }

        public Node(float sum) {
            this.sum = sum;
        }

        @Override
        public int compareTo(Node o) {
            return Float.compare(sum, o.sum);
        }
    }

    static class InternalNode extends Node{
        Node left;
        Node right;

        @Override
        void buildCode(String code) {
            super.buildCode(code);
            left.buildCode(code + "0");
            right.buildCode(code + "1");
        }

        public InternalNode(Node left, Node right) {
            super(left.sum + right.sum);
            this.left = left;
            this.right = right;
        }
    }

    static class LearfNode extends Node {
        String symbol;

        @Override
        void buildCode(String code) {
            super.buildCode(code);
            //System.out.println(symbol + ": " + code);
        }

        public LearfNode(String symbol, float count) {
            super(count);
            this.symbol = symbol;
        }
    }


    FileReader reader;
    private ArrayList<String> simphols = new ArrayList<>();
    double[] count_simphols;
    int ch;
    private String otDir;
    private HashMap <String, Float> mainMap;
    private Map<String, Node> stringMode;
    private Map<String, String> map_code;

    HaffmanCode(String fileDir, boolean rus) throws IOException {
        map_code = new HashMap<>();
        reader = new FileReader(fileDir);
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

        //create files

        mainMap = new HashMap<>();
        for(int i = 0; i < simphols.size(); i++) {
            mainMap.put(simphols.get(i), (float)count_simphols[i]);
        }

        stringMode = new HashMap<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<String, Float> entry: mainMap.entrySet() ) {
            LearfNode node = new LearfNode(entry.getKey(), entry.getValue());
            stringMode.put(entry.getKey(), node);
            priorityQueue.add(node);
        }
        float sum = 0;
        while (priorityQueue.size() > 1) {
            Node first = priorityQueue.poll();
            Node second = priorityQueue.poll();
            InternalNode node = new InternalNode(first, second);
            sum += node.sum;
            priorityQueue.add(node);
        }


        Node root = priorityQueue.poll();

        assert root != null;

        root.buildCode("");

        StringBuffer outDir = new StringBuffer(fileDir);
        outDir.delete(outDir.length() - 4, outDir.length());
        FileWriter writer_F1;

        if (rus == true) {
            otDir = outDir.toString() + "_coded_haffman_Rus.txt";
            writer_F1 = new FileWriter(otDir);
        } else {
            otDir = outDir.toString() + "_coded_haffman.txt";
            writer_F1 = new FileWriter(otDir);
        }

        reader = new FileReader(fileDir);
        createMapCode();
        while((ch = reader.read()) != -1) {
            if (rus == true) {
                ch = String.valueOf((char)ch).toLowerCase().charAt(0);
                ch = String.valueOf((char)ch).replaceAll("[^а-яё\\s]", ".").charAt(0);
                ch = String.valueOf((char)ch).replace("ё", "е").charAt(0);
                ch = String.valueOf((char)ch).replace("ь", "ъ").charAt(0);
            }
            if (mainMap.containsKey(String.valueOf((char)ch))) {
                writer_F1.write(stringMode.get(String.valueOf((char) ch)).code);
            }
        }
        writer_F1.flush();
        writer_F1.close();

    }

    public String getOutDir() {
        return otDir;
    }

    public double getMidLenCodeWord() {
        double result = 0;
        for (Map.Entry<String, Float> entry: mainMap.entrySet() ) {
            result += stringMode.get( entry.getKey()).code.length() * entry.getValue();
        }

        return  result;
    }

    public void outCodes() {
        List<Node> employeeById = new ArrayList<>(stringMode.values());
        Collections.sort(employeeById, new Comparator<Node>() {
            @Override
            public int compare(Node node, Node t1) {
                return node.code.length() - t1.code.length();
            }
        });
        for (Node nd : employeeById) {
            for (Map.Entry<String, Node> entry: stringMode.entrySet() ) {
                if(entry.getValue().code == nd.code) {
                    System.out.println(entry.getKey() + " : " + entry.getValue().code);
                }
            }
        }
    }

    private void createMapCode() {
        for (Node nd : stringMode.values()) {
            for (Map.Entry<String, Node> entry: stringMode.entrySet() ) {
                if(entry.getValue().code == nd.code) {
                    map_code.put(entry.getKey(), entry.getValue().code);
                }
            }
        }
    }

    public Map<String, String> getCodeTable() {
        return map_code;
    }
}
