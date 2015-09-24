# view_pager

- only available in the support library.
- Requires a PagerAdapter
- Use FragmentStatePagerAdapter, a subclass of PagerAdapter to take care of the details
- By default, ViewPager loads the item currently on screen plus one neighboring page in each direction so that the response to a swipe is immediate.
- By default, hte ViewPager shows the first item in its PagerAdapter

## How to implement it?
1. Create a layout file for that activity, using `android.support.v4.view.ViewPager`
2. Create an activity that is an subclass of FragmentActivity
3. Inflate the view
4. Prepare the data set
5. Set up the pager adapter
    - Get a fragmentManager
    - Implement FragmentStatePagerAdapter's getItem(...) and getCount()
6. Set the initial pager item
7. Don't forget to add the newly-created activity to the manifest

### Layout
```xml
<android.support.v4.view.ViewPager
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/activity_crime_pager_view_pager"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    >
</android.support.v4.view.ViewPager>
```

### Activity
```java
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends FragmentActivity {
    // Used for passing data through an intent.
    private static final String EXTRA_CRIME_ID = "com.mnishiguchi.criminalintent2.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    /**
     * Start CrimePagerActivity for the crime associated with the specified id.
     * @param packageContext
     * @param crimeId
     * @return intent
     */
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        // Get the crime id to determine the initial position.
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        // Inflate the pager.
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        // Get the list of the crimes and set up the pager adapter.
        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            /**
             * Fetch the Crime instance from the given position in the data set and
             * create an instance of CrimeFragment based on that Crime instance.
             * @param position
             * @return a properly configured CrimeFragment.
             */
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            /**
             * @return the number of crimes in the list.
             */
            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        // Set the initial pager item.
        for (int i = 0; i < mCrimes.size(); i++) {
            // Search for the item with the given ID in the list and
            // set it as the initial item to display.
            if (mCrimes.get(i).getId().equals(crimeId) ) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
```

## FragmentStatePagerAdapter vs FragmentPagerAdapter
- Only different when a fragment is no longer needed

### FragmentStatePagerAdapter
- Calls `remove(fragment)` on the transaction
- Destroys the unneeded fragment
- The state will be saved using the fragment's Bundle from onSavedInstanceState(Bundle)
-  When the user navigated back, the new fragment will be restored using that instance state.
-  More frugal with memory
-  E.g., a list of items

### FragmentPagerAdapter
- Calls `detach(fragment)` on the transaction
- Unneeded fragment is destroyed
- Destroys the unneeded fragment's view, but leaves the fragment instance alive in the FragmentManager.
- The fragments created by FragmentPagerAdapter are never destroyed.
- Suitable with a small interface a fixed number of fragments.
- E.g., a tabbed interface

### Customizing the PagerAdaper interface
- When we want to host something other than Fragments, such as normal View objects in a ViewPager, we need to implement the raw PagerAdapter interface.
- PagerAdapter is more complicated then Adapter because it does more of the work of managing views than Adapter does.
- Instead of onBindViewHolder(...), PagerAdapter has:
    + instantiateItem(ViewGroup container, int pos)
    + destroyItem(ViewGroup container, int pos, Object o)
    + isViewFromObject(View v, Object o)
