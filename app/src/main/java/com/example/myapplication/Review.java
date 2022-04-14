package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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


        if (extras != null)
        {
            System.out.println("  == extras, title: " + extras.getString("title"));
            movie.setText(extras.getString("title"));
        } else
        {
            System.out.println(" == ERR: empty extras");
        }

        WriteXML write = new WriteXML();

        /*
        try {
            write.writeXml();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        */


        rate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                Individual_Movie_Review review = new Individual_Movie_Review(ratingBar.getRating(), nickname.getText().toString());

                review.commentlist.add(nickname.getText().toString() + ": " + ratingBar.getRating());
                System.out.println(review.commentlist.get(0));

                try
                {
                    write.writeXml();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }

            }
        });


    }

}
