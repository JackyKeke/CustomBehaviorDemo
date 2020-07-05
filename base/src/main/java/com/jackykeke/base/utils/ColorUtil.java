package com.jackykeke.base.utils;

import android.graphics.Color;

public class ColorUtil {


    public static int getTranslucentColor(float percent, int rgb) {
        int blue = Color.blue(rgb);
        int green = Color.green(rgb);
        int red = Color.red(rgb);
        int alpha = Color.alpha(rgb);

        alpha = Math.round(alpha * percent);
        return Color.argb(alpha, red, green, blue);
    }

}
