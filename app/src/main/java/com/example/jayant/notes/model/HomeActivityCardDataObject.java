package com.example.jayant.notes.model;

import java.util.Date;

/**
 * Created by 20708 on 1/11/2016.
 */
public class HomeActivityCardDataObject {
    private String mPreviewText;
    private String mDateText;
    private Date mDate;
    private boolean mPinned;
    private boolean mReminder;

    private NoteColor mNoteColor;

    public HomeActivityCardDataObject(String mPreviewText, String mDateText,boolean mReminder, boolean mPinned,NoteColor mNoteColor ){
        this.mPreviewText = mPreviewText;
        this.mDateText = mDateText;
        this.mPinned = mPinned;
        this.mReminder = mReminder;
        this.mNoteColor = mNoteColor;
    }
    public HomeActivityCardDataObject(String mPreviewText, String mDateText, boolean mReminder,boolean mPinned,NoteColor mNoteColor, Date mDate){
        this.mPreviewText = mPreviewText;
        this.mDateText = mDateText;
        this.mDate = mDate;
        this.mReminder = mReminder;
        this.mPinned = mPinned;
        this.mNoteColor = mNoteColor;
    }

    public String getmPreviewText() {return mPreviewText;}
    public void setmPreviewText(String NameText) {this.mPreviewText = mPreviewText;}


    public String getmDateText() {return mDateText;}
    public void setmDateText(String mDateText) {this.mDateText = mDateText;}


    public Date getmDate(){return mDate ;}
    public void setmEmailText(Date mDate){
        this.mDate=mDate;
    }


    public void setmReminder(boolean mReminder){
        this.mReminder = mReminder;
    }
    public boolean getmReminder(){
        return mReminder;
    }


    public void setmPinned(boolean mPinned){this.mPinned = mPinned;}
    public boolean getmPinned(){
        return mPinned;
    }

    public void setmNoteColor(NoteColor mNoteColor){this.mNoteColor = mNoteColor;}
    public NoteColor getmNoteColor(){
        return mNoteColor;
    }



}
