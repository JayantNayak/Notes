package com.example.jayant.notes.activities;

import android.content.ClipData;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jayant.notes.R;
import com.example.jayant.notes.customwidgets.EditTextWithFont;
import com.example.jayant.notes.customwidgets.EditTextWithFontUndoRedo;
import com.example.jayant.notes.interfaces.Changeable;
import com.example.jayant.notes.model.NoteColor;
import com.example.jayant.notes.utils.ChangeManager;
import com.example.jayant.notes.utils.ImgUtils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class NoteDetailActivity extends AppCompatActivity implements View.OnClickListener {
    static private final String TAG = "NoteDetails-Activity";
    static private final int RESULT_LOAD_IMAGE = 1;
    static private final int RESULT_CAPTURE_IMAGE = 2;

    private Toolbar mToolbar;
    private PopupWindow pwindo;
    private ImageView btnClosePopup;
    private ImageView mCircleWhite;
    private ImageView mCircleYellow;
    private ImageView mCircleBlue;
    private ImageView mCircleGreen;
    private ImageView mCircleOrange;
    private ImageView mUndoBtn;
    private ImageView mRedoBtn;
    private ImageView mCameraBtn;
    private ImageView mGalleryBtn;
    private ImageView mCheckBoxBtn;
    private EditTextWithFont mNoteEditor;
    private EditTextWithFontUndoRedo mNoteUndoRedoEditor;
    private NoteColor mNoteCol;


    private int imgSpanWidth = 400, imgSpanHeight = 500, borderPadding = 10;
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
        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.circle_white));
       // mNoteCol = NoteColor.WHITE;

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

        mUndoBtn = (ImageView) findViewById(R.id.activity_note_detail_footer_undo);
        mUndoBtn.setOnClickListener(this);

        mRedoBtn = (ImageView) findViewById(R.id.activity_note_detail_footer_redo);
        mRedoBtn.setOnClickListener(this);

        mCameraBtn = (ImageView) findViewById(R.id.activity_note_detail_footer_camera);
        mCameraBtn.setOnClickListener(this);

        mGalleryBtn = (ImageView) findViewById(R.id.activity_note_detail_footer_gallery);
        mGalleryBtn.setOnClickListener(this);

        mCheckBoxBtn = (ImageView) findViewById(R.id.activity_note_detail_footer_checkBox);
        mCheckBoxBtn.setOnClickListener(this);

        mNoteEditor = (EditTextWithFont) findViewById(R.id.acivity_note_details_editor);
        //EditTextWithFont noteEdit = (EditTextWithFont)findViewById(R.id.acivity_note_details_editor);
        mNoteUndoRedoEditor = new EditTextWithFontUndoRedo(mNoteEditor);
        mNoteEditor.setMovementMethod(LinkMovementMethod.getInstance());

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            mNoteCol = convertToNoteColor(extras.getString("mNoteColor"));

            switch (mNoteCol) {
                case WHITE:
                    setNoteToolBarColor(R.color.circle_white);

                    break;

                case YELLOW:
                    setNoteToolBarColor(R.color.circle_yellow);

                    break;

                case BLUE:
                    setNoteToolBarColor(R.color.circle_blue);

                    break;

                case GREEN:
                    setNoteToolBarColor(R.color.circle_green);

                    break;

                case ORANGE:
                    setNoteToolBarColor(R.color.circle_orange);

                    break;

            }
        }


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
            //btnClosePopup.setOnTouchListener(this);
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
    private NoteColor convertToNoteColor(String color){
        NoteColor result = null;

            if( color.equals("ORANGE")){
                result = NoteColor.ORANGE;
            }
            else if(color.equals("GREEN")){
                result = NoteColor.GREEN;
            }
            else if(color.equals("YELLOW")){
                result = NoteColor.YELLOW;
            }
            else if(color.equals("BLUE")){
                result = NoteColor.BLUE;
            }
            else if(color.equals("WHITE")){
                result = NoteColor.GREEN;
            }
        Log.d(TAG, "color card: " + result);

        return result;
    }




    private void setAllDefaultCircles() {
        // set all circles to default unelected color state
        mCircleWhite.setImageResource(R.drawable.white_circle_selector);

        mCircleYellow.setImageResource(R.drawable.yellow_circle_selector);

        mCircleBlue.setImageResource(R.drawable.blue_circle_selector);

        mCircleGreen.setImageResource(R.drawable.green_circle_selector);

        mCircleOrange.setImageResource(R.drawable.orange_circle_selector);


    }

    private void setNoteColor(int color, NoteColor colr) {
        //Toast.makeText(this, " color clicked : ", Toast.LENGTH_SHORT).show();
        pwindo.dismiss();
        mToolbar.setBackgroundColor(ContextCompat.getColor(this, color));
        mNoteCol = colr;
    }
    private void setNoteToolBarColor(int color) {

        mToolbar.setBackgroundColor(ContextCompat.getColor(this, color));

    }

    @Override
    public void onClick(View v) {
        //Log.d(TAG, "  clicked on  " + v.getId());
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
                setNoteColor(R.color.circle_green, NoteColor.GREEN);
                break;

            case R.id.select_col_orange:
                setNoteColor(R.color.circle_orange, NoteColor.ORANGE);
                break;
            case R.id.activity_note_detail_footer_gallery:

                // note the default gallery in the phone always doesn't allow to select multiples images
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

              /*  Intent intent = new Intent();
                intent.setType("image*//**//*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);*/
                break;

            case R.id.activity_note_detail_footer_camera:


                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RESULT_CAPTURE_IMAGE);
                break;

            case R.id.activity_note_detail_footer_undo:
                Log.d(TAG, "  UNDO  ");
                mNoteUndoRedoEditor.undo();
                break;
            case R.id.activity_note_detail_footer_redo:
                Log.d(TAG, "  REDO  ");
                mNoteUndoRedoEditor.redo();
                break;


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_CAPTURE_IMAGE && resultCode == RESULT_OK && null != data) {

            Log.d(TAG, "Captured Image ");
            Bitmap b = (Bitmap) data.getExtras().get("data");

            b = Bitmap.createScaledBitmap(b, imgSpanWidth - borderPadding, imgSpanHeight - borderPadding, false);

            Bitmap img = ImgUtils.addWhiteBorder(b, borderPadding);

            final Drawable d = new BitmapDrawable(getResources(), img);
            d.setBounds(0, 0, imgSpanWidth, imgSpanHeight);
            final ImageSpan imgSpan = new ImageSpan(d);

            SpannableStringBuilder ss = new SpannableStringBuilder(".\n");
            ss.setSpan(imgSpan, 0, (".\n").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new NoteDetailsClickAbleSpan(), 0, (".\n").length(), 0);

            int start = Math.max(mNoteEditor.getSelectionStart(), 0);
            int end = Math.max(mNoteEditor.getSelectionEnd(), 0);
            Log.d(TAG, "Start " + start + "End " + end);
            int offsetStart = Math.min(start, end);
            int offsetEnd = Math.max(start, end);

            if (offsetStart != 0) {
                String str = "\n";
                int len = str.length();
                mNoteEditor.append(str, 0, len);
                Log.d(TAG, "append  newline");
                offsetStart = offsetStart + len;
                offsetEnd = offsetEnd + len;

            }

            mNoteEditor.getText().replace(offsetStart, offsetEnd, ss, 0, ss.length());

        }
        // get gallery images
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            /*Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();*/

             /*   Uri selectedImageUri = data.getData();
            String[] projection = { MediaStore.MediaColumns.DATA };
            CursorLoader cursorLoader = new CursorLoader(this,selectedImageUri, projection, null, null, null);
            Cursor cursor =cursorLoader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String selectedImagePath = cursor.getString(column_index);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.selectImage);
            imageView.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));*/


            ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
            if (data.getData() != null) {

                Uri mImageUri = data.getData();
                Log.d(TAG, "Selected Images " + "1");
                mArrayUri.add(mImageUri);

            } else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    if (data.getClipData() != null) {


                        ClipData mClipData = null;
                        mClipData = data.getClipData();


                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);

                        }
                        Log.d(TAG, "Selected Images" + mArrayUri.size());
                    }
                }

            }

            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            CursorLoader cursorLoader = null;
            String textString = "";

            ArrayList<String> mArrayImagePath = new ArrayList<String>();
            for (int i = 0; i < mArrayUri.size(); i++) {

                cursorLoader = new CursorLoader(this, mArrayUri.get(i), projection, null, null, null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                mArrayImagePath.add(selectedImagePath);
                textString += "\n\n" + selectedImagePath;
                cursor.close();
            }
            TextView imagetext = (TextView) findViewById(R.id.selectImagesText);
            imagetext.setText(textString);


            boolean once = true;
            int startPos = 0;
            boolean prevNewLine = false;
            //Fixing allignment issues when adding a new image inside the edit text
            int alignCase = 0;

            int start1 = Math.max(mNoteEditor.getSelectionStart(), 0);
            int end1 = Math.max(mNoteEditor.getSelectionEnd(), 0);

            int offsetStart1 = Math.min(start1, end1);
            int offsetEnd1 = Math.max(start1, end1);
            if (offsetStart1 == offsetEnd1) {
                alignCase = 1;

            } else {
                alignCase = 2;
            }
            for (int i = 0; i < mArrayImagePath.size(); i++) {


                Bitmap b = ImgUtils.decodeSampledBitmapFromResource(mArrayImagePath.get(i), imgSpanWidth - borderPadding, imgSpanHeight - borderPadding);
                Bitmap img = ImgUtils.addWhiteBorder(b, borderPadding);

                final Drawable d = new BitmapDrawable(getResources(), img);
                d.setBounds(0, 0, imgSpanWidth, imgSpanHeight);
                final ImageSpan imgSpan = new ImageSpan(d);


                SpannableStringBuilder ss = new SpannableStringBuilder(".\n");
                ss.setSpan(imgSpan, 0, (".\n").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new NoteDetailsClickAbleSpan(mArrayImagePath.get(i)), 0, (".\n").length(), 0);

                //mNoteEditor.append(ss, 0, (".\n").length());

                int start = Math.max(mNoteEditor.getSelectionStart(), 0);
                int end = Math.max(mNoteEditor.getSelectionEnd(), 0);
                Log.d(TAG, "Start " + start + "End " + end);
                int offsetStart = Math.min(start, end);
                int offsetEnd = Math.max(start, end);

                if (alignCase == 1) {
                    Log.d(TAG, "alignCASE 1");
                    if (offsetStart != 0 && !prevNewLine) {
                        String str = "\n";
                        int len = str.length();
                        mNoteEditor.append(str, 0, len);
                        Log.d(TAG, "append  newline");
                        offsetStart = offsetStart + len;
                        offsetEnd = offsetEnd + len;

                        if (once) {
                            startPos = offsetStart;
                            once = false;
                        }

                    }


                    mNoteEditor.getText().replace(offsetStart, offsetEnd, ss, 0, ss.length());
                    if (offsetStart == 0) {
                        //mNoteEditor.getText().insert(offsetEnd, "\n");
                        mNoteEditor.getText().insert(offsetEnd, "\n");
                        Log.d(TAG, "insert  newline");
                        offsetStart = 1;
                        prevNewLine = true;
                    } else {
                        prevNewLine = false;
                    }


                } else if (alignCase == 2) {
                    Log.d(TAG, "alignCASE 2");


                    mNoteEditor.getText().replace(offsetStart, offsetEnd, ss, 0, ss.length());

                }


            }
            // mNoteEditor.setMovementMethod(LinkMovementMethod.getInstance());
            if (startPos != 0) {
                mNoteEditor.getText().insert(startPos, "\n");
            }

        }

    }

    public class NoteDetailsClickAbleSpan extends ClickableSpan {

        String filePath = null;

        public NoteDetailsClickAbleSpan(String filePath) {
            this.filePath = filePath;
        }

        public NoteDetailsClickAbleSpan() {

        }

        @Override
        public void onClick(View widget) {
            if (filePath != null) {
                Toast.makeText(NoteDetailActivity.this, filePath, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(NoteDetailActivity.this, "Camera Image", Toast.LENGTH_LONG).show();

            }

        }
    }




}
