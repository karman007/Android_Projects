package com.android.karman.newsapp;

/**
 * Created by karma on 4/03/2017.
 */

public class NewsInfo {

    private String url;
    private String title;
    private String section;


    public NewsInfo(String title, String section, String url){
        this.title = title;
        this.section = section;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getUrl() {
        return url;
    }
}
