package com.example.myapplication;

public class Show
{
    private String title;
    private String id;
    private String startTime;
    private String[] start_time;
    private String endTime;
    private String[] end_time;
    private String theater;
    private boolean status;

    public Show (String t, String i, String s, String e, String h, boolean st)
    {
        title = t;
        id = i;
        startTime = s;
        endTime = e;
        theater = t;
        status = st;

        String deliminator = "[:]";
        start_time = s.split(deliminator);
        startTime = start_time[0] + ":" + start_time[1];

        end_time = e.split(deliminator);
        endTime = end_time[0] + ":" + end_time[1];
    }

/*
    @Override
    public String toString()
    {
        if (status == false)
        {
            return (theater + "\n" + title + ":\n" + startTime + " - " + endTime);
        } else
        {
            return (theater + "\n" + title + ":\n" + startTime + " - " + endTime);
        }
    }
*/
    public String getTitle() { return title; }
    public String getID() { return id; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getTheater() { return theater; }
    public Boolean getStatus() { return status; }

}
