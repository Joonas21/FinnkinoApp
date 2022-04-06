package com.example.myapplication;

public class Theater
{
    private String name;
    private String id;

    Theater(String n, String i)
    {
        id = i;
        name = n;
    }

    @Override
    public String toString() { return name; }
    public String getID() { return id; }
    public String getName() { return name; }
}
