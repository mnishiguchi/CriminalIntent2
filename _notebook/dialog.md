# Dialog

## Classes involved
- android.support.v7.app.AlertDialog
- android.support.v4.app.DialogFragment
- Wrap the AlertDialog in an instance of DialogFragment
    + more options for presenting the dialog
    + can survive the configuration changes
- [DatePickerFragment](https://github.com/mnishiguchi/CriminalIntent2/blob/master/app/src/main/java/com/mnishiguchi/criminalintent2/DatePickerFragment.java)
- [TimePickerFragment](https://github.com/mnishiguchi/CriminalIntent2/blob/master/app/src/main/java/com/mnishiguchi/criminalintent2/TimePickerFragment.java)

### The hierachical structure of a dialog with a DatePicker
- Hosting Activity -> CallerFragment
- Hosting Activity -> DialogFragment -> AlertDialog -> DatePicker

## How to implement a dialog?

1. Add the appcompat-v7 library as a dependency
2. Create a wrapper fragment subclassing a DialogFragment
    - Use android.support.v4.app.DialogFragment

3. Create a layout file for a dialog's contents
    - DatePicker, etc

4. Implement onCreateDialog(...)
    - Use android.support.v7.app.AlertDialog
    - Build and return a configured AlertDialog instance.

## Passing data between two fragments
E.g., a caller Fragment and a callee DialogFragment

1. In the DialogFragment, Implement newInstance(...)
    - Enables us to configure the fragment based on passed-in data.

2. In the caller Fragment, setTargetFragment(...) on the DialogFragment
    - So that the caller Fragment can get the result from the DialogFragment.

3. In the DialogFragment, implement sendResult(...) calling onActivityResult(...) on the caller Fragment
    - Pass the result to the caller Fragment.

4. In the caller Fragment, override onActivityResult(...)
    - Retrieve the data from the DialogFragment.

```java
public class CrimeFragment extends Fragment {

    //...

    // for the date picker dialog
    private static final String DIALOG_DATE = "DialogDate";

    // for setting a target fragment
    private static final int REQUEST_DATE = 0;

    // ...

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        
        //...

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get a fragment manager.
                FragmentManager manager = getFragmentManager();

                // Get a new instance of DatePickerFragment.
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());

                // Set a target fragment to this fragment for getting the result from DatePickerFragment.
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);

                // Show the DatePickerFragment.
                dialog.show(manager, DIALOG_DATE);
            }
        });

        //...

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
        }
    }

    /**
     * Set the date text in the UI based on the data from the model layer.
     */
    private void updateDate() {
        mDateButton.setText(mCrime.getDateString(getActivity()));
    }
}
```

```java
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "com.mnishiguchi.criminalintent2.date";

    // for the newInstance method
    private static final String ARG_DATE = "date";

    private DatePicker mDatePicker;

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

        // Get year, month and day for the date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year  = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day   = calendar.get(Calendar.DAY_OF_MONTH);

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
                        Date date = new GregorianCalendar(year, month, day).getTime();

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

```
