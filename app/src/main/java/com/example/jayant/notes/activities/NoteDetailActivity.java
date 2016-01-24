package com.example.jayant.notes.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.jayant.notes.R;
import com.example.jayant.notes.model.NoteColor;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class NoteDetailActivity extends AppCompatActivity implements View.OnClickListener {
    static private final String TAG = "NoteDetails-Activity";

    private Toolbar mToolbar;
    private PopupWindow pwindo;
    private ImageView btnClosePopup;
    private ImageView mCircleWhite;
    private ImageView mCircleYellow;
    private ImageView mCircleBlue;
    private ImageView mCircleGreen;
    private ImageView mCircleOrange;

    private NoteColor mNoteCol;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        initActivityView();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void initActivityView() {
        mToolbar = (Toolbar) findViewById(R.id.activity_note_detail_toolbar);
        mToolbar.inflateMenu(R.menu.menu_activity_note_detail_toolbar);
       /* if(mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("toolbar!");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
        // default white color of note
        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.circle_white) );
        mNoteCol = NoteColor.WHITE;

                mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.activity_note_detail_MoreSubMenu:
                                //Toast.makeText(getApplicationContext(), "clicked on submenu_item1_addInfo", Toast.LENGTH_LONG).show();
                                initPopupWindow();
                                return true;
                            //case R.id.submenu_item2_contacts:
                        }
                        return true;
                    }
                });


    }

    private void initPopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) NoteDetailActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.activity_note_detail_popup_window,
                    (ViewGroup) findViewById(R.id.activity_note_detail_popup_element));
            View parentView = (View) findViewById(R.id.acivity_note_details_parent_view);
            pwindo = new PopupWindow(layout, parentView.getWidth(), 500, true);
            // pwindo.setFocusable(true);
            pwindo.setBackgroundDrawable(new BitmapDrawable());
            //pwindo.setOutsideTouchable(true);
            pwindo.showAtLocation(layout, Gravity.TOP, 0, 50);

            //pwindo.setOverlapAnchor(true);


            btnClosePopup = (ImageView) layout.findViewById(R.id.activity_note_detail_cancel_popup_btn);
            btnClosePopup.getDrawable().setColorFilter(ContextCompat.getColor(this, R.color.popup_window_grey_bar), PorterDuff.Mode.MULTIPLY);

            btnClosePopup.setOnClickListener(this);

            mCircleWhite = (ImageView) layout.findViewById(R.id.select_col_white);
            mCircleWhite.setOnClickListener(this);

            mCircleYellow = (ImageView) layout.findViewById(R.id.select_col_yellow);
            mCircleYellow.setOnClickListener(this);

            mCircleBlue = (ImageView) layout.findViewById(R.id.select_col_blue);
            mCircleBlue.setOnClickListener(this);

            mCircleGreen = (ImageView) layout.findViewById(R.id.select_col_green);
            mCircleGreen.setOnClickListener(this);

            mCircleOrange = (ImageView) layout.findViewById(R.id.select_col_orange);
            mCircleOrange.setOnClickListener(this);

            // change colour of selected colour circles according to toolbar background color


            switch (mNoteCol) {
                case WHITE:
                    setAllDefaultCircles();
                    mCircleWhite.setImageResource(R.drawable.white_filled_circle_selected);
                    break;

                case YELLOW:
                    setAllDefaultCircles();
                    mCircleYellow.setImageResource(R.drawable.yellow_filled_circle_selected);
                    break;

                case BLUE:
                    setAllDefaultCircles();
                    mCircleBlue.setImageResource(R.drawable.blue_filled_circle_selected);
                    break;

                case GREEN:
                    setAllDefaultCircles();
                    mCircleGreen.setImageResource(R.drawable.green_filled_circle_selected);
                    break;

                case ORANGE:
                    setAllDefaultCircles();
                    mCircleOrange.setImageResource(R.drawable.orange_filled_circle_selected);
                    break;



            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //Read more: http://www.androidhub4you.com/2012/07/how-to-create-popup-window-in-android.html#ixzz3y9oMOuOl
    }

    private void setAllDefaultCircles(){
        // set all circles to default unelected color state
        mCircleWhite.setImageResource(R.drawable.white_filled_circle);

        mCircleYellow.setImageResource(R.drawable.yellow_filled_circle);

        mCircleBlue.setImageResource(R.drawable.blue_filled_circle);

        mCircleGreen.setImageResource(R.drawable.green_filled_circle);

        mCircleOrange.setImageResource(R.drawable.orange_filled_circle);


    }

    private void setNoteColor(int color,NoteColor colr) {
        //Toast.makeText(this, " color clicked : ", Toast.LENGTH_SHORT).show();
        pwindo.dismiss();
        mToolbar.setBackgroundColor(ContextCompat.getColor(this, color));
        mNoteCol = colr;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "  clicked on  " + v.getId());
        switch (v.getId()) {

            case R.id.activity_note_detail_cancel_popup_btn:
                btnClosePopup.setSelected(true);
                pwindo.dismiss();
                break;

            case R.id.select_col_white:
                setNoteColor(R.color.circle_white, NoteColor.WHITE);
                break;

            case R.id.select_col_yellow:
                setNoteColor(R.color.circle_yellow, NoteColor.YELLOW);
                break;

            case R.id.select_col_blue:
                setNoteColor(R.color.circle_blue, NoteColor.BLUE);
                break;

            case R.id.select_col_green:
                setNoteColor(R.color.circle_green , NoteColor.GREEN);
                break;

            case R.id.select_col_orange:
                setNoteColor(R.color.circle_orange , NoteColor.ORANGE);
                break;

        }

    }




}
