# Toolbar

- Added to Android as of Android 5.0 (Lollipop)
- Back-ported to the `AppCompat` library
- The top-right area of the toolbar is reserved for the toolbar's menu

## Requirements

- Add `AppCompat` to the project as a dependency
- Use one of the AppCompat themes
- Ensure that all activities are a subclass of AppCompatActivity

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

## AppCompatActivity

- Can substitute for `FragmentActivity` since `AppCompatActivity` is a subclass of `FragmentActivity`

===

## Menus

- The top-right area of the toolbar is reserved for the toolbar's menu
- consists of action items, aka menu items
- actions on the current screen or to the app as a whole

### How to implement it

#### Defining a menu in XML
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

#### Creating an icon as a local resource using Android Asset Studio
- right-click on the drawable directory and select New -> Image Assset
- mdpi, hdpi, xhdpi, xxhdpi icons will be automatically created
- Reference the icon using `@drawable/ic_menu_...` in the xml file









