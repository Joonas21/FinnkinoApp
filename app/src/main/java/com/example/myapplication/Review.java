package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;




/*  class for showing all of a current movie's reviews
 *  as well as writing new ones under the current one   */


//@SuppressWarnings("ALL")
public class Review extends AppCompatActivity {

    Button rate;
    RatingBar ratingBar;
    EditText nickname;
    ListView reviewsList;
    TextView movie;


    Context context;

    public Review() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_layout);


        rate = (Button) findViewById(R.id.rate);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        nickname = (EditText) findViewById(R.id.nickname);
        reviewsList = (ListView) findViewById(R.id.reviewsList);
        movie = (TextView) findViewById(R.id.movie);



        /* the movie title is received as an extra, here's handling that */

        Bundle extras = getIntent().getExtras();
        String title = "";

        if (extras != null)
        {
            title  = title + extras.getString("title");
            movie.setText(extras.getString("title"));

        } else { System.out.println(" == ERR: empty extras"); }



        /* calling the handleCSV class and reading/writing csv file */

        context = getApplicationContext();
        HandleCSV csv = new HandleCSV(context);


        /* searching the csv file to look for anything under the current movie */

        ArrayList temp;
        temp = csv.readCSV(title);


        /* if no reviews were found for the current movie, adding to the array 'no reviews' */

        if (temp.size() == 0) { temp.add("No reviews yet."); }


        /* adding the pre-existing movie reviews to the review list */

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_text, temp);
        reviewsList.setAdapter(adapter);



        String finalTitle = title;


        /* when clicked, writing the user's inputted name and review to database */

        rate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                String user = nickname.getText().toString();
                Float rating = ratingBar.getRating();

                csv.writeCSV(finalTitle, user, rating.toString());

            }
        });
    }
}
