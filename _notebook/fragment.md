# Fragment

- Always use fragments.
- Use the support library instead of Built-in implementation.

## the support library

- Add android.support.v4 library as a dependency

### Advantages of using the support library

- The support library is quickly updated when new features are added to the fragment API.
- We can update the version of the support library in our app and ship a new version of our app at any time
- We will likely to use the support library for some of its features other than fragments.
- No significant downsides to using the support library's fragments other than having to include in our project the support library that is a non-zero size. (Currently under a megabyte)

### Key classes from the support library

- FragmentActivity (android.support.v4.app.FragmentActivity)

==

## Fragment's independence

- To maintain the flexibility of independent fragments.
- Does not go both ways:
    + Hosting activities should know the specifics of how to host their fragments.
    + Fragments should not have to know specifics about their activities.

==

## Examples

### Generic fragment-hosting layout

```xml
<?xml version="1.0" encoding="utf-8"?>

<!-- Generic fragment-hosting layout -->
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/fragment_container"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    />
```

### Generic activity superclass for hosting a single fragment
android.support.v4.app.FragmentActivity
Adding a UI fragment to the FragmentManager

```java
package com.mnishiguchi.criminalintent2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Generic activity superclass for hosting a single fragment
 */
public abstract class SingleFragmentActivity extends FragmentActivity {

    /**
     * Subclasses of SingleFragmentActivity must implement this method
     * @return an instance of the fragment that the activity is hosting
     */
    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        // Call `getSupportFragmentManager()` because we are using the support library
        FragmentManager fm = getSupportFragmentManager();

        // A container view ID serves two purposes:
        // 1. Tells the FragmentManager where in the activity's view the fragment's view should appear.
        // 2. Used as a unique identifier for a fragment in the FragmentManager's list.
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // Fragment transactions are used to add, remover, attach, detach, or
        // replace fragments in the fragment list.
        if (fragment == null) {

            // Instantiate the fragment that the activity is hosting
            fragment = createFragment();

            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
```

### Activity (android.support.v4.app.FragmentActivity)

Subclass of SingleFragmentActivity that is defined above

```java
package com.mnishiguchi.criminalintent2;

import android.support.v4.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity {

    /**
     * @return an instance of the fragment that the activity is hosting
     */
    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
```

### Fragment (android.support.v4.app.Fragment)

```java
package com.mnishiguchi.criminalintent2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Created by masa on 9/7/15.
 */
public class CrimeFragment extends Fragment{

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
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
        mDateButton.setText(mCrime.getDateString(getActivity()));
        mDateButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }
}
```

### Starting a new Fragment-hosting Activity

#### The callee activity
1. Implement `Intent newIntent(Context packageContext, SomeType data)` that creates an Intent to start the Activity.
2. Implement `createFragment()` that creates a Fragment based on the data passed in as an extra.

#### The caller fragment
3. Create a new Intent by calling `newIntent(...)` 
4. Call `startActivity(...)` with it.

```java
// The callee activity
public class CrimeActivity extends SingleFragmentActivity {

    // Used for passing data through an intent.
    private static final String EXTRA_CRIME_ID = "com.mnishiguchi.criminalintent2.crime_id";

    /**
     * Start CrimeActivity for the crime associated with the specified id.
     * @param packageContext
     * @param crimeId
     * @return intent
     */
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    /**
     * @return an instance of the fragment that the activity is hosting
     */
    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}
```

```java
// The caller fragment
public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    //...
        @Override
        public void onClick(View v) {
            // Start CrimeActivity for the crime that was clicked.
            Intent i = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(i);
        }
    //...
```



