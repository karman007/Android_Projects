package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;
import java.util.jar.Pack200;

/**
 * Created by karma on 12/03/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthquakeInfo>> {

    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    private String url;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.url = url;

    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }
    @Override
    public List<EarthquakeInfo> loadInBackground() {
        if (url == null){
            return null;
        }

        return QueryUtils.fetchEarthquakeData(url);
    }
}
