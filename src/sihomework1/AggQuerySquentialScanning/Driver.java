/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sihomework1.AggQuerySquentialScanning;

import java.io.*;
import java.util.*;

/**
 *
 * @author cool
 */
public class Driver {

    static String COMMA_DELIMITER = ",";
    static String NEW_LINE_SEPARATOR = "\n";
    public static ArrayList<DataPoint> ListData;

    static class PQsort implements Comparator<DataPoint> {

        @Override
        public int compare(DataPoint one, DataPoint two) {
            return (int) ((-(two.getAttribute1() + two.getAttribute2()) + (one.getAttribute1() + one.getAttribute2())) * 100);
        }

        public static void main(String[] args) throws FileNotFoundException, IOException {

            // long lStartTime = new Date().getTime();

            System.out.println("Give path to data directory");
            Scanner sc = new Scanner(System.in);
            String cwd = sc.nextLine();
          //  File file = new File(cwd);
         /*   if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("Working Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }
*/
            System.out.println("name of dataset...format .csv");
            String Dsname=sc.next();
            String pathToDataSetFile=cwd+"\\"+Dsname+".csv";
           
            LoadData(pathToDataSetFile.toString());
             System.out.println("Data Loaded");
             System.out.println("give value for Top_k :");
             int Top_k=sc.nextInt();
            
            
            long lStartTime = new Date().getTime();
            /*
             * Display();
             */

            //int Top_k = 1;

            PQsort pqs = new PQsort();
            PriorityQueue<DataPoint> dataSet_pq = new PriorityQueue<>(10, pqs);
            /*
             * for (DataPoint x : ListData) { dataSet_pq.offer(x); }
             *
             */
            DataPoint d = null;
            for (int i = 0; i < ListData.size(); i++) {
                //System.out.println(i);

                //System.out.println("added");
                dataSet_pq.offer(ListData.get(i));
                if (dataSet_pq.size() > Top_k) {
                    //d = dataSet_pq.element();
                    //System.out.println(d.getObjectName() + " " + (d.getAttribute1() + d.getAttribute2()));
                    dataSet_pq.remove();
                    //System.out.println("removed");
                }
            }

            long lEndTime = new Date().getTime();
             System.out.println("\n\n\tTop"+ Top_k +" objects are: ");


  
            for (int k = 0; k < Top_k; k++) {
                d = dataSet_pq.element();
                dataSet_pq.remove();
                  System.out.println( d.getObjectName() + " " + (d.getAttribute1() + d.getAttribute2()));
            }


            
            long difference = lEndTime - lStartTime;
            System.out.println("Elapsed seconds: " + (difference / 1000.0));
        }

        public static void LoadData(String pathToDataSetFile) throws FileNotFoundException, IOException {
            BufferedReader fileReader;
            fileReader = new BufferedReader(new FileReader(pathToDataSetFile));
            String line;
            int counter = 0;

            ListData = new ArrayList<>();
            DataPoint dp;

            while ((line = fileReader.readLine()) != null) {
                dp = new DataPoint();
                String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length > 0) {
                    dp.setObjectName("object" + ++counter);
                    dp.setAttribute1(Double.parseDouble(tokens[0]));
                    dp.setAttribute2(Double.parseDouble(tokens[1]));
                }
                ListData.add(dp);
            }

        }

        public static void Display() {
            System.out.println(ListData.size());
            for (int i = 0; i < ListData.size(); i++) {
                System.out.println(ListData.get(i).getObjectName() + " " + ListData.get(i).getAttribute1() + " " + ListData.get(i).getAttribute2());
            }

        }
    }
}
