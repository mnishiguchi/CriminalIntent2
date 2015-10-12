package com.mnishiguchi.criminalintent2;
import android.content.Context;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Represents a crime case.
 * SugarRecord handles the database creation and provides us with some convenient methods.
 *
 * Created by masa on 9/7/15.
 */
public class Crime extends SugarRecord<Crime> {

    // Local storage for the crime data so that we need not access database many times.
    // Make sure that maintain the list when adding / deleting elements.
    private static List<Crime> sCrimes = listAll(Crime.class);

    private String crimeId;
    private String title;
    private Date date;
    private boolean isSolved;

    public Crime() {
        this.crimeId = UUID.randomUUID().toString();  // Generate unique identifier
        this.date    = new Date();                    // The date a crime occurred
    }

    //==> DB access static methods

    /**
     * Get a list of all the crimes from database
     */
    public static List<Crime> getAll() {
        return sCrimes;
    }

    /**
     * Get a specific crime.
     */
    public static Crime get(String crimeId) {

        // Search in the local list
        for (Crime crime: sCrimes) {
            if (crime.getCrimeId().equals(crimeId)) {
                return crime;
            }
        }
        return null;
    }

    /**
     * Add a new crime to database
     */
    public static void add(Crime crime) {
        crime.save();       // database
        sCrimes.add(crime); // local list
    }

    /**
     * Delete a crime from database
     */
    public static void delete(Crime crime) {
        crime.delete();        // database
        sCrimes.remove(crime); // local list
    }

    /**
     * @return the number of rows in the database
     */
    public static int getSize() {
        return sCrimes.size();
    }

    /* Some db related methods provided by SugarRecord
        - crime.save();
        - crime.delete();
        - crime.getID();
        - Crime.findById(Crime.class, id);
    */

    //==> Getters and setters

    public String getCrimeId() {
        return crimeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param c context
     * @return formatted date string
     */
    public String getDateString(Context c) {
        java.text.DateFormat df = android.text.format.DateFormat.getLongDateFormat(c);
        return df.format(date);
    }

    /**
     * @param c context
     * @return formatted time string
     */
    public String getTimeString(Context c) {
        java.text.DateFormat df = android.text.format.DateFormat.getTimeFormat(c);
        return df.format(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        this.isSolved = solved;
    }
}
