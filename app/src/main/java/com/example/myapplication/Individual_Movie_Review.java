package com.example.myapplication;


import java.util.ArrayList;

public class Individual_Movie_Review {
    private Integer stars;
    private String comment;
    ArrayList<String> commentlist = new ArrayList<String>();


    public Integer getStars() {
        return stars;
    }

    public ArrayList getCommentList() {
        return commentlist;
    }

}