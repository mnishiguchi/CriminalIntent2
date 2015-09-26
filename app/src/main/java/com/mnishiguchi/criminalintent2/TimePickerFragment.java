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
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A wrapper fragment for the AlertDialog with a TimePicker.
 * Created by masa on 9/26/15.
 */
public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "com.mnishiguchi.criminalintent2.date";

    // for the newInstance method
    private static final String ARG_DATE = "date";

    private TimePicker mTimePicker;
    private int mYear, mMonth, mDay;

    /**
     * @param date
     * @return an instance of TimePickerFragment that is configured for the specified date.
     */
    public static TimePickerFragment newInstance(Date date) {
        // Create a bundle.
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        // Create and configure a new instance of TimePickerFragment.
        TimePickerFragment fragment = new TimePickerFragment();
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

        // Get year, month, day and time from the date object.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        mYear    = calendar.get(Calendar.YEAR);
        mMonth   = calendar.get(Calendar.MONTH);
        mDay     = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min  = calendar.get(Calendar.MINUTE);

        // Inflate the dialog's view from an xml file.
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        /*
        Note: The following methods of TimePicker became depricated at the release of API 23:
            setCurrentHour(...), setCurrentMinute(...), getCurrentHour(), getCurrentMinute()
         */

        // Initialize the TimePicker in the dialog.
        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(min);
        mTimePicker.setIs24HourView(false);

        // Build and return a configured AlertDialog instance.
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get year, month and day from the TimePicker.
                        int hour = mTimePicker.getCurrentHour();
                        int min  = mTimePicker.getCurrentMinute();

                        // Translate year, month and day into a Date object.
                        Date date = new GregorianCalendar(mYear, mMonth, mDay, hour, min).getTime();

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
