package com.mnishiguchi.criminalintent2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by masa on 9/7/15.
 */
public class CrimeFragment extends Fragment {

    // for the Bundle arguments
    private static final String ARG_CRIME_ID = "crime_id";

    // for the dialogs
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    // for setting a target fragment
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedCheckBox;

    /**
     * Called when the hosting activity needs an instance of this fragment.
     * @param crimeId
     * @return
     */
    public static CrimeFragment newInstance(UUID crimeId) {

        // Create a bundle.
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        // Create and configure a new instance of CrimeFragment.
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the crime ID and fetch the crime from CrimeLab based the ID.
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space is intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This space is intentionally left blank
            }
        });

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get a fragment manager.
                FragmentManager manager = getFragmentManager();

                // Get a new instance of DatePickerFragment.
                DatePickerFragment dialogDate = DatePickerFragment.newInstance(mCrime.getDate());

                // Set a target fragment to this fragment for getting the result from DatePickerFragment.
                dialogDate.setTargetFragment(CrimeFragment.this, REQUEST_DATE);

                // Show the DatePickerFragment.
                dialogDate.show(manager, DIALOG_DATE);
            }
        });

        mTimeButton = (Button)v.findViewById(R.id.crime_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get a fragment manager.
                FragmentManager manager = getFragmentManager();

                // Get a new instance of TimePickerFragment.
                TimePickerFragment dialogTime = TimePickerFragment.newInstance(mCrime.getDate());

                // Set a target fragment to this fragment for getting the result from DatePickerFragment.
                dialogTime.setTargetFragment(CrimeFragment.this, REQUEST_TIME);

                // Show the DatePickerFragment.
                dialogTime.show(manager, DIALOG_TIME);
            }
        });

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // The result code must be OK.
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        // Retrieve the date that the user entered and update the date text.
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);

            // Update the date in the model layer.
            mCrime.setDate(date);

            // Update the date in the UI.
            updateDate();

        } else if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_DATE);

            // Update the date in the model layer.
            mCrime.setDate(date);

            // Update the date in the UI.
            updateTime();
        }
    }

    /**
     * Set the date text in the UI based on the data from the model layer.
     */
    private void updateDate() {
        mDateButton.setText(mCrime.getDateString(getActivity()));
    }

    /**
     * Set the time text in the UI based on the data from the model layer.
     */
    private void updateTime() {
        mTimeButton.setText(mCrime.getTimeString(getActivity()));
    }
}
