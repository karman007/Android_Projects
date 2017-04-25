package com.example.android.miwok;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by karma on 27/01/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int colorResID;

    public WordAdapter(Activity context, ArrayList<Word> words, int color){
        super(context, 0, words);
        this.colorResID = color;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        assert currentWord != null;

        TextView englishTextView = (TextView) listItemView.findViewById(R.id.english_text_view);
        englishTextView.setText(currentWord.getDefaultTranslation());

        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getMiwokTranslation());

        ImageView image = (ImageView) listItemView.findViewById(R.id.image);

        if (currentWord.hasImage()){
            image.setImageResource(currentWord.getImage());
            image.setVisibility(View.VISIBLE);
        }else {
            image.setVisibility(View.GONE);
        }

        View textContainer =  listItemView.findViewById(R.id.text_container);

        int color = ContextCompat.getColor(getContext(), colorResID);
        textContainer.setBackgroundColor(color);

        return listItemView;

    }
}
