/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sihomework1.AggQueryTA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author cool
 */
public class Driverwp {

    static String COMMA_DELIMITER = ",";
    static String NEW_LINE_SEPARATOR = "\n";
    public static HashMap<String, Double> MapDataPoint;
    public static ArrayList<ObjectAttribute> ListSortedAttribute1;
    public static ArrayList<ObjectAttribute> ListSortedAttribute2;

    static class PQsort implements Comparator<DataPoint> {

        @Override
        public int compare(DataPoint one, DataPoint two) {
            return (int) ((-(two.getfVal()) + (one.getfVal())) * 100);
        }

        public static void main(String[] args) throws FileNotFoundException, IOException {

            long lStartTime = new Date().getTime();

            String pathToDataSetFile = "\dataset1.csv";
            LoadData(pathToDataSetFile.toString());

            // Display();

            boolean Done = false;

            int Top_k = 1;
            int index = 0, curIndex;
            Double UpperBound;

            PQsort pqs = new PQsort();
            PriorityQueue<DataPoint> dataSet_pq = new PriorityQueue<>(10, pqs);
            DataPoint d = null;
            while (!Done) {
                curIndex = index++;
                UpperBound = ListSortedAttribute1.get(curIndex).getAttribute() + ListSortedAttribute2.get(curIndex).getAttribute();
                if (UpperBound > MapDataPoint.get(ListSortedAttribute1.get(curIndex).getObjectName())) {
                    
                     
                        /*
                         * insert into tree
                         */
                        d = new DataPoint();
                        d.setObjectName(ListSortedAttribute1.get(curIndex).getObjectName());
                        d.setfVal(MapDataPoint.get(ListSortedAttribute1.get(curIndex).getObjectName()));
                        if (!dataSet_pq.contains(d)) {
                        dataSet_pq.offer(d);

                        /*
                         * maintain size
                         */
                        if (dataSet_pq.size() > Top_k) {
                            dataSet_pq.remove();
                        }
                    }
                }
                if (UpperBound > MapDataPoint.get(ListSortedAttribute2.get(curIndex).getObjectName())) {
                   // if (!MapDataPoint.containsKey(ListSortedAttribute1.get(curIndex).getObjectName())) {
                        /*
                         * insert into tree
                         */
                        d = new DataPoint();
                        d.setObjectName(ListSortedAttribute2.get(curIndex).getObjectName());
                        d.setfVal(MapDataPoint.get(ListSortedAttribute2.get(curIndex).getObjectName()));
                        if (!dataSet_pq.contains(d)) {
                        dataSet_pq.offer(d);

                        /*
                         * maintain size
                         */
                        if (dataSet_pq.size() > Top_k) {
                            dataSet_pq.remove();
                        }
                    }
                }

               // if (UpperBound <= MapDataPoint.get(ListSortedAttribute1.get(curIndex).getObjectName()) || UpperBound <= MapDataPoint.get(ListSortedAttribute2.get(curIndex).getObjectName())) {
                 if (UpperBound <=dataSet_pq.peek().getfVal()){
                  Done = true;
                }

            }

            System.out.println("\n\n\tTop" + Top_k + " objects are: ");

            for (int k = 0; k < Top_k; k++) {
                d = dataSet_pq.element();
                dataSet_pq.remove();

                System.out.println(d.getObjectName() + " " + (d.getfVal()));
            }
            long lEndTime = new Date().getTime();
            long difference = lEndTime - lStartTime;
            System.out.println("Elapsed milliseconds: " + difference);

        }
    }

    public static void LoadData(String pathToDataSetFile) throws FileNotFoundException, IOException {
        BufferedReader fileReader;
        fileReader = new BufferedReader(new FileReader(pathToDataSetFile));
        String line;
        int counter = 0;
        String objName;

        ListSortedAttribute1 = new ArrayList<>();
        ListSortedAttribute2 = new ArrayList<>();
        MapDataPoint = new HashMap<>(1000000);
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
                MapDataPoint.put(objName, (Double.parseDouble(tokens[0]) + Double.parseDouble(tokens[1])));
            }
            ListSortedAttribute1.add(at1);
            ListSortedAttribute2.add(at2);
        }

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

    }

    public static void Display() {
        System.out.println(ListSortedAttribute1.size());
        for (int i = 0; i < ListSortedAttribute1.size(); i++) {
            System.out.println(ListSortedAttribute1.get(i).getObjectName() + " " + ListSortedAttribute1.get(i).getAttribute() + "----" + ListSortedAttribute2.get(i).getObjectName() + " " + ListSortedAttribute2.get(i).getAttribute());
        }

        System.out.println("printing hash map");

        for (Map.Entry<String, Double> entry : MapDataPoint.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());


        }
    }
}
