package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Review extends AppCompatActivity {

    Button rate;
    RatingBar ratingBar;
    EditText nickname;
    EditText dateOfRating;
    EditText review;
    ListView reviewsList;
    TextView movie;


    public Review() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rating_layout);

        rate = (Button) findViewById(R.id.rate);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        nickname = (EditText) findViewById(R.id.nickname);
        dateOfRating = (EditText) findViewById(R.id.dateOfRating);
        review = (EditText) findViewById(R.id.review);
        reviewsList = (ListView) findViewById(R.id.reviewsList);
        movie = (TextView) findViewById(R.id.movie);

        // code here

    }
}

