package com.example.android.miwok;

/**
 * Created by karma on 27/01/2017.
 */

public class Word {

    private String miwok;

    private String english;

    private int imageResID = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    private int audioResID;

    /*
    * sets the enlgish and miwok words.
    * */

    public Word(String english,String miwok, int image, int audio){
        this.english = english;
        this.miwok = miwok;
        this.imageResID = image;
        this.audioResID = audio;
    }

    public Word(String english,String miwok, int audio){
        this.english = english;
        this.miwok = miwok;
        this.audioResID = audio;
    }

    /*
    * Get the miwok translation for the word
    * */
    public String getMiwokTranslation(){
        return miwok;
    }

    /*
    * Get the english translation for the word
    * */
    public String getDefaultTranslation(){
        return english;
    }

    public int getImage(){
        return imageResID;
    }

    public boolean hasImage(){
        return imageResID != NO_IMAGE_PROVIDED;
    }

    public int getAudioResID(){ return audioResID;}

    @Override
    public String toString() {
        return "Word{" +
                "miwok='" + miwok + '\'' +
                ", english='" + english + '\'' +
                ", imageResID=" + imageResID +
                ", audioResID=" + audioResID +
                '}';
    }
}
