package com.mnishiguchi.criminalintent2;
import android.content.Context;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.UUID;

/**
 * Created by masa on 9/7/15.
 */
public class Crime extends SugarRecord<Crime> {

    private String mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        mId = UUID.randomUUID().toString(); // Generate unique identifier
        mDate = new Date();      // The date a crime occurred
    }

    public Crime(String id, Date date, String title, boolean solved) {
        mId     = id;
        mTitle  = title;
        mDate   = date;
        mSolved = solved;
    }

    //==> Getters and setters

    public String getCrimeId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * @param c context
     * @return formatted date string
     */
    public String getDateString(Context c) {
        java.text.DateFormat df = android.text.format.DateFormat.getLongDateFormat(c);
        return df.format(mDate);
    }

    /**
     * @param c context
     * @return formatted time string
     */
    public String getTimeString(Context c) {
        java.text.DateFormat df = android.text.format.DateFormat.getTimeFormat(c);
        return df.format(mDate);
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
