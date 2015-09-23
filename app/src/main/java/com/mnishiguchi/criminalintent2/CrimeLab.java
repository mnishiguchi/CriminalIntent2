package com.mnishiguchi.criminalintent2;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Centralize data storage
 */
public class CrimeLab {

    private List<Crime> mCrimes;

    // Storage for an instance of CrimeLab
    private static CrimeLab sCrimeLab;

    /**
     * Get access to the singleton CrimeLab object
     * @param context
     * @return a CrimeLab object
     */
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context.getApplicationContext() );
        }
        return sCrimeLab;
    }

    /**
     * Other classes will not be able to create a CrimeLab
     * @param context
     */
    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        createFakeCrimes();
    }

    /**
     * @return an ArrayList of Crime objects
     */
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    /**
     * @param id
     * @return a Crime object with the specified id
     */
    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id) ) {
                return crime;
            }
        }
        return null;
    }

    /**
     * For development only
     */
    private void createFakeCrimes() {
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);  // Every other one
            mCrimes.add(crime);
        }
    }
}
