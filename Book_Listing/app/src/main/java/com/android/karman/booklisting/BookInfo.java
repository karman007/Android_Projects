package com.android.karman.booklisting;

/**
 * Created by karma on 4/03/2017.
 */

public class BookInfo {

    private String url;
    private String title;
    private String[] author;


    public BookInfo(String title, String[] author, String url){
        this.title = title;
        this.author = author;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String[] getAuthors() {
        return author;
    }

    public String getUrl() {
        return url;
    }
}
