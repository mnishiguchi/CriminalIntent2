package com.mnishiguchi.criminalintent2;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    public static final String EXTRA_CRIME_ID = "com.mnishiguchi.criminalintent2.crime_id";

    /**
     * Start CrimeActivity for the crime associated with the specified id.
     * @param packageContext
     * @param crimeId
     * @return intent
     */
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    /**
     * @return an instance of the fragment that the activity is hosting
     */
    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
