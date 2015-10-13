package com.mnishiguchi.criminalintent2;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by masa on 9/26/15.
 */
public class Utils {

    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Return a friendly untitled string in case that a passed-in string is empty.
     */
    public static String checkEmptyTitle(Context context, String str) {
        return TextUtils.isEmpty(str) ?
                context.getString(android.R.string.untitled) : str;
    }
}


