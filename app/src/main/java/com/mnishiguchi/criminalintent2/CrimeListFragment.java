package com.mnishiguchi.criminalintent2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by masa on 9/13/15.
 */
public class CrimeListFragment extends Fragment {
    /*
     RecyclerView
     - Recycles viewHolders, communicating with the adapter
     - Positions viewHolders on the screen
     - Requires a LayoutManager to work
     */
    private RecyclerView mCrimeRecyclerView;

    private TextView mEmptyText;
    private Button mEmptyButton;

    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the FragmentManager to call onCreateOptionsMenu(...) when
        // the hosting Activity receives its onCreateOptionsMenu(...)
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate a layout xml file
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        // Instantiate the RecyclerView in the layout
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        // RecyclerView requires a LayoutManager to work
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Instantiate the empty view
        mEmptyText = (TextView) view.findViewById(R.id.empty_view_text);
        mEmptyButton = (Button) view.findViewById(R.id.empty_view_button);
        mEmptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCrime();
            }
        });

        // Update the Adapter and connect it to the RecyclerView
        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Reload the list.
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // Pass in the res ID of the menu file and the Menu instance
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    /**
     * Invoked when the user presses an action item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item_new_crime:
                createCrime();
                return true;  // Indicate that no further processing is necessary

            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;  // Toggle the bool
                getActivity().invalidateOptionsMenu(); // Redraw the menus
                updateSubtitle();
                return true;  // Indicate that no further processing is necessary

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createCrime() {
        Crime crime = new Crime();
        CrimeLab.get(getActivity()).addCrime(crime);
        Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
        startActivity(intent);
    }

    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        // Hide the subtitle if in the hidden state
        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    /**
     * Update the Adapter based on the current data set.
     * Connect the Adapter to the RecyclerView
     */
    private void updateUI() {

        // Get a list of crimes from the model layer
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        // Reload the list if the adapter is already set up.
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        // Show the placeholder view if the list is empty.
        if (crimes.isEmpty()) {
            mCrimeRecyclerView.setVisibility(View.GONE);
            mEmptyText.setVisibility(View.VISIBLE);
            mEmptyButton.setVisibility(View.VISIBLE);
        }
        else {
            mCrimeRecyclerView.setVisibility(View.VISIBLE);
            mEmptyText.setVisibility(View.GONE);
            mEmptyButton.setVisibility(View.GONE);
        }
    }

    //==> Inner classes

    /**
     * Represent a list item
     */
    private class CrimeHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        // Data item
        private Crime mCrime;

        // View widgets
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        /**
         * A constructor to create a viewHolder with the specified layout
         * @param itemView
         */
        public CrimeHolder(View itemView) {
            super(itemView);

            // Detect clicks
            itemView.setOnClickListener(this);

            // Instantiate the widgets
            mTitleTextView = (TextView) itemView.findViewById(
                    R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(
                    R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(
                    R.id.list_item_crime_solved_check_box);
        }

        /**
         * Wire each widget with appropriate data based on the passed-in object
         * @param crime
         */
        public void bindCrime(Crime crime) {

            // Store the data item that was passed in
            mCrime = crime;

            // Wire each widget to appropriate data
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDateString(getActivity()));
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v) {
            // Start CrimeActivity for the crime that was clicked.
            Intent i = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(i);
        }
    }

    /**
     * Accept the data set to be displayed
     * Creates the necessary viewHolders
     * Binds the viewHolders to data
     */
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        /**
         * Called by the RecyclerView when a new view is needed to display an item.
         * @param parent
         * @param viewType
         * @return an instance of the viewHolder
         */
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // Inflate the layout of the rows from an xml file
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(
                    R.layout.list_item_crime, parent, false);

            // Create a viewHolder with the layout
            return new CrimeHolder(view);
        }

        /**
         * Called by the RecyclerView
         * Bind the viewHolder layout to a data object
         * @param holder
         * @param position a position in the data set
         */
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {

            // Get the data item based on the position
            Crime crime = mCrimes.get(position);

            // Provide data to the viewHolder
            holder.bindCrime(crime);
        }

        /**
         * Called by the RecyclerView to determine how many new ViewHolders
         * the RecyclerView should create
         * @return the size of the list
         */
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
