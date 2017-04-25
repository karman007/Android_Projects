package com.example.karma.tourguide;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by karma on 20/02/2017.
 */

public class MyInformationAdapter extends ArrayAdapter<Information>{
    public MyInformationAdapter(Activity context, ArrayList<Information> objects){
        super(context, 0, objects);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Information info = getItem(position);

        assert info != null;

        TextView title = (TextView) listItemView.findViewById(R.id.title_text);
        title.setText(info.getTitle());

        TextView body = (TextView) listItemView.findViewById(R.id.body_text);
        body.setText(info.getBody());

        ImageView image = (ImageView) listItemView.findViewById(R.id.image);
        image.setImageResource(info.getImageResID());

        return listItemView;

    }
}
