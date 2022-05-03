package com.example.myapplication;




/*  class for a single show running in theaters  */


//@SuppressWarnings("ALL")
public class Show
{
    private String title;
    private String id;
    private String startTime;
    private String[] start_time;
    private String endTime;
    private String[] end_time;
    private String theater;


    public Show (String t, String i, String s, String e, String th)
    {
        title = t;
        id = i;
        startTime = s;
        endTime = e;
        theater = th;

        String deliminator = "[:]";
        start_time = s.split(deliminator);
        startTime = start_time[0] + ":" + start_time[1];

        end_time = e.split(deliminator);
        endTime = end_time[0] + ":" + end_time[1];
    }


    /* https://stackoverflow.com/questions/47776826/item-in-listview-showing-package-name */
    // overriding toString() method for some reason idk but it works

    @Override
    public String toString()
    {
        return (title + ":\n" + startTime + " - " + endTime);
    }

    public String getTitle() { return title; }
    public String getID() { return id; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getTheater() { return theater; }

}
