package com.example.myapplication;




/*  class for a single Theater object used in the spinner  */


//@SuppressWarnings("ALL")
public class Theater
{
    private String name;
    private String id;


    public Theater(String n, String i)
    {
        id = i;
        name = n;
    }


    /* https://stackoverflow.com/questions/47776826/item-in-listview-showing-package-name */
    // overriding toString() method for some reason idk but it works

    @Override
    public String toString() { return name; }
    public String getID() { return id; }
    public String getName() { return name; }
}
