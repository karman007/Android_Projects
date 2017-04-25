package com.android.karman.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by karma on 12/03/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {

    private static final String LOG_TAG = BookLoader.class.getName();

    private String url;

    public BookLoader(Context context, String url) {
        super(context);
        this.url = url;

    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }
    @Override
    public List<BookInfo> loadInBackground() {
        if (url == null){
            return null;
        }

        return QueryUtils.fetchBookData(url);
    }
}
