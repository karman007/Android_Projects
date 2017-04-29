package com.android.karman.inventory;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.karman.inventory.data.ItemContract.ItemEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ITEM_LOADER = 0;

    private ItemCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView listView = (ListView)findViewById(R.id.list_view);

        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        cursorAdapter = new ItemCursorAdapter(this,null,false);
        listView.setAdapter(cursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);

                Uri currentPath = ContentUris.withAppendedId(ItemEntry.CONTENT_URI, id);

                intent.setData(currentPath);

                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(ITEM_LOADER,null, this);
    }

    private void insertPet(){
        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_PRODUCT_NAME, "Hard Drives");
        values.put(ItemEntry.COLUMN_PRODUCT_QUANTITY, 10);
        values.put(ItemEntry.COLUMN_PRODUCT_PRICE, 999.95);
        values.put(ItemEntry.COLUMN_SUPPLIER_NAME, "Karman");
        values.put(ItemEntry.COLUMN_SUPPLIER_EMAIL, "supplier_name@gmail.com");

        Uri newUri = getContentResolver().insert(ItemEntry.CONTENT_URI, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertPet();
                return true;
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {ItemEntry._ID,
                ItemEntry.COLUMN_PRODUCT_NAME,
                ItemEntry.COLUMN_PRODUCT_QUANTITY,
                ItemEntry.COLUMN_PRODUCT_PRICE};

        return new CursorLoader(this,
                ItemEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    private void deleteAllPets(){
        int rowsDeleted = getContentResolver().delete(ItemEntry.CONTENT_URI, null,null);
    }
}
