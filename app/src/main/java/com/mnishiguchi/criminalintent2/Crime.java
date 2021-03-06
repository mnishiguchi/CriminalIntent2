package com.mnishiguchi.criminalintent2;
import android.content.Context;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.UUID;

/**
 * Represents a crime case.
 * SugarRecord handles the database creation and provides us with some convenient methods.
 * Database access is always performed through CrimeLab.
 *
 * Created by masa on 9/7/15.
 */
public class Crime extends SugarRecord<Crime> {

    private String crimeId;
    private String title;
    private Date date;
    private boolean isSolved;

    public Crime() {
        this.crimeId = UUID.randomUUID().toString();  // Generate unique identifier
        this.date    = new Date();                    // The date a crime occurred
    }

    /* Some db related methods provided by SugarRecord.
    In this app, accessing database is performed through CrimeLab.
    So do not directly manipulate database from anywhere else.
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
