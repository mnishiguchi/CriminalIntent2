package com.mnishiguchi.criminalintent2;

import android.support.v4.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity {

    /**
     * @return an instance of the fragment that the activity is hosting
     */
    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
