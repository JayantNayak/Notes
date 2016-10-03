package com.example.jayant.notes.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.jayant.notes.R;
import com.example.jayant.notes.adapters.HomeActivityRecyclerViewAdapter;
import com.example.jayant.notes.model.DBHelper;
import com.example.jayant.notes.model.HomeActivityCardDataObject;
import com.example.jayant.notes.model.NoteColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    static private final String TAG ="Home-Activity";
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private HomeActivityRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DBHelper noteDb;
    private ArrayList allNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        noteDb = new DBHelper(this);
       // allNotes = noteDb.getAllNotes();
        prepareSampleDataBaseSet();
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
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_home_card_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        //mAdapter =  new HomeActivityRecyclerViewAdapter(getDataSet());
        mAdapter =  new HomeActivityRecyclerViewAdapter(getAllDataBaseNotes());
        mRecyclerView.setAdapter(mAdapter);

    }
    private ArrayList<HomeActivityCardDataObject> getAllDataBaseNotes (){
        ArrayList<HomeActivityCardDataObject> results = new ArrayList<HomeActivityCardDataObject>();
        Map<String, ArrayList<String>> resultMap = noteDb.getAllNotes();

        String dateText[] = convertStringArrayListToStringArray(resultMap.get("dateTexList"));
        NoteColor cardCol[] = convertStringArrayListToNoteColorArray(resultMap.get("colorList"));
        boolean reminderBool[] = convertStringArrayListToBooleanArray(resultMap.get("reminderList"));
        boolean pinnedBool[] = convertStringArrayListToBooleanArray(resultMap.get("pinList"));
        String noteId[] = convertStringArrayListToStringArray(resultMap.get("idList"));

        for (int index = 0; index < dateText.length; index++) {
            Log.d(TAG, " adding data  " + index +" color " + cardCol[index]);
            HomeActivityCardDataObject obj = new HomeActivityCardDataObject(
                    index+dateText[index] +"..." ,
                    dateText[index],
                    reminderBool[index],
                    pinnedBool[index],
                    cardCol[index],noteId[index]);
            results.add(index, obj);

        }


        return results;

    }
    private String[] convertStringArrayListToStringArray(ArrayList<String> arrList){
        String resultList[] = new  String[arrList.size()]; ;
        for(int i=0;i<arrList.size(); i++ ){
            resultList[i] = arrList.get(i);
        }
        return resultList;
    }
    private boolean[] convertStringArrayListToBooleanArray(ArrayList<String> arrList){
        boolean resultList[] = new  boolean[arrList.size()]; ;
        for(int i=0;i<arrList.size(); i++ ){
            resultList[i] = Boolean.getBoolean(arrList.get(i));
        }
        return resultList;
    }
    private NoteColor[] convertStringArrayListToNoteColorArray(ArrayList<String> arrList){

        NoteColor resultList[] = new  NoteColor[arrList.size()];
        //NoteColor resultList[] = new  NoteColor[1]; ;
        for(int i=0;i<arrList.size(); i++ ){
           // Log.d(TAG," color type " + typeOf(arrList.get(i)));
            if( arrList.get(i).equals("ORANGE")){
                resultList[i] = NoteColor.ORANGE;
            }
            else if(arrList.get(i).equals("GREEN")){
                resultList[i] = NoteColor.GREEN;
            }
            else if(arrList.get(i).equals("YELLOW")){
                resultList[i] = NoteColor.YELLOW;
            }
            else if(arrList.get(i).equals("BLUE")){
                resultList[i] = NoteColor.BLUE;
            }
            else if(arrList.get(i).equals("WHITE")){
                resultList[i] = NoteColor.WHITE;
            }


        }

        return resultList;
    }



    private ArrayList<HomeActivityCardDataObject> getDataSet() {


        ArrayList results = new ArrayList<HomeActivityCardDataObject>();

        ArrayList<String[]> cardText = getCardText("hi");
        String previewText[] = cardText.get(0);
        String dateText[] = cardText.get(1);


        ArrayList<boolean[]> cardBools = getCardBool("hi");
        boolean reminderBool[] = cardBools.get(0);
        boolean pinnedBool[] = cardBools.get(1);

        ArrayList<NoteColor[]> cardColor = getCardColor("hi");
        NoteColor cardCol[] = cardColor.get(0);

    /*

        for (int index = 0; index < previewText.length; index++) {
            HomeActivityCardDataObject obj = new HomeActivityCardDataObject(
                                                index+previewText[index] +"..." ,
                                               dateText[index],
                                                reminderBool[index],
                                                pinnedBool[index],
                                                cardCol[index]);
            results.add(index, obj);

            Log.d(TAG, " adding data  " + index);
        }
        */

        return results;
    }
    private void prepareSampleDataBaseSet() {
        noteDb.dropTable();
        noteDb.createTbale();

        ArrayList results = new ArrayList<HomeActivityCardDataObject>();

        ArrayList<String[]> cardText = getCardText("hi");
        String previewText[] = cardText.get(0);
        String dateText[] = cardText.get(1);


        ArrayList<boolean[]> cardBools = getCardBool("hi");
        boolean reminderBool[] = cardBools.get(0);
        boolean pinnedBool[] = cardBools.get(1);

        ArrayList<NoteColor[]> cardColor = getCardColor("hi");
        NoteColor cardCol[] = cardColor.get(0);

        String sample = "Helllooo every one";
        byte value[] = sample.getBytes();

        for (int index = 0; index < dateText.length; index++) {
            noteDb.insertNote(value,dateText[index], cardCol[index].toString(),String.valueOf(reminderBool[index]), String.valueOf(pinnedBool[index]));
            Log.d(TAG, " inserting data  " + index);
        }



    }



    private ArrayList<String[]> getCardText(String stockLoadId){
        String[] previewList = {
                "zero",
                "one",
                "two",
                "three",
                "four",
                "five",
                "six",
                "seven",
                "eight",
                "nine",
                "ten",
                "eleven",
                "tweleve",
                "thirteen",
                "fourteen",
                "fiveteen",
                "sixteen",
                "seventeen",
                "eighteen",
                "nineteen",
                "twenty",
                "twenty one",
                "twenty two",
                "twenty three",
                "twenty four"

        };
        String[] indexList = {
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20",
                "21",
                "22",
                "23",
                "24"
        };


        String [] dateTexList = {
                "00/01",
                "01/01",
                "02/01",
                "03/01",
                "04/01",
                "05/01",
                "06/01",
                "07/01",
                "08/01",
                "09/01",
                "10/01",
                "11/01",
                "12/01",
                "13/01",
                "14/01",
                "15/01",
                "16/01",
                "17/01",
                "18/01",
                "19/01",
                "20/01",
                "21/01",
                "22/01",
                "23/01",
                "24/01"
        };




        ArrayList<String[]> list = new ArrayList<String[]>();
        list.add(previewList);
        list.add(dateTexList);

        return list;


    }
    private ArrayList<boolean[]> getCardBool(String stockLoadId){
        boolean reminderList[] = {
                true,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false
        };

        boolean pinList[] = {
                true,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                false,
                false,
                false,
                false

        };

        ArrayList<boolean[]> list = new ArrayList<boolean[]>();
        list.add(reminderList);
        list.add(pinList);
        return list;

    }

    private ArrayList<NoteColor[]> getCardColor(String stockLoadId){

        NoteColor[] colorList = {
                NoteColor.ORANGE,
                NoteColor.GREEN,
                NoteColor.YELLOW,
                NoteColor.BLUE,
                NoteColor.WHITE,

                NoteColor.ORANGE,
                NoteColor.GREEN,
                NoteColor.YELLOW,
                NoteColor.BLUE,
                NoteColor.WHITE,

                NoteColor.ORANGE,
                NoteColor.GREEN,
                NoteColor.YELLOW,
                NoteColor.BLUE,
                NoteColor.WHITE,

                NoteColor.ORANGE,
                NoteColor.GREEN,
                NoteColor.YELLOW,
                NoteColor.BLUE,
                NoteColor.WHITE,

                NoteColor.WHITE,
                NoteColor.WHITE,
                NoteColor.YELLOW,
                NoteColor.WHITE,
                NoteColor.WHITE
        };
        ArrayList<NoteColor[]> list = new ArrayList<NoteColor[]>();
        list.add(colorList);
        return list;
    }




}
