package com.example.android.quakereport;

/**
 * Created by Mayank on 11/6/18 at 2:52 AM
 **/
public class Earthquake {

    private String magnitude;
    private String location;
    private String date;

    public String getDate() {
        return date;
    }

    private String time;


    public Earthquake(String magnitude, String location, String time) {
        this.magnitude = magnitude;
        this.location = location;
        this.time = time;
    }

    public Earthquake(String magnitude, String location, String date, String time) {
        this.magnitude = magnitude;
        this.location = location;
        this.date=date;
        this.time = time;
    }


    public String getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }
}
