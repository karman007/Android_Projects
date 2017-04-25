package com.android.karman.booklisting;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by karma on 4/03/2017.
 */

public class BookAdapter extends ArrayAdapter<BookInfo> {


    public BookAdapter(Activity context, ArrayList<BookInfo> objects){
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
       View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        BookInfo info = getItem(position);

        assert info != null;

        TextView place = (TextView) listItemView.findViewById(R.id.title_of_book);
        place.setText(info.getTitle());

        TextView place_offset = (TextView) listItemView.findViewById(R.id.authors);
        place_offset.setText(TextUtils.join(", ", info.getAuthors()));

        return listItemView;

    }

}
