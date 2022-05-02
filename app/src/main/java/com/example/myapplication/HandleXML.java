package com.example.myapplication;

import android.content.Context;

import org.w3c.dom.Document;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import com.opencsv.CSVWriter;


// SOURCE FOR CLASS
/* https://mkyong.com/java/how-to-create-xml-file-in-java-dom/ */


// SOURCE FOR CLASS
/* https://mkyong.com/java/how-to-create-xml-file-in-java-dom/ */


public class HandleXML {
    Context context;

    public HandleXML(Context context) {
        this.context = context;
    }

    //public HandleXML() {}


    public void writeXml(String movieTitle, String userName, String movieRating) {

        File file = new File("/data/data/com.example.myapplication/test.csv");

        try {

            // create FileWriter object with file as parameter
            //BufferedWriter outStream= new BufferedWriter(new FileWriter("test.csv", true));
            FileWriter outputfile = new FileWriter("/data/data/com.example.myapplication/test.csv", true);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // add data to csv
            String[] data1 = {movieTitle, userName + ": " + movieRating};
            writer.writeNext(data1);

            // closing writer connection
            writer.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public ArrayList readXML(String movieTitle){
        ArrayList userReviews = new ArrayList();

        try {
            File tmpfile = new File("/data/data/com.example.myapplication/test.csv");
            Scanner sc = new Scanner(tmpfile);
            //Scanner sc = new Scanner(new File("/data/data/com.example.myapplication/files/test.csv"));
            //parsing a CSV file into the constructor of Scanner class
            //sc.useDelimiter(",");

            //setting comma as delimiter pattern
            while (sc.hasNext()) {
                String[] review = sc.nextLine().split(",");
                review[0] = review[0].replaceAll("\"", "");
                if (review[0].trim().equals(movieTitle)){
                    review[1] = review[1].replaceAll("\"", "");
                    userReviews.add(review[1]);
                    System.out.println(review[1]);

                }
                //System.out.println(review[0]);
            }
            sc.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return userReviews;
    }


}


