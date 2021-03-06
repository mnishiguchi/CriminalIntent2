# Toolbar

- Added to Android as of Android 5.0 (Lollipop)
- Back-ported to the `AppCompat` library
- The top-right area of the toolbar is reserved for the toolbar's menu
- More flexible than older Android's Action Bar
    - Can be manually included as a normal view in a activity / fragment
    - multiple toolbars on the screen at the same time
    - can place Views inside of the toolbar
    - can adjust  the height of the toolbar
    - etc

==

## Requirements

- Add `AppCompat` to the project as a dependency
- Use one of the AppCompat themes
- Ensure that all activities are a subclass of AppCompatActivity

==

## the AppCompat themes
- the AppCompat library comes with three themes:

    1. Theme.AppCompat
    2. Theme.AppCompat.Light
    3. Theme.AppCompat.Light.DarkActionBar

- Specified in the AndroidManifest.xml
    + at the application level
    + optionally, per activity

### app/manifests/AndroidManifest.xml
```xml
...
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme" >
...
```

### app/res/values/styles.xml
- No need to switch themes based on the version of Android
- Delete the extra style.xml files if multiple versions of the style.xml file exist in the project
```xml
<resources>

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
    </style>

</resources>
```

==

## AppCompatActivity

- Can substitute for `FragmentActivity` since `AppCompatActivity` is a subclass of `FragmentActivity`

==

## Hierarchical navigation (Up button)
- As opposed to back buton being *temporal navigation*
- Enable hierarchical navigation by adding a `parentActivityName` attribute in the AndroidManifest.xml file

```xml
...
<activity
    android:name=".CrimePagerActivity"
    android:label="@string/title_activity_crime_pager"
    android:parentActivityName=".CrimeListActivity" >
</activity>
...
```


