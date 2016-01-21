package com.example.jayant.notes.customwidgets;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.jayant.notes.R;
import com.example.jayant.notes.utils.NotesUtils;


public class ButtonWithFont extends Button {


    private int defaultDimension = 0;

    private int TYPE_BOLD = 1;

    private int TYPE_ITALIC = 2;

    private int FONT_ARIAL = 1;

    private int FONT_OPEN_SANS = 2;

    private int fontType;

    private int fontName;

    public ButtonWithFont(Context context) {
        super(context);
        init(null, 0);
    }

    public ButtonWithFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ButtonWithFont(Context context, AttributeSet attrs, int defStyle) {
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
        if (!isInEditMode()) {
            if (fontName == FONT_ARIAL) {
                Typeface arialFont = Typeface
                        .createFromAsset(getContext().getAssets(), NotesUtils.FONT_ARIAL);
                setFontType(arialFont);
            } else if (fontName == FONT_OPEN_SANS) {
                Typeface openSansFont = Typeface
                        .createFromAsset(getContext().getAssets(), NotesUtils.FONT_OPEN_SANS);
                setFontType(openSansFont);
            }
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

    @Override
    public void setBackgroundDrawable(Drawable d) {
        CustomButtonBackgroundDrawable layer = new CustomButtonBackgroundDrawable(d);
        super.setBackgroundDrawable(layer);
    }


    protected class CustomButtonBackgroundDrawable extends LayerDrawable {

        protected ColorFilter pressedFilter = new LightingColorFilter(Color.LTGRAY, 1);

        protected int disabledAlpha = 100;

        public CustomButtonBackgroundDrawable(Drawable d) {
            super(new Drawable[]{d});
        }

        @Override
        protected boolean onStateChange(int[] states) {
            boolean enabled = false;
            boolean pressed = false;

            for (int state : states) {
                if (state == android.R.attr.state_enabled) {
                    enabled = true;
                } else if (state == android.R.attr.state_pressed) {
                    pressed = true;
                }
            }

            mutate();
            if (enabled && pressed) {
                setColorFilter(pressedFilter);
            } else if (!enabled) {
                setColorFilter(null);
                setAlpha(disabledAlpha);
            } else {
                setColorFilter(null);
            }

            invalidateSelf();

            return super.onStateChange(states);
        }

        @Override
        public boolean isStateful() {
            return true;
        }
    }

}
