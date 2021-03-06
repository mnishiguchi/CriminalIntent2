# RecyclerView

- Introduced with Android 5.0
- The ListView and GridView are replaced with the RecyclerView because of the complexity required to alter the behavior of a ListView or GridView.

==

## How to implement it?

1. Add the recycleview-v7 library as a dependency
2. Add RecycleView to the fragment's layout file
3. Create a layout file for a list item, if necessary
4. Set up the RecycleView in the fragment's onCreateView(...) method
    - instantiate the RecycleView
    - Set a LayoutManager on it
5. Implement Adapter and ViewHolder as inner classes of the fragment
6. Implement updateUI() which sets up the adapter of updates the list if the adapter is already set up

==

## Reloading the list

- The RecyclerView's Adapter needs to be informed that the data has changed (or may have changed) so that it can refetch the data and reload the list.
-  In general, onResume() is the safest place to take action to update a fragment's view.
- Methods for reloading the list:
    + `notifyDataSetChanged()` - Reload all the items that are currently visible
    + `notifyItemChanged(int position)` - Reload only specified item  

==

## Placeholding the empty list

- Give something to interact with when there are no items in the list
- Use setVisibility(...) to toggle the placeholder when appropriate
- [StackOverflow](http://stackoverflow.com/questions/28217436/how-to-show-an-empty-view-with-a-recyclerview)
- [EmptyRecyclerView.java](https://gist.github.com/adelnizamutdinov/31c8f054d1af4588dc5c)

### E.g.

layout file
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <android.support.v7.widget.RecyclerView
        android:id="@+id/crime_recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <!-- Empty view -->
    <Button
        android:id="@+id/empty_view_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:text="@string/new_crime"
        android:visibility="gone" />
    <TextView
        android:id="@+id/empty_view_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_above="@id/empty_view_button"
        android:visibility="gone"
        android:text="@string/no_data_available" />
</RelativeLayout>
```

Java code
```java
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //...

        // Instantiate the empty view
        mEmptyText = (TextView) view.findViewById(R.id.empty_view_text);
        mEmptyButton = (Button) view.findViewById(R.id.empty_view_button);
        mEmptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCrime();
            }
        });

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    //...

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
    //...
```



==

## Examples

### Simple list using the Android standard library

#### The layout of the fragment
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.RecyclerView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/crime_recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    />
```

#### The layout of the list item

- `android.R.layout.simple_list_item_1`
- Note: No layout file is necessary if we use one of [the Android standard library's layouts](http://developer.android.com/reference/android/R.layout.html)

#### The fragment
```java
package com.mnishiguchi.criminalintent2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate a layout xml file
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        // Instantiate the RecyclerView in the layout
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        // RecyclerView requires a LayoutManager to work
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Update the Adapter and connect it to the RecyclerView
        updateUI();

        return view;
    }

    /**
     * Update the Adapter based on the current data set.
     * Connect the Adapter to the RecyclerView
     */
    private void updateUI() {

        // Get a list of crimes from the model layer
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        //
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    //==> Inner classes

    /**
     * Represent a row of the crime list
     */
    private class CrimeHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView;

        /**
         * A constructor to create a viewHolder with the specified layout
         * @param itemView
         */
        public CrimeHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
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
                    android.R.layout.simple_list_item_1, parent, false);

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

            // Display the item's title text
            holder.mTitleTextView.setText(crime.getTitle());
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
```

### Custom list

#### The layout of the fragment
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.RecyclerView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/crime_recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    />
```

#### The layout of the list item
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <CheckBox
        android:id="@+id/list_item_crime_solved_check_box"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:padding="4dp"
        />

    <TextView
        android:id="@+id/list_item_crime_title_text_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_toLeftOf="@id/list_item_crime_solved_check_box"
        android:textStyle="bold"
        android:padding="4dp"
        tools:text="Crime Title"
        />

    <TextView
        android:id="@+id/list_item_crime_date_text_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_toLeftOf="@id/list_item_crime_solved_check_box"
        android:layout_below="@id/list_item_crime_title_text_view"
        android:textStyle="bold"
        android:padding="4dp"
        tools:text="Crime Title"
        />
</RelativeLayout>
```

#### The fragment
```java
package com.mnishiguchi.criminalintent2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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

    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate a layout xml file
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        // Instantiate the RecyclerView in the layout
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        // RecyclerView requires a LayoutManager to work
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Update the Adapter and connect it to the RecyclerView
        updateUI();

        return view;
    }

    /**
     * Update the Adapter based on the current data set.
     * Connect the Adapter to the RecyclerView
     */
    private void updateUI() {

        // Get a list of crimes from the model layer
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        //
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
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
            String msg = mCrime.getTitle() + " clicked";
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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
```

