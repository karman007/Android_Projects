package com.android.karman.inventory;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.karman.inventory.data.ItemContract;

/**
 * Created by karma on 16/04/2017.
 */

public class ItemCursorAdapter extends CursorAdapter {

    public ItemCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView productName = (TextView) view.findViewById(R.id.product_name);
        TextView productQuantity = (TextView) view.findViewById(R.id.product_quantity);
        TextView productPrice = (TextView) view.findViewById(R.id.product_price);


        String name = cursor.getString(cursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_PRODUCT_NAME));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_PRODUCT_QUANTITY));
        int price = cursor.getInt(cursor.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_PRODUCT_PRICE));
        String priceString = "" + price;
        String quantityString = "" + quantity;

        productName.setText(name);
        productPrice.setText(priceString);
        productQuantity.setText(quantityString);
    }
}
