package com.example.myapplication;


import java.util.ArrayList;

public class Individual_Movie_Review {
    private Float stars;
    private String comment;
    ArrayList<String> commentlist = new ArrayList<String>();

    public Individual_Movie_Review(Float s, String c){
        stars = s;
        comment = c;
    }


    public Float getStars() {
        return stars;
    }

    public ArrayList getCommentList() {
        return commentlist;
    }

}