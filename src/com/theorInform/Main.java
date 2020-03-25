package com.theorInform;

import java.io.*;
import java.util.*;

public class Main {

    private static final int M = 3;
    static String dirNameF1 = "resource\\file\\F1.txt";
    static String dirNameF2 = "resource\\file\\F2.txt";
    static String dirNameF3 = "resource\\file\\F3.txt";

    public static void main(String[] args) throws IOException {

        files();
        //labThree(false);
        //labFour(true);
        //HaffmanCode hfmnCde = new HaffmanCode(dirNameF3, true);
        //decodeFile(hfmnCde.getCodeTable(), hfmnCde.getOutDir());

        /*FanoCode fnoCde = new FanoCode(dirNameF3, true);
        fnoCde.outCodes();
        decodeFile(fnoCde.getCodeTable(), fnoCde.getOutDir());*/

        ShenonCode snnCde = new ShenonCode(dirNameF2, false);
        snnCde.outCodes();
        System.out.print(snnCde.getCodeTable());
        //Exit((HashMap<String, Float>) snnCde.getCodeTable(), snnCde.getOutDir());




    }

    public static void decodeFile(Map<String, Float> codeMap, String directoryFile) throws IOException {
        ArrayList<Float> verArr = new ArrayList<>(codeMap.values());
        ArrayList<Float> qArr = new ArrayList<>();
        Collections.sort(verArr, Collections.reverseOrder());
        float tmpQ = 0;
        String tmpStr = "";
        ArrayList<Integer> lArr = new ArrayList<>();
        for (int i = 0; i < verArr.size(); i++) {
            qArr.add(tmpQ);
            lArr.add((int) Math.ceil(-log_2(((float)verArr.get(i)))));
            tmpQ += (float)verArr.get(i);
        }
        System.out.println(qArr);
        System.out.println(lArr);
        for (int k = 0; k < directoryFile.length(); k++) {




        }




//        int ch;
//        String otDir;
//        StringBuffer outDir = new StringBuffer(directoryFile);
//        outDir.delete(outDir.length() - 4, outDir.length());
//        FileReader reader = new FileReader(directoryFile);
//        otDir = outDir.toString() + "_decoded.txt";
//        FileWriter writer = new FileWriter(otDir);
//        String tmpSimphol = "";
//
//
//        while((ch = reader.read()) != -1) {
//            tmpSimphol += (char)ch;
//            //System.out.println(tmpSimphol);
//            for(int k = 0; k < codeMap.values().size(); k++) {
//                if (tmpSimphol.equals(codeMap.values().toArray()[k])) {
//                    writer.write(String.valueOf(codeMap.keySet().toArray()[k]));
//                    tmpSimphol = "";
//                }
//            }
//
//        }
//        writer.flush();
//        writer.close();


    }

