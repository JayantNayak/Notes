package com.example.jayant.notes.model;

/**
 * Created by I329687 on 9/25/2016.
 */

import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "NotesDatabase.db";
    public static final String NOTES_TABLE_NAME = "notestable";
    public static final String NOTES_COLUMN_ID = "id";
    public static final String NOTES_COLUMN_CONTENT = "content";
    public static final String NOTES_COLUMN_TIMESTAMP = "timestamp";
    public static final String NOTES_COLUMN_COLOR = "color";
    public static final String NOTES_COLUMN_ALARAM = "alarm";
    public static final String NOTES_COLUMN_MISC = "misc";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table "+ NOTES_TABLE_NAME  +"("+ NOTES_COLUMN_ID + " integer primary key, " +NOTES_COLUMN_CONTENT+ " blob," +
                        NOTES_COLUMN_TIMESTAMP+ " text,"+NOTES_COLUMN_COLOR+ " text," +NOTES_COLUMN_ALARAM+ " text,"+NOTES_COLUMN_MISC+ " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+ NOTES_TABLE_NAME);
        onCreate(db);
    }
    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+ NOTES_TABLE_NAME);
    }
    public void createTbale(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "create table "+ NOTES_TABLE_NAME  +"("+ NOTES_COLUMN_ID + " integer primary key, " +NOTES_COLUMN_CONTENT+ " blob," +
                        NOTES_COLUMN_TIMESTAMP+ " text,"+NOTES_COLUMN_COLOR+ " text," +NOTES_COLUMN_ALARAM+ " text,"+NOTES_COLUMN_MISC+ " text)"
        );
    }
    public boolean insertNote  (byte[]  content, String timestamp, String color, String alarm, String misc)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_COLUMN_CONTENT, content);
        contentValues.put(NOTES_COLUMN_TIMESTAMP, timestamp);
        contentValues.put(NOTES_COLUMN_COLOR, color);
        contentValues.put(NOTES_COLUMN_ALARAM, alarm);
        contentValues.put(NOTES_COLUMN_MISC, misc);
        db.insert(NOTES_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ NOTES_TABLE_NAME +" where " + NOTES_COLUMN_ID +" = "+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, NOTES_TABLE_NAME);
        return numRows;
    }

    public boolean  updateNote (Integer id,byte[]  content, String timestamp, String color, String alarm, String misc)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_COLUMN_CONTENT, content);
        contentValues.put(NOTES_COLUMN_TIMESTAMP, timestamp);
        contentValues.put(NOTES_COLUMN_COLOR, color);
        contentValues.put(NOTES_COLUMN_ALARAM, alarm);
        contentValues.put(NOTES_COLUMN_MISC, misc);
        db.update(NOTES_TABLE_NAME, contentValues, NOTES_COLUMN_ID +" = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteNote (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(NOTES_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public  Map<String, ArrayList<String>> getAllNotes()
    {
        ArrayList<String[]> result_list = new ArrayList<String[]>();

        //hp = new HashMap();
        Map<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + NOTES_TABLE_NAME, null );
        res.moveToFirst();
        ArrayList<String> dateTexList = new ArrayList<String>();
        ArrayList<String> reminderList = new ArrayList<String>();
        ArrayList<String> pinList = new ArrayList<String>();
        ArrayList<String> colorList = new ArrayList<String>();
        ArrayList<String> idList = new ArrayList<String>();
        while(res.isAfterLast() == false){
            //String resString =
            dateTexList.add(res.getString(res.getColumnIndex(NOTES_COLUMN_TIMESTAMP)));
            //reminderList.add(Boolean.getBoolean(res.getString(res.getColumnIndex(NOTES_COLUMN_ALARAM))));
            //pinList.add(Boolean.getBoolean(res.getString(res.getColumnIndex(NOTES_COLUMN_MISC))));

            reminderList.add(res.getString(res.getColumnIndex(NOTES_COLUMN_ALARAM)));
            pinList.add(res.getString(res.getColumnIndex(NOTES_COLUMN_MISC)));

            colorList.add(res.getString(res.getColumnIndex(NOTES_COLUMN_COLOR)));
            idList.add(res.getString(res.getColumnIndex(NOTES_COLUMN_ID)));

            res.moveToNext();
        }

        result.put("dateTexList",dateTexList);
        result.put("reminderList",reminderList);
        result.put("pinList",pinList);
        result.put("colorList",colorList);
        result.put("idList",idList);

        return result;
    }
}
