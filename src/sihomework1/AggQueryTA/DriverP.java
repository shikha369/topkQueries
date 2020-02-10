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
public class DriverP {

    static String COMMA_DELIMITER = ",";
    static String NEW_LINE_SEPARATOR = "\n";
    /*
     * MapDataPoint is random access datastructure which stores object-score
     * pairs
     */
    public static HashMap<String, Double> MapDataPoint;
    public static ArrayList<ObjectAttribute> ListSortedAttribute1;
    public static ArrayList<ObjectAttribute> ListSortedAttribute2;

    static class PQsort implements Comparator<DataPoint> {

        @Override
        public int compare(DataPoint one, DataPoint two) {
            return (int) ((-(two.getfVal()) + (one.getfVal())) * 100);
        }

        public static void main(String[] args) throws FileNotFoundException, IOException {

            // long lStartTime = new Date().getTime();
            System.out.println("Give path to data directory");
            Scanner sc = new Scanner(System.in);
            String cwd = sc.nextLine();

            System.out.println("name of dataset...format .csv");
            String Dsname = sc.next();
            String pathToDataSetFile = cwd + "\\" + Dsname + ".csv";
            String pathToDataSetFileAttribute1 = cwd + "\\SortedAttribute1.csv";
            String pathToDataSetFileAttribute2 = cwd + "\\SortedAttribute2.csv";



            /*
             * Method to Load DataSet into random access structure and
             * preprocessed attributes into sorted structures
             */
            LoadData(pathToDataSetFile, pathToDataSetFileAttribute1, pathToDataSetFileAttribute2);

            // Display();
            System.out.println("Data Loaded");

            /*
             * Input value for Parameter Top_k .. we are dealing with top-k
             * queries
             */
            System.out.println("give value for Top_k :");
            int Top_k = sc.nextInt();

            long lStartTime = new Date().getTime();
            boolean Done = false;

            //int Top_k = 5000;
            int index = 0, curIndex = 0;
            Double UpperBound;

            PQsort pqs = new PQsort();

            /*
             * Using priority Queue to implement min_heap structure
             */

            PriorityQueue<DataPoint> dataSet_pq = new PriorityQueue<>(10, pqs);
            DataPoint d = null;
            while (!Done) {


                UpperBound = ListSortedAttribute1.get(curIndex).getAttribute() + ListSortedAttribute2.get(curIndex).getAttribute();
                //      if (UpperBound > MapDataPoint.get(ListSortedAttribute1.get(curIndex).getObjectName())) {


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
                //   }
                //    if (UpperBound > MapDataPoint.get(ListSortedAttribute2.get(curIndex).getObjectName())) {
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
                //   }

                // if (UpperBound <= MapDataPoint.get(ListSortedAttribute1.get(curIndex).getObjectName()) || UpperBound <= MapDataPoint.get(ListSortedAttribute2.get(curIndex).getObjectName())) {

                /*
                 * Condition to terminate
                 */

                if (UpperBound <= dataSet_pq.peek().getfVal() && dataSet_pq.size() == Top_k) {
                    Done = true;
                }

                curIndex = index++;
            }


            long lEndTime = new Date().getTime();
            System.out.println("\n\n\tTop" + Top_k + " objects [in reverse order] are: ");

            for (int k = 0; k < Top_k; k++) {
                d = dataSet_pq.element();
                dataSet_pq.remove();

                System.out.println(d.getObjectName() + " " + (d.getfVal()));
            }

            long difference = lEndTime - lStartTime;
            System.out.println("Elapsed milliseconds: " + difference);

        }
    }

    /*
     * Method to Load data from Files into respective Data structure
     */
    public static void LoadData(String pathToDataSetFile, String pathToDataSetFileAttribute1, String pathToDataSetFileAttribute2) throws FileNotFoundException, IOException {
        BufferedReader fileReader, SortedAttribute1Reader, SortedAttribute2Reader;
        fileReader = new BufferedReader(new FileReader(pathToDataSetFile));
        SortedAttribute1Reader = new BufferedReader(new FileReader(pathToDataSetFileAttribute1));
        SortedAttribute2Reader = new BufferedReader(new FileReader(pathToDataSetFileAttribute2));
        String line, At1Line, At2Line;
        String[] tokens, At1Tokens, At2Tokens;
        int counter = 0;
        String objName;

        ListSortedAttribute1 = new ArrayList<>();
        ListSortedAttribute2 = new ArrayList<>();
        MapDataPoint = new HashMap<>(1000000);
        ObjectAttribute at1;
        ObjectAttribute at2;

        while ((line = fileReader.readLine()) != null) {
            At1Line = SortedAttribute1Reader.readLine();
            At2Line = SortedAttribute2Reader.readLine();
            objName = "object" + ++counter;
            at1 = new ObjectAttribute();
            at2 = new ObjectAttribute();
            tokens = line.split(COMMA_DELIMITER);
            At1Tokens = At1Line.split(COMMA_DELIMITER);
            At2Tokens = At2Line.split(COMMA_DELIMITER);
            if (tokens.length > 0) {
                at1.setObjectName(At1Tokens[0]);
                at1.setAttribute(Double.parseDouble(At1Tokens[1]));
                at2.setObjectName(At2Tokens[0]);
                at2.setAttribute(Double.parseDouble(At2Tokens[1]));
                MapDataPoint.put(objName, (Double.parseDouble(tokens[0]) + Double.parseDouble(tokens[1])));
            }
            ListSortedAttribute1.add(at1);
            ListSortedAttribute2.add(at2);
        }

        /*
         * Collections.sort(ListSortedAttribute1, new
         * Comparator<ObjectAttribute>() {
         *
         * @Override public int compare(ObjectAttribute o1, ObjectAttribute o2)
         * { return o2.getAttribute().compareTo(o1.getAttribute()); } });
         *
         * Collections.sort(ListSortedAttribute2, new
         * Comparator<ObjectAttribute>() {
         *
         * @Override public int compare(ObjectAttribute o1, ObjectAttribute o2)
         * { return o2.getAttribute().compareTo(o1.getAttribute()); } });
         */
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
