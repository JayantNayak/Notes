package com.example.jayant.notes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.jayant.notes.R;

public class HomeActivity extends AppCompatActivity {
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initActivityViews();

    }
    private void  initActivityViews(){
        mToolbar = (Toolbar) findViewById(R.id.activity_home_toolbar);
       /* if(mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("toolbar!");
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }*/
    }

}
