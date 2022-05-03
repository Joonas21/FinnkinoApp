package com.example.myapplication;

import android.content.Context;


import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.opencsv.CSVWriter;




/*  class for reading and writing reviews into/out of csv database  */

//@SuppressWarnings("ALL")
public class HandleCSV {
    Context context;

    public HandleCSV(Context context) {
        this.context = context;
    }


    /* creating a new file and adding data gotten as a parameter into the csv file */

    public void writeCSV(String movieTitle, String userName, String movieRating)
    {

        File file = new File("/data/data/com.example.myapplication/test.csv");

        try {

            FileWriter outputFile = new FileWriter("/data/data/com.example.myapplication/test.csv", true);

            CSVWriter writer = new CSVWriter(outputFile);

            String[] data1 = { movieTitle, userName + ":  " + movieRating };
            writer.writeNext(data1);

            writer.close();

        } catch (Exception e) { e.printStackTrace(); }
    }


    /* reading reviews from the file and returning into review class as an arList */

    public ArrayList readCSV(String movieTitle)
    {
        ArrayList userReviews = new ArrayList();

        try {
            File tmpfile = new File("/data/data/com.example.myapplication/test.csv");
            Scanner sc = new Scanner(tmpfile);


            /*  looping through the entire file  */
            /* the data is stored as  ' movieTitle,  userName starRating '  */
            /* so, review[0] = movieTitle,  review[1] = userName starRating */

            while (sc.hasNext())
            {
                String[] review = sc.nextLine().split(",");
                review[0] = review[0].replaceAll("\"", "");


                /* if current movie has reviews */

                if (review[0].trim().equals(movieTitle))
                {
                    review[1] = review[1].replaceAll("\"", "");
                    userReviews.add("  " + review[1] + " stars");
                }
            }
            sc.close();

        }
        catch (Exception e) { e.printStackTrace(); }

        return userReviews;
    }
}


