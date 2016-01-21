package com.example.jayant.notes;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.example.jayant.notes.utils.NotesUtils;

/**
 * Created by Jayant on 1/21/2016.
 */
public class NotesApplication extends Application {
    private static Context context;
    private Typeface arialFont, openSans;
    public NotesApplication(){}

    public static Context getApplicationGlobalContext(){ return NotesApplication.context;}
    public void onCreate() {
        super.onCreate();
        NotesApplication.context = getApplicationContext();

        arialFont = Typeface.createFromAsset(getAssets(), NotesUtils.FONT_ARIAL);
        openSans = Typeface.createFromAsset(getAssets(), NotesUtils.FONT_OPEN_SANS);


    }
    public Typeface getArialFont() {
        return arialFont;
    }

    public Typeface getOpenSans() {
        return openSans;
    }
}