    public static void labThree(boolean outCodes) throws IOException {
        HaffmanCode codeFile1 = new HaffmanCode(dirNameF1, false);
        HaffmanCode codeFile2 = new HaffmanCode(dirNameF2, false);
        HaffmanCode codeFile3Rus = new HaffmanCode(dirNameF3, true);

        //Вывод таблицы
        System.out.print("Метод кодирования \t\t Название текста \t\t Оценка избыточности \t\t Оценка энтропии \t\t\t Оценка энтропии \t\t\t Оценка энтропии \n");
        System.out.print(" \t\t\t \t\t\t \t\t\t \t\t\t кодирования \t\t\t\t выходной посл-ти (1) \t\t выходной посл-ти (2) \t\t выходной посл-ти (3)\n");
        System.out.print("Метод Хаффмана \t\t\t F1 \t\t\t\t\t ");
        System.out.printf("%.8f", codeFile1.getMidLenCodeWord() - forSimphol(codeFile1.getOutDir(), 1, false));
        System.out.printf("%28.8f", forSimphol(codeFile1.getOutDir(), 1, false));
        System.out.printf("%28.8f",  forSimphol(codeFile1.getOutDir(), 2, false));
        System.out.printf("%28.8f", forSimphol(codeFile1.getOutDir(), 3, false));
        System.out.println("");

        System.out.print("\t\t\t \t\t\t F2 \t\t\t\t\t ");
        System.out.printf("%.8f", codeFile2.getMidLenCodeWord() - forSimphol(codeFile2.getOutDir(), 1, false));
        System.out.printf("%28.8f", forSimphol(codeFile2.getOutDir(), 1, false));
        System.out.printf("%28.8f",  forSimphol(codeFile2.getOutDir(), 2, false));
        System.out.printf("%28.8f", forSimphol(codeFile2.getOutDir(), 3, false));
        System.out.println("");

        System.out.print("\t\t\t \t\t\t Русский язык \t\t\t ");
        System.out.printf("%.8f", codeFile3Rus.getMidLenCodeWord() - 2.25);
        System.out.printf("%28.8f", forSimphol(codeFile3Rus.getOutDir(), 1, false));
        System.out.printf("%28.8f",  forSimphol(codeFile3Rus.getOutDir(), 2, false));
        System.out.printf("%28.8f", forSimphol(codeFile3Rus.getOutDir(), 3, false));
        System.out.println("");

        if (outCodes == true) {
            //Вывод кодов
            System.out.println("первый файл");
            codeFile1.outCodes();
            System.out.println("второй файл");
            codeFile2.outCodes();
            System.out.println("Третий файл");
            codeFile3Rus.outCodes();
        }
    }

    public static void labFour(boolean outCodes) throws IOException {
        FanoCode codeFile1 = new FanoCode(dirNameF1, false);
        FanoCode codeFile2 = new FanoCode(dirNameF2, false);
        FanoCode codeFile3Rus = new FanoCode(dirNameF3, true);

        //Вывод таблицы

        System.out.print("Метод Фано \t\t\t\t F1 \t\t\t\t\t ");
        System.out.printf("%.8f", codeFile1.getMidLenCodeWord() - forSimphol(codeFile1.getOutDir(), 1, false));
        System.out.printf("%28.8f", forSimphol(codeFile1.getOutDir(), 1, false));
        System.out.printf("%28.8f",  forSimphol(codeFile1.getOutDir(), 2, false));
        System.out.printf("%28.8f", forSimphol(codeFile1.getOutDir(), 3, false));
        System.out.println("");

        System.out.print("\t\t\t \t\t\t F2 \t\t\t\t\t ");
        System.out.printf("%.8f", codeFile2.getMidLenCodeWord() - forSimphol(codeFile2.getOutDir(), 1, false));
        System.out.printf("%28.8f", forSimphol(codeFile2.getOutDir(), 1, false));
        System.out.printf("%28.8f",  forSimphol(codeFile2.getOutDir(), 2, false));
        System.out.printf("%28.8f", forSimphol(codeFile2.getOutDir(), 3, false));
        System.out.println("");

        System.out.print("\t\t\t \t\t\t Русский язык \t\t\t ");
        System.out.printf("%.8f", codeFile3Rus.getMidLenCodeWord() - 2.25);
        System.out.printf("%28.8f", forSimphol(codeFile3Rus.getOutDir(), 1, false));
        System.out.printf("%28.8f",  forSimphol(codeFile3Rus.getOutDir(), 2, false));
        System.out.printf("%28.8f", forSimphol(codeFile3Rus.getOutDir(), 3, false));
        System.out.println("");
        if (outCodes == true) {
            //Вывод кодов
            System.out.println("первый файл");
            codeFile1.outCodes();
            System.out.println("второй файл");
            codeFile2.outCodes();
            System.out.println("Третий файл");
            codeFile3Rus.outCodes();
        }
    }


