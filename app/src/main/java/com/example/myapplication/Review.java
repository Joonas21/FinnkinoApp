package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Review extends AppCompatActivity {

    Button rate;
    RatingBar ratingBar;
    EditText nickname;
    EditText dateOfRating;
    EditText review;
    ListView reviewsList;
    TextView movie;

    ArrayList<Individual_Movie_Review> l = new ArrayList<>();

    Context context;

    public Review() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rating_layout);


        Bundle extras = getIntent().getExtras();


        rate = (Button) findViewById(R.id.rate);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        nickname = (EditText) findViewById(R.id.nickname);
        dateOfRating = (EditText) findViewById(R.id.dateOfRating);
        review = (EditText) findViewById(R.id.review);
        reviewsList = (ListView) findViewById(R.id.reviewsList);
        movie = (TextView) findViewById(R.id.movie);

        String title = "";

        if (extras != null)
        {
            title  = title + extras.getString("title");
            System.out.println("  == extras, title: " + extras.getString("title"));
            movie.setText(extras.getString("title"));
        } else
        {
            System.out.println(" == ERR: empty extras");
        }

        context = getApplicationContext();

        ArrayList temp;


        HandleXML xml = new HandleXML(context);

        //temp = xml.readXML("movieTitle");
        //temp = xml.readXML("anotherOne");


        temp = xml.readXML(title);

        if (temp.size() == 0) { temp.add("No reviews yet."); }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, temp);

        reviewsList.setAdapter(adapter);


        String finalTitle = title;
        String user = nickname.getText().toString();
        Float rating = ratingBar.getRating();


        rate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                xml.writeXml(finalTitle, user, rating.toString());

            }
        });


    }

}
