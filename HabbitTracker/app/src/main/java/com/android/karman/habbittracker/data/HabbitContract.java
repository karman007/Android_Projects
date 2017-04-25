package com.android.karman.habbittracker.data;

import android.provider.BaseColumns;

/**
 * Created by karma on 8/04/2017.
 */

public final class HabbitContract {
    private HabbitContract(){}

    public static class HabbitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habbits";

        public static final String _ID                = BaseColumns._ID;
        public static final String COLUMN_HABBIT_NAME = "name";
        public static final String COLUMN_HABBIT_DAY  = "day";


        /*Gender Constants*/
        public static final int DAY_MONDAY    = 0;
        public static final int DAY_TUESDAY   = 1;
        public static final int DAY_WEDNESDAY = 2;
        public static final int DAY_THURSDAY  = 3;
        public static final int DAY_FRIDAY    = 4;
        public static final int DAY_SATURDAY  = 5;
        public static final int DAY_SUNDAY    = 6;


    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HabbitEntry.TABLE_NAME + " (" +
                    HabbitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    HabbitEntry.COLUMN_HABBIT_NAME + " TEXT NOT NULL," +
                    HabbitEntry.COLUMN_HABBIT_DAY + " INTEGER NOT NULL DEFAULT 0)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabbitEntry.TABLE_NAME;

}
