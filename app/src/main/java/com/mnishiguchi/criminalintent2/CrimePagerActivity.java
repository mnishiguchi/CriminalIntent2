package com.mnishiguchi.criminalintent2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends FragmentActivity {
    /*
    ViewPager
    - only available in the support library.
    - Requires a PagerAdapter
    - Use FragmentStatePagerAdapter, a subclass of PagerAdapter to take care of the details
     */

    // Used for passing data through an intent.
    private static final String EXTRA_CRIME_ID = "com.mnishiguchi.criminalintent2.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    /**
     * Start CrimePagerActivity for the crime associated with the specified id.
     * @param packageContext
     * @param crimeId
     * @return intent
     */
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        // Get the crime id to determine the initial position.
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        // Inflate the pager.
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        // Get the list of the crimes and set up the pager adapter.
        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            /**
             * Fetch the Crime instance from the given position in the data set and
             * create an instance of CrimeFragment based on that Crime instance.
             * @param position
             * @return a properly configured CrimeFragment.
             */
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            /**
             * @return the number of crimes in the list.
             */
            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        // Set the initial pager item.
        for (int i = 0; i < mCrimes.size(); i++) {
            // Search for the item with the given ID in the list and
            // set it as the initial item to display.
            if (mCrimes.get(i).getId().equals(crimeId) ) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
