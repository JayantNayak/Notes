package com.example.jayant.notes;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jayant on 1/21/2016.
 */
public class NotesApplication extends Application {
    private static Context context;
    public NotesApplication(){}

    public static Context getApplicationGlobalContext(){ return NotesApplication.context;}
    public void onCreate() {
        super.onCreate();
        NotesApplication.context = getApplicationContext();


    }

}
