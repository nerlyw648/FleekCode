package com.tyron.code.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

public class Utils {
    public static int getColorFromAttr(Context context, int attr) {
        final TypedValue typedValue = new TypedValue();
        final TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{attr});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }
}
