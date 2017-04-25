/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.karman.inventory;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.karman.inventory.data.ItemContract.ItemEntry;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText mNameEditText;

    private EditText mQuantityEditText;

    private EditText mPriceEditText;

    private static final int EXISTING_ITEM_LOADER = 0;

    private Uri currentItemUri;

    private boolean itemHasChanged = false;

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            itemHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        currentItemUri = intent.getData();

        if (currentItemUri == null) {
            setTitle("Add a Product");

            invalidateOptionsMenu();
        } else {
            setTitle("Edit Product");
            getLoaderManager().initLoader(EXISTING_ITEM_LOADER, null, this);
        }

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_product_name);
        mQuantityEditText = (EditText) findViewById(R.id.edit_product_quantity);
        mPriceEditText = (EditText) findViewById(R.id.edit_product_price);

        mNameEditText.setOnTouchListener(touchListener);
        mQuantityEditText.setOnTouchListener(touchListener);
        mPriceEditText.setOnTouchListener(touchListener);

    }

    private void savePet() {
        String nameString = mNameEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();

        if (currentItemUri == null &&
                TextUtils.isEmpty(nameString) && TextUtils.isEmpty(quantityString) &&
                TextUtils.isEmpty(priceString)) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_PRODUCT_NAME, nameString);

        int quantity = 0;
        if (!TextUtils.isEmpty(quantityString)) {
            quantity = Integer.parseInt(quantityString);
        }
        values.put(ItemEntry.COLUMN_PRODUCT_QUANTITY, quantity);

        int price = 0;
        if (!TextUtils.isEmpty(priceString)) {
            price = Integer.parseInt(priceString);
        }
        values.put(ItemEntry.COLUMN_PRODUCT_PRICE, price);

        if (currentItemUri == null) {

            Uri newUri = getContentResolver().insert(ItemEntry.CONTENT_URI, values);

            if (newUri == null) {
                Toast.makeText(this, "Error with saving product", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Product Saved", Toast.LENGTH_SHORT).show();
            }
        } else {
            int rowsAffected = getContentResolver().update(currentItemUri, values, null, null);

            if (rowsAffected == 0) {
                Toast.makeText(this, "Error with updating product", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Product updated", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (currentItemUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_save:
                savePet();
                finish();
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                  if (!itemHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                DialogInterface.OnClickListener discardButtonListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    }
                };
                showUnsavedChangesDialog(discardButtonListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!itemHasChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };

        showUnsavedChangesDialog(discardButtonListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ItemEntry._ID,
                ItemEntry.COLUMN_PRODUCT_NAME,
                ItemEntry.COLUMN_PRODUCT_QUANTITY,
                ItemEntry.COLUMN_PRODUCT_PRICE};

        return new CursorLoader(this,
                currentItemUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null || data.getCount() < 1) {
            return;
        }

        if (data.moveToFirst()) {
            int nameColIdx = data.getColumnIndex(ItemEntry.COLUMN_PRODUCT_NAME);
            int quantityColIdx = data.getColumnIndex(ItemEntry.COLUMN_PRODUCT_QUANTITY);
            int priceColIdx = data.getColumnIndex(ItemEntry.COLUMN_PRODUCT_PRICE);


            String name = data.getString(nameColIdx);
            int price = data.getInt(priceColIdx);
            int quantity = data.getInt(quantityColIdx);

            mNameEditText.setText(name);
            mQuantityEditText.setText(quantity);
            mPriceEditText.setText(price);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNameEditText.setText("");
        mQuantityEditText.setText("");
        mPriceEditText.setText("");
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and quit editing?");
        builder.setPositiveButton("Discard", listener);
        builder.setNegativeButton("Keep Editing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete this product");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deletePet();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deletePet() {
        if (currentItemUri != null) {

            int rowsDeleted = getContentResolver().delete(currentItemUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, "Error with deleting product",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Product deleted",
                        Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}