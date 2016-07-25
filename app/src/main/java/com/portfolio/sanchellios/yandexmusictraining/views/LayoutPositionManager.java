package com.portfolio.sanchellios.yandexmusictraining.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by aleksandrvasilenko on 23.04.16.
 */
public class LayoutPositionManager {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private final String POSITION = ListOfArtistsActivity.LAYOUT_POSITION;

    public LayoutPositionManager(Context context){
        this.context = context;
    }

    public void setPosition(int position){
        // XXX why not constructor?
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putInt(POSITION, position);
        editor.apply();
        Log.i(POSITION, "Stored position = " + position);
    }

    public int getPosition(){
        // XXX has field?
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int position = preferences.getInt(POSITION, 0);
        Log.i(POSITION, "Retrieved position: " + position);
        return position;
    }

    public void resetPosition(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putInt(POSITION, 0);
        editor.apply();
        Log.i(POSITION, "Reset position = " + 0);
    }
}
