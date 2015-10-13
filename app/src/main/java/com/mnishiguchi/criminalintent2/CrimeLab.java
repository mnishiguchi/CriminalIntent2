package com.mnishiguchi.criminalintent2;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Centralizes data management, handling CRUD operations on database.
 * Works as an in-memory object store.
 */
public class CrimeLab {

    // the CrimeLab singleton instance
    private static CrimeLab sCrimeLab;

    // In-memory storage for the crime data.
    // Make sure that maintain the list when adding / deleting elements.
    private List<Crime> mCrimes = Crime.listAll(Crime.class);

    private Context mAppContext;

    /**
     * Provides access to the CrimeLab singleton instance.
     * @param context
     * @return a reference to the CrimeLab singleton instance
     */
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context.getApplicationContext() );
        }
        return sCrimeLab;
    }

    /**
     * Private constructor to create a CrimeLab singleton instance.
     * @param appContext
     */
    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mCrimes = new ArrayList<>();
    }

    /**
     * Add a new crime to database
     */
    public void addCrime(Crime crime) {
        crime.save();       // database
        mCrimes.add(crime); // local list

        String title = Utils.checkEmptyTitle(mAppContext, crime.getTitle());
        Utils.toast(mAppContext, title + " saved");
    }

    /**
     * Update a crime to database
     */
    public void updateCrime(Crime crime) {
        crime.save();  // database

        // Update the local list
        for (int i = 0, size = getCount(); i < size; i++) {
            if (mCrimes.get(i).getCrimeId().equals(crime.getCrimeId())) {
                mCrimes.set(i, crime);

                String title = Utils.checkEmptyTitle(mAppContext, crime.getTitle());
                Utils.toast(mAppContext, title + " updated");
                return;
            }
        }
    }

    /**
     * Deletes a crime from database
     */
    public void deleteCrime(Crime crime) {
        String title = crime.getTitle();

        crime.delete();        // database
        mCrimes.remove(crime); // local list

        Utils.toast(mAppContext, title + " deleted");
    }

    /**
     * @return a reference to the in-memory list of all the crimes.
     */
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    /**
     * @return the specified crime.
     */
    public Crime getCrime(String crimeId) {

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
    public int getCount() {
        return (int) Crime.count(Crime.class, null, null, null, null, null);
    }
}