    private static  void Exit (HashMap<String, Float> map, String directoryFile) throws IOException {
        FileInputStream inFile = new FileInputStream(directoryFile);
        byte[] strng = new byte[inFile.available()];
        inFile.read(strng);
        String str = new String(strng);

        String otDir;
        StringBuffer outDir = new StringBuffer(directoryFile);
        outDir.delete(outDir.length() - 4, outDir.length());
        otDir = outDir.toString() + "_decoded.txt";
        FileWriter writer = new FileWriter(otDir);

        String tmpOutString = "";



        ArrayList <Float> arr = new ArrayList<>();
        ArrayList <Float> Q = new ArrayList<>();
        ArrayList <Integer> L = new ArrayList<>();
        for (Map.Entry entry: map.entrySet()) {
            arr.add((float)entry.getValue());
        }
        arr.sort(Collections.reverseOrder());
        Q.add(0f);
        for (float a: arr) {
            Q.add(Q.get(Q.size() - 1) + a);
            L.add((int)Math.ceil(-log_2(a)));
        }
        System.out.println(Q);
        System.out.println(L);
        //String str = "01000110100"; // cbcab
        for (int i = 0; i < str.length(); i++) {
            float p = 0.5f, min = 0f, max = 1f;
            ArrayList <Float> Q_tmp = new ArrayList<>(Q);
            boolean flag = true;
            int simbol = 1;
            while (flag) {
                System.out.println(i);
                ArrayList<Float> tmp = new ArrayList<>();
                if (str.substring(i, i + 1).equals("0")) {
                    for (float a : Q_tmp) {
                        if (a < p) {
                            tmp.add(a);
                        }
                    }
                    max = p;
                    p = max + min;
                    p /= 2;
                } else {
                    for (float a : Q_tmp) {
                        if (a >= p) {
                            tmp.add(a);
                        }
                    }
                    min = p;
                    p = max - min;
                    p /= 2;
                    p = max - p;
                }

                Q_tmp.clear();
                Q_tmp.addAll(tmp);
                System.out.println(tmp);
                if (Q_tmp.size() == 1) {
                    /////
                    for(int k = 0; k < map.values().size(); k++) {
                        System.out.println("ver: " +  arr.get(Q.indexOf(Q_tmp.get(0))) + "verpizt: " + map.values().toArray()[k]);;
                        if ((float)arr.get(Q.indexOf(Q_tmp.get(0))) == (float)map.values().toArray()[k]) {
                            tmpOutString += String.valueOf(map.keySet().toArray()[k]);
                            writer.write(String.valueOf(map.keySet().toArray()[k]));
                        }
                    }



                    while (true) {
                        if (simbol != L.get(Q.indexOf(Q_tmp.get(0)))) {
                            simbol++;
                            i++;
                        } else {
                            break;
                        }
                    }
                    flag = false;
                    System.out.println(Q_tmp.get(0));
                } else {
                    simbol++;
                    i++;
                }
            }

        }
        System.out.println(tmpOutString + "  " + tmpOutString.length());

        writer.flush();
        writer.close();


    }


    /*static void lab1() {
        String dirNameF1 = "resource\\file\\F1.txt";
        String dirNameF2 = "resource\\file\\F2.txt";
        String dirNameF3 = "resource\\file\\F3.txt";
        double teorF1OneNumb, teorF2OneNumbm, teorF1TwoNumb, teorF2TwoNumb;
        teorF1OneNumb = -(3 * (0.3333 * log_2(2, 0.3333)));
        teorF2OneNumbm = -1 * (2 * (0.2 * log_2(2, 0.2)) + 0.6 * log_2(2, 0.6));


        System.out.print("       \t" + "1 символ " + "\t\t\t " + "2 символа \t\t\t" + "Теоретическое" + "\t \n");
        System.out.print("Файл 1 \t" + forSimphol(dirNameF1, 1) + "\t " + forSimphol(dirNameF1, 2) + "\t" + teorF1OneNumb + "\t\n");
        System.out.print("Файл 2 \t" + forSimphol(dirNameF2, 1) + "\t " + forSimphol(dirNameF2, 2) + "\t" + teorF2OneNumbm + "\t \n");
        System.out.println("Русский текст \t\t\t\t\t\t\t\t\t Максимальное");
        System.out.print("Файл 3 \t" + forOneRusSimphol(dirNameF3) + "\t " + forTwoRusSimphol(dirNameF3) + "\t " + log_2(2, 33) + "\t \n");
        System.out.println("Алфавит 256 символов");
        System.out.print("Файл 3 \t" + forOneSimphol(dirNameF3, 3) + "\t " + forTwoSimphol(dirNameF3) + "\t \n");
    }*/

