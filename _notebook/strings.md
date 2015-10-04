# Strings

==

## TextUtils
- http://developer.android.com/reference/android/text/TextUtils.html
- Convenient methods such as [isEmpty(aStr)](http://developer.android.com/reference/android/text/TextUtils.html#isEmpty%28java.lang.CharSequence%29)

==

## android.R.string
- built-in strings
- http://developer.android.com/reference/android/R.string.html

```java
// Get the crime title if not empty
String crimeTitle = TextUtils.isEmpty(mCrime.getTitle()) ?
        getString(android.R.string.untitled) : mCrime.getTitle();
```

==

## Format string
- Use place holders such as %1$1s that expect string arguments
- In code, call getString(...) and pass in the format string and other strings in the order in which thy should preplace the placeholders


### E.g. one placeholder

res/values/strings.xml
```xml
<resources>
    ...
    <string name="subtitle_format">%1$1s crimes</string>
    ...
</resources>
```

In code
```java
String subtitle = getString(R.string.subtitle_format, crimeCount);
```

### E.g. multiple placeholders

res/values/strings.xml
```xml
<resources>
    ...
    <string name="crime_report"> %1$1s!
        The crime was discovered on %2$s. %3$s, and %4$s
    </string>
    ...
</resources>
```

In code
```java
String report = getString(R.string.crime_report,
        mCrime.getTitle(), dataString, solvedString, suspect)
```
