package com.example.jayant.notes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.jayant.notes.R;

public class NoteDetailActivity extends AppCompatActivity {
    private  Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        initActivityView();
    }


    private void initActivityView(){
        mToolbar = (Toolbar) findViewById(R.id.activity_note_detail_toolbar);
        mToolbar.inflateMenu(R.menu.menu_activity_note_detail_toolbar);
       /* if(mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("toolbar!");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
    }
}
