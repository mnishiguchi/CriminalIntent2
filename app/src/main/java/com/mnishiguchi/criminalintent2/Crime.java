package com.mnishiguchi.criminalintent2;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by masa on 9/7/15.
 */
public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        mId = UUID.randomUUID(); // Generate unique identifier
        mDate = new Date();      // The date a crime occurred
    }

    //==> Getters and setters

    public UUID getId() {
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
