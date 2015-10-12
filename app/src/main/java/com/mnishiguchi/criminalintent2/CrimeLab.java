package com.mnishiguchi.criminalintent2;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Centralize data storage
 */
public class CrimeLab {

    // Local storage for the crime data so that we need not access database many times.
    // Make sure that maintain the list when adding / deleting elements.
    private static List<Crime> mCrimes = Crime.listAll(Crime.class);

    // the CrimeLab singleton instance
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
     * Private constructor to create a CrimeLab singleton instance.
     * @param context
     */
    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
    }

    /**
     * Add a new crime to database
     */
    public static void addCrime(Crime crime) {
        crime.save();       // database
        mCrimes.add(crime); // local list
    }

    /**
     * Delete a crime from database
     */
    public static void deleteCrime(Crime crime) {
        crime.delete();        // database
        mCrimes.remove(crime); // local list
    }

    /**
     * Get a list of all the crimes from database
     */
    public static List<Crime> getCrimes() {
        return mCrimes;
    }

    /**
     * Get a specific crime.
     */
    public static Crime getCrime(String crimeId) {

        // Search in the local list
        for (Crime crime : mCrimes) {
            if (crime.getCrimeId().equals(crimeId)) {
                return crime;
            }
        }
        return null;
    }

    /**
     * @return the number of rows in the database
     */
    public static int getSize() {
        return mCrimes.size();
    }
}