    static double forSimphol(String fileDir, int numbSimph, boolean rus) throws IOException {
        FileInputStream inFile = new FileInputStream(fileDir);
        byte[] str = new byte[inFile.available()];
        inFile.read(str);
        String array = new String(str);
        String tmpSimph = "";
        if (rus == true) {
            array = array.toLowerCase();
            array = array.replaceAll("[^а-яё\\s]", ".");
            array = array.replace("ё", "е");
            array = array.replace("ь", "ъ");
        }
        ArrayList<String> simphols = new ArrayList<>();

        for (int i = 0; i < array.length() - numbSimph; i++) {
            for (int j = 0; j < numbSimph; j++) {
                tmpSimph += String.valueOf(array.charAt(i + j));
            }
            //System.out.println(tmpSimph);
            if (!simphols.contains(tmpSimph)) {
                simphols.add(tmpSimph);
            }
            tmpSimph = "";
        }


        //System.out.println(simphols);

        double[] count_simphols = new double[simphols.size()];

        for (int i = 0; i < count_simphols.length; i++) {
            count_simphols[i] = 0;
        }
        for (int i = 0; i < simphols.size(); i++) {
            for (int j = 0; j < array.length() - numbSimph; j++) {
                for (int k = 0; k < numbSimph; k++) {
                    tmpSimph += String.valueOf(array.charAt(k + j));
                }
                if (simphols.get(i).equals(tmpSimph)) {
                    count_simphols[i]++;
                }

                tmpSimph = "";
            }
        }

        /*for (double i : count_simphols) {
            System.out.println(i);
        }*/

        double allElems = 0;
        for (int i = 0; i < count_simphols.length; i++) {
            allElems += count_simphols[i];
        }
        double result = 0;

        for (int i = 0; i < count_simphols.length; i++) {
            //System.out.println("sssss " + (count_simphols[i] / allElems));
            result += (count_simphols[i] / allElems) * log_2(allElems / count_simphols[i]);
        }

        return result / numbSimph;
    }

    public static void files() throws IOException {
        Random random = new Random();
        int i = 0;
        int ran = 0;
        File Dir = new File("resource\\file");
        if (!Dir.exists()) {
            try {
                Dir.mkdir();
            } catch (SecurityException var10) {

            }
        }
        File F1 = new File("resource\\file", "F1.txt");
        File F2 = new File("resource\\file", "F2.txt");
        do {
            ran = random.nextInt(3);
            switch (ran) {
                case 0:
                    try (FileWriter writer = new FileWriter(F1, true)) {
                        writer.append("a");
                        writer.flush();
                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                    break;
                case 1:
                    try (FileWriter writer = new FileWriter(F1, true)) {
                        writer.append("b");
                        writer.flush();
                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                    break;
                case 2:
                    try (FileWriter writer = new FileWriter(F1, true)) {
                        writer.append("c");
                        writer.flush();
                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                    break;
                default:
                    break;
            }
            //System.out.println(F1.length());
        } while (F1.length() < 10240);

        do {
            ran = random.nextInt(100);
            if (ran <= 19) {
                try (FileWriter writer = new FileWriter(F2, true)) {
                    writer.append("a");
                    writer.flush();
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }
            } else if (ran <= 39) {
                try (FileWriter writer = new FileWriter(F2, true)) {
                    writer.append("b");
                    writer.flush();
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }
            } else {
                try (FileWriter writer = new FileWriter(F2, true)) {
                    writer.append("c");
                    writer.flush();
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }
            }
        } while (F2.length() < 10240);
    }

    static double log_2 (double number) {
        return Math.log10(number) / Math.log10(2);
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
        System.out.println(S_R);
        while (S_L >= S_R) {
            m = m - 1;
            S_L = S_L - arr.get(m);
            S_R = S_R + arr.get(m);
        }
        return m;
    }

}
