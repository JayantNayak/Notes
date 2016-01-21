package com.example.jayant.notes.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import java.util.Set;

/**
 * Created by OM on 22-11-2015.
 */
public class NotesUtils {

    public static String FONT_ARIAL = "ArialRegular.ttf";
    public static String FONT_OPEN_SANS = "OpenSansLight.ttf";

    public static float convertPixelsToDp(float px, Resources resources) {
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;

    }

    public static float convertDpToPixel(float dp, Resources resources) {
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static void applyScreenFont(DisplayMetrics dm, Context context,
                                       Set<Integer> nonBoldElements,
                                       ViewGroup containerLayout) {

        if (null != dm && null != context) {
            if (DisplayMetrics.DENSITY_LOW != dm.densityDpi) {
                Typeface mFont = Typeface
                        .createFromAsset(context.getAssets(), "fonts/HelveticaNeueLTStd-Md.otf");
                Typeface mFontBold = Typeface
                        .createFromAsset(context.getAssets(), "fonts/HelveticaNeueLTStd-Bd.otf");
            }
        }
    }




    public static LinearLayout.LayoutParams  getLinearLayoutParamWithMargins(int leftDp, int topDp, int rightDp, int bottomDp, Resources resources) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins((int) convertDpToPixel(leftDp, resources),
                (int) convertDpToPixel(topDp, resources), (int) convertDpToPixel(rightDp, resources),
                (int) convertDpToPixel(bottomDp, resources));

        return params;
    }

    public static LinearLayout.LayoutParams  getLinearLayoutParamWithMargin(int margin, Resources resources) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        margin = (int) convertDpToPixel(margin, resources);
        params.setMargins(margin, margin, margin, margin);

        return params;
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (null != activity
                && null != activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE)) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (null != inputMethodManager
                    && null != activity.getCurrentFocus()) {
                inputMethodManager.hideSoftInputFromWindow(activity
                        .getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

}
