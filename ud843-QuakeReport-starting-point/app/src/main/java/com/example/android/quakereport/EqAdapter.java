package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.text.DecimalFormat;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by karma on 4/03/2017.
 */

public class EqAdapter extends ArrayAdapter<EarthquakeInfo> {

    private static final String LOCATION_SEPARATOR = " of ";


    public EqAdapter(Activity context, ArrayList<EarthquakeInfo> objects){
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
       View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        EarthquakeInfo info = getItem(position);
        DecimalFormat formatter = new DecimalFormat("0.0");



        assert info != null;

        TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitude);
        String mag = formatter.format(info.getMagnitude());
        magnitude.setText(mag);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

        int magnitudeColor = getMagnitudeColor(info.getMagnitude());

        magnitudeCircle.setColor(magnitudeColor);

        String location = info.getPlace();
        String primaryLocation;
        String locationOffset;

        if (location.contains(LOCATION_SEPARATOR)){
            String[] parts = location.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        }else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = location;
        }

        TextView place = (TextView) listItemView.findViewById(R.id.place_primary);
        place.setText(primaryLocation);

        TextView place_offset = (TextView) listItemView.findViewById(R.id.place_offset);
        place_offset.setText(locationOffset);

        Date dateObject = new Date(info.getTimeInMilliSeconds());

        TextView date = (TextView) listItemView.findViewById(R.id.date);
        String formatteDate = formatDate(dateObject);
        date.setText(formatteDate);

        TextView time = (TextView) listItemView.findViewById(R.id.time);
        String formatteTime = formatTime(dateObject);
        time.setText(formatteTime);

        return listItemView;

    }

    private String formatDate(Date dateobject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL DD,yyyy",Locale.ENGLISH);
        return dateFormat.format(dateobject);
    }

    private String formatTime(Date dateobject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a",Locale.ENGLISH);
        return timeFormat.format(dateobject);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
