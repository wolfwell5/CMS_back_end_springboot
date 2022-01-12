package com.springboot.myspringboot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class mainTest {

    private static List<CarNumberPeriod> idx2IsDigitalList = new ArrayList<>();
    private static List<CarNumberPeriod> idx3IsDigitalList = new ArrayList<>();
    private static int carNumberLengh = 6;

    public static void main(String[] args) {
        printSeparateLine();
        readFileAndGenerateNumbers();
        printSeparateLine();
    }

    private static void appendValueWithSuffixSpace(BufferedWriter writer, String str, String separator, int separatorPrintTimes) throws IOException {
        writer.append(str);
        for (int i = 0; i < separatorPrintTimes; i++) {
            writer.append(separator);
        }
    }

    private static void printSeparateLine() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /***
     *  !! Notice the idx is from 0,
     *  not start from 1
     * @param str
     * @param idx
     * @return
     */
    private static boolean judgeTheGivenIdxIsNumber(String str, int idx) {
        return Character.isDigit(str.charAt(idx));
    }

    private static void readFileAndGenerateNumbers() {

        try {
            String jsonStr = "";
            File jsonFile = new File("/Users/xuewei.wang/Library/Application Support/JetBrains/IntelliJIdea2020.3/scratches/Session/汽车 Car/Select Car Number Data/carNumberDataFormated.json");
            System.out.println("len is : " + jsonFile.getName());

            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");

            int ch;
            StringBuilder sb = new StringBuilder();

            while ((ch = fileReader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();

            jsonStr = sb.toString();

            ObjectMapper objectMapper = new ObjectMapper();
            List<CarNumberPeriod> carNumberPeriods = objectMapper.readValue(jsonStr, new TypeReference<List<CarNumberPeriod>>() {
            });

            writeStrToFile("path", carNumberPeriods);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeStrToFile(String path, List<CarNumberPeriod> res) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("carNumbers"));

        appendValueWithSuffixSpace(writer, "publishDate", "\t", 1);
        writer.append("\t\t");
        appendValueWithSuffixSpace(writer, "start-number", "\t", 1);
        writer.append("end-number");
        writer.append("\n");

        partitionCarNumberAndAdd2List(res);
        printCarNumbersAsTable(res, writer);

        writer.append("\nidx2IsDigitalCount is: " + idx2IsDigitalList.size() + "\n");
        writer.append(idx2IsDigitalList.toString());
        writer.append("\n");
        generateSuperCarNumber(writer, idx3IsDigitalList);

        writer.append("\n------------------------------------------------------------------------------------\n");
        writer.append("idx3IsDigitalCount is: " + idx3IsDigitalList.size() + "\n");
        printCarNumbersAsTable(idx3IsDigitalList, writer);

        writer.close();
    }

    private static void partitionCarNumberAndAdd2List(List<CarNumberPeriod> res) {

        for (int i = 0; i < res.size(); i++) {
            CarNumberPeriod carNumberPeriodItem = res.get(i);
            String periodItem = carNumberPeriodItem.getSubhd();
            String[] startEndNubmer = periodItem.split("~");
            String startNumber = startEndNubmer[0];

            if (judgeTheGivenIdxIsNumber(startNumber, 2)) idx2IsDigitalList.add(carNumberPeriodItem);
            if (judgeTheGivenIdxIsNumber(startNumber, 3)) idx3IsDigitalList.add(carNumberPeriodItem);
        }
    }

    private static void printCarNumbersAsTable(List<CarNumberPeriod> res, BufferedWriter writer) throws IOException {
        writer.append("\n");

        for (int i = 0; i < res.size(); i++) {
            CarNumberPeriod carNumberPeriodItem = res.get(i);
            String periodItem = carNumberPeriodItem.getSubhd();
            String publishDate = carNumberPeriodItem.getTfrq();
            String[] startEndNubmer = periodItem.split("~");
            String startNumber = startEndNubmer[0];
            String endNumber = startEndNubmer[1];

            appendValueWithSuffixSpace(writer, publishDate, "\t", 1);
            appendValueWithSuffixSpace(writer, startNumber, "\t", 1);

            appendValueWithSuffixSpace(writer, "", "\t", 2);
            appendValueWithSuffixSpace(writer, endNumber, "\t", 1);
            appendValueWithSuffixSpace(writer, "", "\n", 1);
        }
    }

    private static void generateSuperCarNumber(BufferedWriter writer, List<CarNumberPeriod> idx3IsDigitalList) {

        idx3IsDigitalList.forEach((item) -> {
            String tfrq = item.getTfrq();
            String subhd = item.getSubhd();
//            int number = item;
        });

    }

}
