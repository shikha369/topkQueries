/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sihomework1.Unif.DistQuery;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author cool
 */
public class Driver {

    static String COMMA_DELIMITER = ",";
    static ArrayList<Vector<Double>> ObjectList = new ArrayList<>();
    static ArrayList<Vector<Double>> QueryList = new ArrayList<>();
    static ArrayList<Double> L1distRatio = new ArrayList<>();
    static ArrayList<Double> L2distRatio = new ArrayList<>();
    static ArrayList<Double> LInFdistRatio = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException, IOException {

        System.out.println("Give path to data directory");
        Scanner sc = new Scanner(System.in);
        String cwd = sc.nextLine();

        System.out.println("name of object dataset...format .csv");
        String Dsname = sc.next();

        System.out.println("name of query dataset...format .csv");
        String Qsname = sc.next();


        String pathToObjectsFile = cwd + "\\" + Dsname + ".csv";
        String pathToQueryFile = cwd + "\\" + Qsname + ".csv";
        
        Double dist;
        //Vector<Double> minDistObj,maxDistObj;
        double avgL1r = 0, avgL2r = 0, avgLInfr = 0;

        /*
         * Load dataset and Queryset into their respective Lists
         */
        LoadData(pathToObjectsFile, pathToQueryFile);

        System.out.println("Data Loaded");

        /*
         * for each query point : find nearest and farthest neighbor,calculate
         * distance and find ratio .. do same for L1,L2 and L Infinity distance
         * metric
         */

        for (int Qindex = 0; Qindex < QueryList.size(); Qindex++) {
            Distances d = new Distances();
            for (int Oindex = 0; Oindex < ObjectList.size(); Oindex++) {
                
                
                /* condition check (dist > 0 will skip the object which is same as query) */
                dist = InfNorm(QueryList.get(Qindex), ObjectList.get(Oindex));
                if (dist > 0) { 
                    if (dist > d.get("maxInfNorm")) {
                        d.Update("maxInfNorm", dist);
                    }
                    if (dist < d.get("minInfNorm")) {
                        d.Update("minInfNorm", dist);
                    }
                }
                dist = L1Norm(QueryList.get(Qindex), ObjectList.get(Oindex));
                if (dist > 0) {
                    if (dist > d.get("maxL1Norm")) {
                        d.Update("maxL1Norm", dist);
                    }
                    if (dist < d.get("minL1Norm")) {
                        d.Update("minL1Norm", dist);
                    }
                }

                dist = L2Norm(QueryList.get(Qindex), ObjectList.get(Oindex));
                if (dist > 0) {
                    if (dist > d.get("maxL2Norm")) {
                        d.Update("maxL2Norm", dist);
                    }
                    if (dist < d.get("minL2Norm")) {
                        d.Update("minL2Norm", dist);
                    }
                }
            }
            
            /* calculating ratio*/
            
            L1distRatio.add(d.get("maxL1Norm") / d.get("minL1Norm"));
            L2distRatio.add(d.get("maxL2Norm") / d.get("minL2Norm"));
            LInFdistRatio.add(d.get("maxInfNorm") / d.get("minInfNorm"));
            avgL1r += d.get("maxL1Norm") / d.get("minL1Norm");
            avgL2r += d.get("maxL2Norm") / d.get("minL2Norm");
            avgLInfr += d.get("maxInfNorm") / d.get("minInfNorm");
            System.out.println("query" + Qindex + " done");
            //....min dist = "+d.get("minL1Norm")+" max dist "+d.get("maxL1Norm")+"for"
            // +QueryList.get(Qindex));

        }

        //   System.out.println(L1distRatio);
        //  System.out.println(L2distRatio);
        //  System.out.println(LInFdistRatio);
        System.out.println("avg L1 ratio :" + avgL1r / L1distRatio.size() + " avg L2 ratio :" + avgL2r / L1distRatio.size() + " avg LInf ratio :" + avgLInfr / L1distRatio.size());

    }

    
    /*Method to Load Dataset and Query Set*/
    
    public static void LoadData(String pathToObjectsFile, String pathToQueryFile) throws FileNotFoundException, IOException {
        BufferedReader ObjectsfileReader, QueryfileReader;
        ObjectsfileReader = new BufferedReader(new FileReader(pathToObjectsFile));
        QueryfileReader = new BufferedReader(new FileReader(pathToQueryFile));
        String oLine, qLine;
        int i, counter = 0;

        ObjectList = new ArrayList<>();
        QueryList = new ArrayList<>();

        Vector<Double> v;
        while ((oLine = ObjectsfileReader.readLine()) != null) {
            v = new Vector<Double>();
            String[] tokens = oLine.split(COMMA_DELIMITER);
            if (tokens.length > 0) {
                for (i = 0; i < tokens.length; i++) {
                    v.add(i, Double.parseDouble(tokens[i]));
                }
            }
            ObjectList.add(v);
        }
        ObjectsfileReader.close();
        while ((qLine = QueryfileReader.readLine()) != null) {
            v = new Vector<Double>();
            String[] tokens = qLine.split(COMMA_DELIMITER);
            if (tokens.length > 0) {
                for (i = 0; i < tokens.length; i++) {
                    //v.add(i, Double.NaN);
                    v.add(i, Double.parseDouble(tokens[i]));
                }
            }
            QueryList.add(v);
        }
        QueryfileReader.close();

    }

    /*Method to calculate L1 distance between two data points*/
    public static Double L1Norm(Vector<Double> v1, Vector<Double> v2) {
        Double n = 0.0;
        int size = v1.size();
        for (int i = 0; i < size; i++) {
            n += Math.abs(v1.elementAt(i) - v2.elementAt(i));
        }
        return n;
    }

    /*Method to calculate L2 distance between two data points*/
    public static Double L2Norm(Vector<Double> v1, Vector<Double> v2) {
        Double n = 0.0;
        int size = v1.size();
        for (int i = 0; i < size; i++) {
            n += Math.pow((v1.elementAt(i) - v2.elementAt(i)), 2);
        }
        return Math.sqrt(n);
    }

    /*Method to calculate L Infinity distance between two data points*/
    public static Double InfNorm(Vector<Double> v1, Vector<Double> v2) {
        Double n = 0.0;
        int size = v1.size();
        for (int i = 0; i < size; i++) {
            if (n < (Math.abs(v1.elementAt(i) - v2.elementAt(i)))) {
                n = Math.abs(v1.elementAt(i) - v2.elementAt(i));
            }
        }
        return n;
    }
}
