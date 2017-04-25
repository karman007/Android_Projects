package com.example.android.pets.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by karma on 8/04/2017.
 */

public final class PetContract {

    private  PetContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.android.pets";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PETS = "pets";


    public static class PetEntry implements BaseColumns{

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_PETS);

        public static final String TABLE_NAME = "pets";

        public static final String _ID               = BaseColumns._ID;
        public static final String COLUMN_PET_NAME   = "name";
        public static final String COLUMN_PET_BREED  = "breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";


        /*Gender Constants*/
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE    = 1;
        public static final int GENDER_FEMALE  = 2;

        public static boolean isValidGender(int gender){
            return gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_FEMALE;
        }

    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PetEntry.TABLE_NAME + " (" +
                    PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL," +
                    PetEntry.COLUMN_PET_BREED + " TEXT," +
                    PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL DEFAULT 0," +
                    PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PetEntry.TABLE_NAME;

}
