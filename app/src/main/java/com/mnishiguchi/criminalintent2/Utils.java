package com.mnishiguchi.criminalintent2;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by masa on 9/26/15.
 */
public class Utils {

    public static void toast(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }

}
