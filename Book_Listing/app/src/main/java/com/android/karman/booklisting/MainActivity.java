package com.android.karman.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private static final String USGS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?";

    private BookAdapter adapter;

/*
    private TextView emptyStateTextView;

    private EditText search_text;

    private Button search_button;
*/

    private String search_url = USGS_REQUEST_URL;
    String maxResults, orderBy;

    private static final int EARTHQUAKE_LOADER_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a fake list of earthquake locations.
        // Find a reference to the {@link ListView} in the layout

        final ViewHolder holder = new ViewHolder();

        holder.emptyStateTextView = (TextView)findViewById(R.id.empty_view);
        holder.earthquakeListView = (ListView) findViewById(R.id.list);
        holder.earthquakeListView.setEmptyView(holder.emptyStateTextView);
        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new BookAdapter(this,new ArrayList<BookInfo>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        holder.earthquakeListView.setAdapter(adapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        maxResults = sharedPreferences.getString(
                getString(R.string.settings_max_results_key),
                getString(R.string.settings_max_results_default));

        orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );


        holder.earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookInfo info = adapter.getItem(position);

                assert info != null;
                Uri bookUri = Uri.parse(info.getUrl());

                Intent webIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                startActivity(webIntent);
            }
        });

        final LoaderManager.LoaderCallbacks<List<BookInfo>> loaderCallbacks = new LoaderManager.LoaderCallbacks<List<BookInfo>>() {
            @Override
            public Loader<List<BookInfo>> onCreateLoader(int id, Bundle args) {


                return new BookLoader(MainActivity.this, search_url);
            }

            @Override
            public void onLoadFinished(Loader<List<BookInfo>> loader, List<BookInfo> data) {
                View loadingIndicator = findViewById(R.id.loading_indicator);
                loadingIndicator.setVisibility(View.GONE);

                holder.emptyStateTextView.setText(R.string.no_books);
                adapter.clear();

                if (data != null && !data.isEmpty()){
                    adapter.addAll(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<List<BookInfo>> loader) {
                adapter.clear();
            }
        };
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        final LoaderManager loaderManager = getLoaderManager();
        if (networkInfo != null && networkInfo.isConnected()){

            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, loaderCallbacks);

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
                uriBuilder.appendQueryParameter("maxResults", maxResults);
                uriBuilder.appendQueryParameter("printType", orderBy);
                search_url = uriBuilder.toString();
                Log.e(LOG_TAG, search_url);


                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()){
                    holder.earthquakeListView.setVisibility(View.VISIBLE);
                    loaderManager.restartLoader(EARTHQUAKE_LOADER_ID,null,loaderCallbacks);

                }else{
                    Toast.makeText(MainActivity.this,"No Internet ",Toast.LENGTH_LONG).show();

                    holder.emptyStateTextView.setText(R.string.no_internet_connection);
                    holder.loadingIndicator = findViewById(R.id.loading_indicator);
                    holder.loadingIndicator.setVisibility(View.GONE);

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static class ViewHolder{
        TextView emptyStateTextView;

        EditText search_text;

        Button search_button;

        ListView earthquakeListView;

        View loadingIndicator;

    }
}
