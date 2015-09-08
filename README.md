# CriminalIntent2

This repo is for me to practice Android Programming, along with the tutorial book [Android Programming(2nd ed.) by Bill Phillips, Chris Stewart, Brian Hardy and Kristin Marsicano](https://www.bignerdranch.com/blog/android-programming-the-big-nerd-ranch-guide-second-edition/). All the credit goes to these guys from Big Nerd Ranch. It's an awesome book.

=============

## Table of contents

- [Fragment](https://github.com/mnishiguchi/CriminalIntent2#fragment)
- [Adding dependencies in Android Studio](https://github.com/mnishiguchi/CriminalIntent2#adding-dependencies-in-android-studio)
- [Adding a UI fragment to the FragmentManager](https://github.com/mnishiguchi/CriminalIntent2#adding-a-ui-fragment-to-the-fragmentmanager)
- [Styles, themes, and theme attributes](https://github.com/mnishiguchi/CriminalIntent2#styles-themes-and-theme-attributes)
- [Screen pixel densities and dp and sp](https://github.com/mnishiguchi/CriminalIntent2#screen-pixel-densities-and-dp-and-sp)
- [Layout parameters vs widget parameters](https://github.com/mnishiguchi/CriminalIntent2#layout-parameters-vs-widget-parameters)
- [Formatting a date](https://github.com/mnishiguchi/CriminalIntent2#formatting-date)

=============

## Fragment

English, [日本語](http://qiita.com/mnishiguchi/items/de1b41fbf8cb02bd4ad1)
- Always use fragments.

### Support library vs built-in implementation

- Use the support library instead of Built-in implementation.

### Advantages of using the support library

- The support library is quickly updated when new features are added to the fragment API.
- We can update the version of the support library in our app and ship a new version of our app at any time
- We will likely to use the support library for some of its features other than fragments.
- No significant downsides to using the support library's fragments other than having to include in our project the support library that is a non-zero size. (Currently under a megabyte)

### Key classes from the support library

- FragmentActivity (android.support.v4.app.FragmentActivity)

```java
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class SomeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);
    }
    // ...
}
```

- Fragment (android.support.v4.app.Fragment)

```java
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SomeFragment extends Fragment{
    // ...

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ...
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.some_layout_file, container, false);

        // ...

        return v;
    }
}
```

=============

## Adding dependencies in Android Studio

- To use the support library, our project must list it as a dependency.

1. Open `app/build.gradle` to see currently registered dependencies
    Note: There are two `build.gradle` files: one for the project as a whole; the other for our app module.
2. File -> Project structure
![alt text](https://qiita-image-store.s3.amazonaws.com/0/82804/fbef2831-8bd4-6063-6f93-a8a177f77d97.png)
3. Select the App module on the left
4. Select the Dependencies tab
![alt text](https://qiita-image-store.s3.amazonaws.com/0/82804/5ce888b0-3adb-4278-f924-f0c597e27c10.png)
5. Click the + button and choose Library dependency
![alt text](https://qiita-image-store.s3.amazonaws.com/0/82804/d94dd5f8-bf5e-e508-9db3-1de2d12cef89.png)
6. Choose library dependency and click OK
![alt text](https://qiita-image-store.s3.amazonaws.com/0/82804/fe150e5f-fc96-bb23-60b5-97ce33d3c6d0.png)

=============

## Adding a UI fragment to the FragmentManager

```java
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        // Call `getSupportFragmentManager()` because we are using the support library
        FragmentManager fm = getSupportFragmentManager();

        // A container view ID serves two purposes:
        // 1. Tells the FragmentManager where in the activity's view the fragment's view should appear.
        // 2. Used as a unique identifier for a fragment in the FragmentManager's list.
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // Fragment transactions are used to add, remover, attach, detach, or
        // replace fragments in the fragment list.
        if (fragment == null) {
            fragment = new CrimeFragment();

            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
```

=============

## Styles, themes, and theme attributes

### Style
- An XML resource that contains attributes that describes how a widget should look and behave.

#### Creating our own styles

- Add them to a style file in `res/values/`
- Refer to them in layouts like `@style/my_own_style`

### Theme

- A collection of styles
- A style resource whose attributes point to other stye resources
- Android provides platform themes that our app can use.

### Theme attribute reference

- We can apply to a widget a style from the app's theme by using theme attribute reference.
- E.g. `style="?android:listSeparatorTextViewStyle"`

=============

## Screen pixel densities and dp and sp

- In practice, `sp` and `dp` are used almost exclusively
- Android will translate these values into pixels at runtime

### dp (density-independent pixel)

- Typially used for margins, padding, or anything else for which you would otherwisespecify size with a pixel value
- Always 1dp = 1/160 inches on a device's screen, regardless of screen density

### sp (scale-independent pixel)

- Density-independent pixels that also take into account the user's font size preference.
- Almost always used to set display text size

=============

## Layout parameters vs widget parameters

### Layout parameters
- Attributes whose names begins with layout
- directions to the widget's parent
- E.g. margin - `android:layout_marginLeft`

### widget parameters
- Attributes whose names does not begin with layout
- directions to the widget
- E.g. padding - `android:padding`

=============

## Formatting date

### Pattern A
- Format the date in short form according to the current locale.
- Note `android.text.format.DateFormat.getDateFormat(context)` returns `java.text.DateFormat` rather than `android.text.format.DateFormat`.

```java
public class Crime {
    // ...

    /**
     * @param c context
     * @return formatted date string
     */
    public String getDateString(Context c) {
        java.text.DateFormat df =
            android.text.format.DateFormat.getDateFormat(c);
        return df.format(mDate);
    }
    // ...
}
```

- E.g. called in a fragment
```java
    mDateButton.setText(mCrime.getDateString(getActivity()));
```

### Pattern B
- Format the date in long form according to the current locale.

```java
public class Crime {
    // ...

    /**
     * @param c context
     * @return formatted date string
     */
    public String getDateString(Context c) {
        java.text.DateFormat df =
            android.text.format.DateFormat.getLongDateFormat(c);
        return df.format(mDate);
    }
    // ...
}
```

- E.g. called in a fragment
```java
    mDateButton.setText(mCrime.getDateString(getActivity()));
```


### Pattern C

- Format the date according to the specified format.
- Try to avoid this pattern unless a custom format is really needed.
- `android.text.format.DateFormat.format(CharSequence inFormat, Date inDate)`
- [documentation about patterns](http://developer.android.com/reference/java/text/SimpleDateFormat.html)

```java
public class Crime {
    // ...

    /**
     * @return formatted date string
     */
    public String getDateString() {
        return ((String)DateFormat.format("EEE, MMM d, ''yy", mDate)).toString();
    }
    // ...
}
```

- E.g. called in a fragment
```java
    mDateButton.setText(mCrime.getDateString());
```
