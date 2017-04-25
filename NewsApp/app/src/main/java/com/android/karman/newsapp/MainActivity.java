package com.android.karman.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getName();

    /** URL to query the USGS dataset*/
    private static final String USGS_REQUEST_URL = "https://content.guardianapis.com/search";

    private NewsAdapter adapter;

    private String search_url = USGS_REQUEST_URL;

    private static final int NEWS_LOADER_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewHolder holder = new ViewHolder();

        holder.emptyStateTextView = (TextView)findViewById(R.id.empty_view);
        holder.newsListView = (ListView) findViewById(R.id.list);
        holder.newsListView.setEmptyView(holder.emptyStateTextView);
        adapter = new NewsAdapter(this,new ArrayList<NewsInfo>());

        holder.newsListView.setAdapter(adapter);

        holder.newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsInfo info = adapter.getItem(position);

                assert info != null;
                Uri newsUri = Uri.parse(info.getUrl());

                Intent webIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(webIntent);
            }
        });

        final LoaderManager.LoaderCallbacks<List<NewsInfo>> loaderCallbacks = new LoaderManager.LoaderCallbacks<List<NewsInfo>>() {
            @Override
            public Loader<List<NewsInfo>> onCreateLoader(int id, Bundle args) {


                return new NewsLoader(MainActivity.this, search_url);
            }

            @Override
            public void onLoadFinished(Loader<List<NewsInfo>> loader, List<NewsInfo> data) {
                View loadingIndicator = findViewById(R.id.loading_indicator);
                loadingIndicator.setVisibility(View.GONE);

                holder.emptyStateTextView.setText(R.string.no_news);
                adapter.clear();

                if (data != null && !data.isEmpty()){
                    adapter.addAll(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<List<NewsInfo>> loader) {
                adapter.clear();
            }
        };
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        final LoaderManager loaderManager = getLoaderManager();
        if (networkInfo != null && networkInfo.isConnected()){

            loaderManager.initLoader(NEWS_LOADER_ID, null, loaderCallbacks);

        }else{

            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            holder.emptyStateTextView.setText(R.string.no_internet_connection);
        }


        holder.search_text = (EditText) findViewById(R.id.search_text);
        holder.search_button = (Button) findViewById(R.id.search_button);

        holder.search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = holder.search_text.getText().toString();
                input = input.replace(" ","+");
                Uri baseuri = Uri.parse(USGS_REQUEST_URL);
                final Uri.Builder uriBuilder = baseuri.buildUpon();
                uriBuilder.appendQueryParameter("q",input);
                uriBuilder.appendQueryParameter("api-key", "test");
                search_url = uriBuilder.toString();
                Log.e(LOG_TAG, search_url);


                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()){
                    holder.newsListView.setVisibility(View.VISIBLE);
                    loaderManager.restartLoader(NEWS_LOADER_ID,null,loaderCallbacks);

                }else{
                    Toast.makeText(MainActivity.this,"No Internet ",Toast.LENGTH_LONG).show();

                    holder.emptyStateTextView.setText(R.string.no_internet_connection);
                    holder.loadingIndicator = findViewById(R.id.loading_indicator);
                    holder.loadingIndicator.setVisibility(View.GONE);

                }
            }
        });
    }
    private static class ViewHolder{
        TextView emptyStateTextView;

        EditText search_text;

        Button search_button;

        ListView newsListView;

        View loadingIndicator;

    }
}
