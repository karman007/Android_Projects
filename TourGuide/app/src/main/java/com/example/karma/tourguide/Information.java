package com.example.karma.tourguide;

/**
 * Created by karma on 20/02/2017.
 */

public class Information {

    private String title;

    private String body;

    private String location;

    private int imageResID;

    public Information(String title, String body, String location){
        this.title = title;
        this.body = body;
        this.location = location;
    }

    public Information(String title, String body, String location, int image){
        this.title = title;
        this.body = body;
        this.location = location;
        this.imageResID = image;
    }

    public String getBody(){return body;}

    public String getTitle(){return title;}

    public String getLocation(){return  location;}

    public int getImageResID() {return  imageResID;}

    @Override
    public String toString() {
        return "Information{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", location='" + location + '\'' +
                ", imageResID=" + imageResID +
                '}';
    }
}
