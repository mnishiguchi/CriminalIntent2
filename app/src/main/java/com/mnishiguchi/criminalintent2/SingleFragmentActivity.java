package com.mnishiguchi.criminalintent2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Generic activity superclass for hosting a single fragment
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    /**
     * Subclasses of SingleFragmentActivity must implement this method
     * @return an instance of the fragment that the activity is hosting
     */
    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        // Call `getSupportFragmentManager()` because we are using the support library
        FragmentManager fm = getSupportFragmentManager();

        // A container view ID serves two purposes:
        // 1. Tells the FragmentManager where in the activity's view the fragment's view should appear.
        // 2. Used as a unique identifier for a fragment in the FragmentManager's list.
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // Fragment transactions are used to add, remover, attach, detach, or
        // replace fragments in the fragment list.
        if (fragment == null) {

            // Instantiate the fragment that the activity is hosting
            fragment = createFragment();

            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
