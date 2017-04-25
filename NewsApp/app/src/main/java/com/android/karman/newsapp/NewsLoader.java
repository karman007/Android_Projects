package com.android.karman.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by karma on 12/03/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<NewsInfo>> {

    private static final String LOG_TAG = NewsLoader.class.getName();

    private String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;

    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }
    @Override
    public List<NewsInfo> loadInBackground() {
        if (url == null){
            return null;
        }

        return QueryUtils.fetchBookData(url);
    }
}
