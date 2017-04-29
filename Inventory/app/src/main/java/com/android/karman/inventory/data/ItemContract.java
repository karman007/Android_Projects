package com.android.karman.inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by karma on 8/04/2017.
 */

public final class ItemContract {

    private ItemContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.android.inventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_INVENTORY = "inventory";


    public static class ItemEntry implements BaseColumns {

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);

        public static final String TABLE_NAME = "inventory";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_SUPPLIER_NAME = "sup_name";
        public static final String COLUMN_SUPPLIER_EMAIL = "sup_email";
        public static final String COLUMN_PRODUCT_IMAGE = "image";


    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ItemEntry.TABLE_NAME + " (" +
                    ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ItemEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL," +
                    ItemEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL DEFAULT 0," +
                    ItemEntry.COLUMN_PRODUCT_PRICE + " DOUBLE NOT NULL DEFAULT 0," +
                    ItemEntry.COLUMN_PRODUCT_IMAGE + " TEXT," +
                    ItemEntry.COLUMN_SUPPLIER_NAME + " TEXT," +
                    ItemEntry.COLUMN_SUPPLIER_EMAIL + " TEXT NOT NULL)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME;

}
