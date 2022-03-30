package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    EditText startTimeInput;
    EditText endTimeInput;
    EditText dateInput;
    EditText nameInput;
    Spinner theaterSpinner;
    ListView movies;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTimeInput = (EditText) findViewById(R.id.startTime);
        endTimeInput = (EditText) findViewById(R.id.endTime);
        dateInput = (EditText) findViewById(R.id.date);
        nameInput = (EditText) findViewById(R.id.name);
        theaterSpinner = (Spinner) findViewById(R.id.theater);
        movies = (ListView) findViewById(R.id.movies);
        search = (Button) findViewById(R.id.search);

    }
}