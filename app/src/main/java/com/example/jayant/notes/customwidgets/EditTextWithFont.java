package com.example.jayant.notes.customwidgets;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Selection;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

import com.example.jayant.notes.R;
import com.example.jayant.notes.activities.NoteDetailActivity;
import com.example.jayant.notes.utils.NotesUtils;

/**
 * <b>@author 55420.</b><br>
 * <b>com.m.qr.customwidgets</b><br>
 * 02/03/2015<br>
 * jibythomas@qatarairways.com.qa
 */
public class EditTextWithFont extends EditText{
    static private final String TAG = "EditTextWithFont";
    private int defaultDimension = 0;

    private int TYPE_BOLD = 1;

    private int TYPE_ITALIC = 2;

    private int FONT_ARIAL = 1;

    private int FONT_OPEN_SANS = 2;

    private int fontType;

    private int fontName;

    public EditTextWithFont(Context context) {
        super(context);
        init(null, 0);
    }

    public EditTextWithFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public EditTextWithFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.font, defStyle, 0);

        fontName = a.getInt(R.styleable.font_name, defaultDimension);
        fontType = a.getInt(R.styleable.font_type, defaultDimension);

        a.recycle();

        if (fontName == FONT_ARIAL) {
            Typeface arialFont = Typeface
                    .createFromAsset(getContext().getAssets(), NotesUtils.FONT_ARIAL);
            setFontType(arialFont);
        } else if (fontName == FONT_OPEN_SANS) {
            Typeface openSansFont = Typeface
                    .createFromAsset(getContext().getAssets(), NotesUtils.FONT_OPEN_SANS);
            setFontType(openSansFont);
        } else {
            Typeface arialFont = Typeface
                    .createFromAsset(getContext().getAssets(), NotesUtils.FONT_ARIAL);
            setFontType(arialFont);
        }
    }

    private void setFontType(Typeface font) {
        if (fontType == TYPE_BOLD) {
            setTypeface(font, Typeface.BOLD);
        } else if (fontType == TYPE_ITALIC) {
            setTypeface(font, Typeface.ITALIC);
        } else {
            setTypeface(font);
        }
    }



    public void deletingData() {

        Log.d(TAG, "  deleted  ");

        //Editable buffer = ((EditText) view).getText();
        Editable buffer = EditTextWithFont.this.getText();
        // If the cursor is at the end of a NoteDetailsClickAbleSpan then remove the whole span
        int start = Selection.getSelectionStart(buffer);
        int end = Selection.getSelectionEnd(buffer);
        if (start == end) {
            NoteDetailActivity.NoteDetailsClickAbleSpan[] link = buffer.getSpans(start, end, NoteDetailActivity.NoteDetailsClickAbleSpan.class);
            if (link.length > 0) {
                buffer.replace(
                        buffer.getSpanStart(link[0]),
                        buffer.getSpanEnd(link[0]),
                        ""
                );
                buffer.removeSpan(link[0]);
               // return true;
            }
        }

    }


    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new EditTextWithFontInputConnection(super.onCreateInputConnection(outAttrs),
                true);
    }

    private class EditTextWithFontInputConnection extends InputConnectionWrapper {

        public EditTextWithFontInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN){

                if(event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    EditTextWithFont.this.deletingData();
                    Log.d(TAG, "  clicked on  ");
                    // Un-comment if you wish to cancel the backspace:
                    // return false;
                }
                else if(event.getKeyCode() != KeyEvent.KEYCODE_DEL){
                    Log.d(TAG, "  seperate key ");

                }
                Log.d(TAG, "  key down ");

            }


            return super.sendKeyEvent(event);
        }


        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            // magic: in latest Android, deleteSurroundingText(1, 0) will be called for backspace
            if (beforeLength == 1 && afterLength == 0) {
                // backspace
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                        && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
            }

            return super.deleteSurroundingText(beforeLength, afterLength);
        }

    }
}
