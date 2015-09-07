package com.mnishiguchi.criminalintent2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        // Call `getSupportFragmentManager()` because we are using the support library
        FragmentManager fm = getSupportFragmentManager();

        // A container view ID serves two purposes:
        // 1. Tells the FragmentManager where in the activity's view the fragment's view should appear.
        // 2. Used as a unique identifier for a fragment in the FragmentManager's list.
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // Fragment transactions are used to add, remover, attach, detach, or
        // replace fragments in the fragment list.
        if (fragment == null) {
            fragment = new CrimeFragment();

            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
