/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sihomework1.AggQueryTA;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author cool
 */
public class PreProcess {

    static String COMMA_DELIMITER = ",";

    public static void main(String[] args) throws FileNotFoundException, IOException {

        System.out.println("Give path to data directory");
        Scanner sc = new Scanner(System.in);
        String cwd = sc.nextLine();
        System.out.println("name of dataset to preprocess...format .csv");
        String Dsname = sc.next();
        String pathToDataSetFile = cwd + "\\" + Dsname + ".csv";
        //String pathToDataSetFile = "D:\\my courses\\searching n indexing\\2016\\Assignments\\dataset3.csv";
        //String Destination = "D:\\my courses\\searching n indexing\\2016\\Assignments";
        String Destination = cwd;

        /*
         * Invoke method to preprocess data... Destination tells u the dir where
         * processed data will be stored...it is set to same path that usr will
         * give at first
         */
        preProcessData(pathToDataSetFile, Destination);

        System.out.println("processed attributes written in specified working directory..as Sorted Attribute1.csv and Sorted Attribute2.csv");

    }
    
    /*Method to sort the Dataset attribute wise ... Sorted object-attribute set will be wriiten in destination path */

    public static void preProcessData(String pathToDataSetFile, String Destination) throws FileNotFoundException, IOException {

        FileWriter SortedAttributefile1Writer = new FileWriter(Destination + "//SortedAttribute1.csv");
        FileWriter SortedAttributefile2Writer = new FileWriter(Destination + "//SortedAttribute2.csv");
        BufferedReader fileReader;
        fileReader = new BufferedReader(new FileReader(pathToDataSetFile));
        String line;
        int counter = 0;
        String objName;

        ArrayList<ObjectAttribute> ListSortedAttribute1 = new ArrayList<>();
        ArrayList<ObjectAttribute> ListSortedAttribute2 = new ArrayList<>();

        ObjectAttribute at1;
        ObjectAttribute at2;

        while ((line = fileReader.readLine()) != null) {
            objName = "object" + ++counter;
            at1 = new ObjectAttribute();
            at2 = new ObjectAttribute();
            String[] tokens = line.split(COMMA_DELIMITER);
            if (tokens.length > 0) {
                at1.setObjectName(objName);
                at1.setAttribute(Double.parseDouble(tokens[0]));
                at2.setObjectName(objName);
                at2.setAttribute(Double.parseDouble(tokens[1]));

            }
            ListSortedAttribute1.add(at1);
            ListSortedAttribute2.add(at2);
        }
        fileReader.close();

        Collections.sort(ListSortedAttribute1, new Comparator<ObjectAttribute>() {

            @Override
            public int compare(ObjectAttribute o1, ObjectAttribute o2) {
                return o2.getAttribute().compareTo(o1.getAttribute());
            }
        });

        Collections.sort(ListSortedAttribute2, new Comparator<ObjectAttribute>() {

            @Override
            public int compare(ObjectAttribute o1, ObjectAttribute o2) {
                return o2.getAttribute().compareTo(o1.getAttribute());
            }
        });



        for (int i = 0; i < ListSortedAttribute1.size(); i++) {
            SortedAttributefile1Writer.append(ListSortedAttribute1.get(i).getObjectName() + "," + ListSortedAttribute1.get(i).getAttribute() + System.lineSeparator());
        }
        SortedAttributefile1Writer.flush();
        SortedAttributefile1Writer.close();
        for (int i = 0; i < ListSortedAttribute2.size(); i++) {
            SortedAttributefile2Writer.append(ListSortedAttribute2.get(i).getObjectName() + "," + ListSortedAttribute2.get(i).getAttribute() + System.lineSeparator());
        }
        SortedAttributefile2Writer.flush();
        SortedAttributefile2Writer.close();
        ListSortedAttribute1.clear();
        ListSortedAttribute2.clear();

    }
}
