package com.mnishiguchi.criminalintent2;

import android.support.v4.app.Fragment;

/**
 * Created by masa on 9/13/15.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    /**
     * @return an instance of the fragment that the activity is hosting
     */
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
