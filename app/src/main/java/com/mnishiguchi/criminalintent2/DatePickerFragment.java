package com.mnishiguchi.criminalintent2;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A wrapper fragment for the AlertDialog with a DatePicker.
 * Created by masa on 9/26/15.
 */
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "com.mnishiguchi.criminalintent2.date";

    // for the newInstance method
    private static final String ARG_DATE = "date";

    private DatePicker mDatePicker;
    private int mHour, mMin;  // Remember the values of hour and minute

    /**
     * @param date
     * @return an instance of DatePickerFragment that is configured for the specified date.
     */
    public static DatePickerFragment newInstance(Date date) {
        // Create a bundle.
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        // Create and configure a new instance of DatePickerFragment.
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param savedInstanceState
     * @return a configured AlertDialog instance
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Get the date from the bundle arguments.
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        // Get year, month and day from the date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year  = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day   = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMin  = calendar.get(Calendar.MINUTE);


        // Inflate the dialog's view from an xml file.
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        // Initialize the DatePicker in the dialog.
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);

        // Build and return a configured AlertDialog instance.
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get year, month and day from the DatePicker.
                        int year  = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day   = mDatePicker.getDayOfMonth();

                        // Create a Date object based on the year, month and day.
                        Date date = new GregorianCalendar(year, month, day, mHour, mMin).getTime();

                        // Send the date as result data
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }

    /**
     * Sends the resulting date to the target fragment.
     * The target fragment can retrieve the result in onActivityResult(...).
     * @param resultCode Activity.RESULT_OK, etc
     * @param date the date that the user inputted
     */
    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        // Package the date in an intent.
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        // Pass the result to the target fragment by calling onActivityResult(...) on it.
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
