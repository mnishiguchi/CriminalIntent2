package com.mnishiguchi.criminalintent2;

import android.content.Context;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mnishiguchi.criminalintent2.Crime.*;

/**
 * Centralize data storage
 */
public class CrimeLab {

    // A snapshot of the crimes list
    private List<Crime> mCrimes;

    // A CrimeLab singleton instance
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
        mCrimes = listAll(Crime.class);
    }

    /**
     * Add a new crime to daatabase
     * @param c
     */
    public void addCrime(Crime c) {
        c.save();
        mCrimes.add(c);
    }

    /**
     * Remove the specified crime from the CrimeLab's list
     * @param c
     */
    public void deleteCrime(Crime c) {
        c.delete();
        mCrimes.remove(c);
    }

    /**
     * @return an ArrayList of Crime objects
     */
    public List<Crime> getCrimes() {
        mCrimes = listAll(Crime.class);
        return mCrimes;
    }

    /**
     * @param id
     * @return a Crime object with the specified id
     */
    public Crime getCrime(String id) {
        return Select.from(Crime.class)
                .where( Condition.prop("UUID").eq(id) )
                .list().get(0);
    }
}
