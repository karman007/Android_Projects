package com.example.android.quakereport;

/**
 * Created by karma on 4/03/2017.
 */

public class EarthquakeInfo {

    private String url;
    private String place;
    private double magnitude;
    private long timeInMilliSeconds;


    public EarthquakeInfo(String place,double mag, long timeInMilliSeconds, String url){
        this.place = place;
        this.magnitude = mag;
        this.timeInMilliSeconds = timeInMilliSeconds;
        this.url = url;
    }

    public String getPlace() {
        return place;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public long getTimeInMilliSeconds() {
        return timeInMilliSeconds;
    }

    public String getUrl() {
        return url;
    }
}
