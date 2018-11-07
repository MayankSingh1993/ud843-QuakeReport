package com.example.android.quakereport;

/**
 * Created by Mayank on 11/6/18 at 2:52 AM
 **/
public class Earthquake {

    private String magnitude;
    private String locationOffset;
    private String  primaryLocation;
    private String url;



    private String date;
    private String time;

    public String getUrl() {
        return url;
    }

    public Earthquake(String mag, String locationOffset, String primaryLocation, String date, String time, String url) {

        this.magnitude = mag;
       this.locationOffset=locationOffset;
       this.primaryLocation=primaryLocation;
        this.date=date;
        this.time = time;
        this.url=url;
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
