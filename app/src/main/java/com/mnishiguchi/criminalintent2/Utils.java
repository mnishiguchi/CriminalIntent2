package com.mnishiguchi.criminalintent2;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by masa on 9/26/15.
 */
public class Utils {

    public static void toast(Activity a, String msg) {
        Toast.makeText(a, msg, Toast.LENGTH_SHORT).show();
    }

}
