package com.mnishiguchi.criminalintent2;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Centralizes data management, handling CRUD operations on database.
 * Works as an in-memory object store.
 */
public class CrimeLab {

    // the CrimeLab singleton instance
    private static CrimeLab sCrimeLab;

    // Local storage for the crime data so that we need not access database many times.
    // Make sure that maintain the list when adding / deleting elements.
    private static List<Crime> mCrimes = Crime.listAll(Crime.class);

    private Context mAppContext;

    /**
     * Gets access to the singleton CrimeLab object
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

        Utils.toast(mAppContext, checkEmptyTitle(crime) + " saved");
    }

    /**
     * Update a crime to database
     */
    public void updateCrime(Crime crime) {
        crime.save();  // database

        // Update the local list
        for (int i = 0, size = getSize(); i < size; i++) {
            if (mCrimes.get(i).getCrimeId().equals(crime.getCrimeId())) {
                mCrimes.set(i, crime);
            }

            Utils.toast(mAppContext, checkEmptyTitle(crime) + " updated");
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
     * Gets a list of all the crimes from database
     */
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    /**
     * Gets a specific crime.
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
    public int getSize() {
        return mCrimes.size();
    }

    /**
     * Return a friendly untitled string in case that title is empty.
     */
    public String checkEmptyTitle(Crime crime) {
        return TextUtils.isEmpty(crime.getTitle()) ?
                mAppContext.getString(android.R.string.untitled) : crime.getTitle();
    }
}
