package com.android.karman.habbittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.karman.habbittracker.data.HabbitContract.HabbitEntry;
import com.android.karman.habbittracker.data.HabbitTrackerDbHelper;

public class MainActivity extends AppCompatActivity {

    private HabbitTrackerDbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new HabbitTrackerDbHelper(this);
    }

    private Cursor readHabitInfo(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] project = {HabbitEntry._ID,
                HabbitEntry.COLUMN_HABBIT_NAME,
                HabbitEntry.COLUMN_HABBIT_DAY};

        Cursor cursor = db.query(
                HabbitEntry.TABLE_NAME,
                project,
                null,
                null,
                null,
                null,
                null
        );

        try {

            int idColumnIndex = cursor.getColumnIndex(HabbitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabbitEntry.COLUMN_HABBIT_NAME);
            int dayColumnIndex = cursor.getColumnIndex(HabbitEntry.COLUMN_HABBIT_DAY);


            while (cursor.moveToNext()){
                int currentId  = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDay = cursor.getString(dayColumnIndex);

            }
        }finally {
            cursor.close();
        }
        return cursor;
    }

    private void insertHabbit(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabbitEntry.COLUMN_HABBIT_NAME, "Drawing");
        values.put(HabbitEntry.COLUMN_HABBIT_DAY, HabbitEntry.DAY_MONDAY);
        long newRowId = db.insert(HabbitEntry.TABLE_NAME, null, values);

        if (newRowId != -1){
            Toast.makeText(this,"Habbit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Error with saving habbit", Toast.LENGTH_SHORT).show();
        }
    }

}
