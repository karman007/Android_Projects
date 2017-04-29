package com.android.karman.inventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.karman.inventory.data.ItemContract;

/**
 * Created by karma on 16/04/2017.
 */

public class ItemCursorAdapter extends CursorAdapter {


    public static final String LOG_TAG = ItemCursorAdapter.class.getSimpleName();

    public ItemCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        final TextView productName = (TextView) view.findViewById(R.id.product_name);
        final TextView productQuantity = (TextView) view.findViewById(R.id.product_quantity);
        final TextView productPrice = (TextView) view.findViewById(R.id.product_price);
        ImageView image = (ImageView) view.findViewById(R.id.product_image);
        Button saleButton = (Button) view.findViewById(R.id.sale_button);
        saleButton.setTag(cursor.getPosition());
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = productName.getText().toString().trim();
                String quantityString = productQuantity.getText().toString().trim();
                String priceString = productPrice.getText().toString().trim();
                int newQuantity = Integer.parseInt(quantityString);
                if (newQuantity >= 1 ){
                    newQuantity--;
                }else {
                    Toast.makeText(context,"Quantity too Low",Toast.LENGTH_SHORT).show();
                    return;
                }
                int postion = (int)v.getTag();
                Log.e(LOG_TAG,"Pos: " + postion);
                Uri currentPath = ContentUris.withAppendedId(ItemContract.ItemEntry.CONTENT_URI, getItemId(postion));

                Log.e(LOG_TAG,"Uri: " + currentPath);
                ContentValues values = new ContentValues();

                values.put(ItemContract.ItemEntry.COLUMN_PRODUCT_QUANTITY, Integer.toString(newQuantity));

                int rowsAffected = context.getContentResolver().update(currentPath, values, null, null);

                if (rowsAffected == 0) {
                    Toast.makeText(context, "Error with updating product", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Product updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String name = cursor.getString(cursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_PRODUCT_NAME));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_PRODUCT_QUANTITY));
        double price = cursor.getDouble(cursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_PRODUCT_PRICE));
        String imageString = cursor.getString(cursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_PRODUCT_NAME));
        String priceString = "$" + price;
        String quantityString = "" + quantity;
        Uri imageUri = Uri.parse(imageString);

        productName.setText(name);
        productPrice.setText(priceString);
        productQuantity.setText(quantityString);
        if (imageUri != null){
            image.setImageURI(imageUri);
        }

    }
}
