package com.android.karman.newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by karma on 4/03/2017.
 */

public class NewsAdapter extends ArrayAdapter<NewsInfo> {


    public NewsAdapter(Activity context, ArrayList<NewsInfo> objects){
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
       View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        NewsInfo info = getItem(position);

        assert info != null;

        TextView newsTitle = (TextView) listItemView.findViewById(R.id.title_of_news);
        newsTitle.setText(info.getTitle());

        TextView section = (TextView) listItemView.findViewById(R.id.section);
        section.setText(info.getSection());

        return listItemView;

    }

}
