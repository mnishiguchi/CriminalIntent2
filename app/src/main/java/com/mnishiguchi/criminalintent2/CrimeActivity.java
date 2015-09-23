package com.mnishiguchi.criminalintent2;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    // Used for passing data through an intent.
    private static final String EXTRA_CRIME_ID = "com.mnishiguchi.criminalintent2.crime_id";

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
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}
