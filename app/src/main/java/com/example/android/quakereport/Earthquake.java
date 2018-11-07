package com.example.android.quakereport;

/**
 * Created by Mayank on 11/6/18 at 2:52 AM
 **/
public class Earthquake {

    private String magnitude;
    private String locationOffset;
    private String  primaryLocation;



    private String date;
    private String time;

    public Earthquake(String mag, String locationOffset, String primaryLocation, String date, String time) {

        this.magnitude = mag;
       this.locationOffset=locationOffset;
       this.primaryLocation=primaryLocation;
        this.date=date;
        this.time = time;
    }





    public String getMagnitude() {
        return magnitude;
    }
    public String getDate() {
        return date;
    }


    public String getLocationOffset() {
        return locationOffset;
    }

    public String getPrimaryLocation() {
        return primaryLocation;
    }
    public String getTime() {
        return time;
    }
}
