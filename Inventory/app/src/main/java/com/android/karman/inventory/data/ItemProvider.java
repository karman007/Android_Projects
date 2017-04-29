package com.android.karman.inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.karman.inventory.data.ItemContract.ItemEntry;

/**
 * Created by karma on 10/04/2017.
 */

public class ItemProvider extends ContentProvider {

    private ItemDbHelper mDbHelper;

    private static final UriMatcher sUriMatcher =  new UriMatcher(UriMatcher.NO_MATCH);

    public static final int PRODUCTS = 100;
    public static final int PRODUCT_ID = 101;

    static {
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_INVENTORY, PRODUCTS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_INVENTORY + "/#", PRODUCT_ID);

    }

    public static final String LOG_TAG = ItemProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        mDbHelper = new ItemDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match){
            case PRODUCTS:
                cursor = database.query(ItemContract.ItemEntry.TABLE_NAME, projection,selection, selectionArgs,
                        null,null,sortOrder);
                break;
            case PRODUCT_ID:
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(ItemContract.ItemEntry.TABLE_NAME, projection,selection,selectionArgs,
                        null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI "+ uri);
        }

        Log.e(LOG_TAG,"URI: " +uri );
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match  = sUriMatcher.match(uri);
        switch (match){
            case PRODUCTS:
                return ItemEntry.CONTENT_LIST_TYPE;
            case PRODUCT_ID:
                return ItemEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match "+ match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case PRODUCTS:
                return insertProduct(uri,values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match){
            case PRODUCTS:
                rowsDeleted = database.delete(ItemContract.ItemEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case PRODUCT_ID:
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ItemContract.ItemEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case PRODUCTS:
                return updateProduct(uri,values,selection,selectionArgs);
            case PRODUCT_ID:
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                return updateProduct(uri,values,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for "+uri);
        }
    }

    private Uri insertProduct(Uri uri, ContentValues values){

        String name = values.getAsString(ItemEntry.COLUMN_PRODUCT_NAME);
        if (name == null){
            throw new IllegalArgumentException("Product requires a name");
        }

        Double price = values.getAsDouble(ItemEntry.COLUMN_PRODUCT_PRICE);
        if (price != null && price < 0.0){
            throw new IllegalArgumentException("Product requires valid price");
        }

        Integer quantity = values.getAsInteger(ItemEntry.COLUMN_PRODUCT_QUANTITY);
        if (quantity != null && quantity < 0){
            throw new IllegalArgumentException("Product requires valid quantity");
        }

        String sup_email = values.getAsString(ItemEntry.COLUMN_SUPPLIER_EMAIL);
        if (sup_email == null){
            throw new IllegalArgumentException("Supplier requires a email");
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(ItemContract.ItemEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return ContentUris.withAppendedId(uri,id);
    }

    private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs){

        if (values.size() == 0){
            return 0;
        }

        if (values.containsKey(ItemEntry.COLUMN_PRODUCT_NAME)){
            String name = values.getAsString(ItemContract.ItemEntry.COLUMN_PRODUCT_NAME);
            if (name == null){
                throw new IllegalArgumentException("Product requires a name");
            }
        }

        if (values.containsKey(ItemContract.ItemEntry.COLUMN_PRODUCT_PRICE)){
            Double price = values.getAsDouble(ItemContract.ItemEntry.COLUMN_PRODUCT_PRICE);
            if (price != null && price < 0.0){
                throw new IllegalArgumentException("Product requires valid price");
            }
        }

        if (values.containsKey(ItemEntry.COLUMN_PRODUCT_QUANTITY)){
            Integer quantity = values.getAsInteger(ItemEntry.COLUMN_PRODUCT_QUANTITY);
            if (quantity != null && quantity < 0){
                throw new IllegalArgumentException("Product requires valid quantity");
            }
        }

        if (values.containsKey(ItemEntry.COLUMN_SUPPLIER_EMAIL)){
            String sup_email = values.getAsString(ItemEntry.COLUMN_SUPPLIER_EMAIL);
            if (sup_email == null){
                throw new IllegalArgumentException("Supplier requires a email");
            }
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int rowsUpdated = db.update(ItemContract.ItemEntry.TABLE_NAME, values,selection, selectionArgs);

        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return rowsUpdated;
    }
}
