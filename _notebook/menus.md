# Menus

- The top-right area of the toolbar that is reserved for the toolbar's menu
- Consists of action items, aka menu items
- Actions on the current screen or to the app as a whole

## How to implement it

### 1. Defining a menu in XML
- right-click on the res directory and select New -> Android resource file

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto" >
    <item
        android:id="@+id/menu_item_new_crime"
        android:icon="@drawable/ic_menu_add"
        android:title="@string/new_crime"
        app:showAsAction="ifRoom|withText" />
</menu>
```

### 2. Creating an icon as a local resource using Android Asset Studio
- right-click on the drawable directory and select New -> Image Assset
- mdpi, hdpi, xhdpi, xxhdpi icons will be automatically created
- Reference the icon using `@drawable/ic_menu_...` in the xml file

### 3. Add code for creating the menu in a Fragment
### 4. Add code for responding to menu selections

```java
public class CrimeListFragment extends Fragment {
    ...
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the FragmentManager to call Fragment.onCreateOptionsMenu(...) 
        // when the hosting Activity receives its onCreateOptionsMenu(...)
        setHasOptionsMenu(true);
    }
    ...
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Pass in the res ID of the menu file and the Menu instance
        inflater.inflate(R.menu.fragment_crime_list, menu);
    }

    /**
     * Invoked when the user presses an action item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:

                // Do something

                // Indicate that no further processing is necessary
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
```









