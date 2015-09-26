# Formatting date/time

[日本語](http://qiita.com/mnishiguchi/items/9313d8ebe3b9d7bcd513)

### format based on locale
- Format the date in long/short form according to the current locale.
- Note `android.text.format.DateFormat.getDateFormat(context)` returns `java.text.DateFormat` rather than `android.text.format.DateFormat`.

```java
public class Crime {
    private Date mDate;

    // ...

    /**
     * @param c context
     * @return formatted date string based on locale
     */
    public String getShortDateString(Context c) {
        java.text.DateFormat df =
            android.text.format.DateFormat.getDateFormat(c);
        return df.format(mDate);
    }

    /**
     * @param c context
     * @return formatted date string based on locale
     */
    public String getLongDateString(Context c) {
        java.text.DateFormat df =
            android.text.format.DateFormat.getLongDateFormat(c);
        return df.format(mDate);
    }

    /**
     * @param c context
     * @return formatted time string based on locale
     */
    public String getTimeString(Context c) {
        java.text.DateFormat df = 
            android.text.format.DateFormat.getTimeFormat(c);
        return df.format(mDate);
    }
}
```

- E.g. called in a fragment
```java
    mDateButton.setText(mCrime.getDateString(getActivity()));
```


### Custom format ignoring locale

- Format the date according to the specified format.
- Try to avoid this pattern unless a custom format is really needed.
- `android.text.format.DateFormat.format(CharSequence inFormat, Date inDate)`
- [documentation about patterns](http://developer.android.com/reference/java/text/SimpleDateFormat.html)

```java
public class Crime {
    private Date mDate;
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
