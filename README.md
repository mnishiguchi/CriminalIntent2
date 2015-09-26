# CriminalIntent2

This repo is for me to practice Android Programming, along with the tutorial book [Android Programming(2nd ed.) by Bill Phillips, Chris Stewart, Brian Hardy and Kristin Marsicano](https://www.bignerdranch.com/blog/android-programming-the-big-nerd-ranch-guide-second-edition/). All the credit goes to these guys from Big Nerd Ranch. It's an awesome book.

==

## Index

- [Dependencies - Adding dependencies in Android Studio](https://github.com/mnishiguchi/CriminalIntent2/blob/master/_notebook/adding_dependency.md)
- [DateFormat](https://github.com/mnishiguchi/CriminalIntent2/blob/master/_notebook/formatting_date.md)
- [Dialog](https://github.com/mnishiguchi/CriminalIntent2/blob/master/_notebook/dialog.md)
- [Fragment](https://github.com/mnishiguchi/CriminalIntent2/blob/master/_notebook/fragment.md)
- [Parameters - Layout parameters vs widget parameters](https://github.com/mnishiguchi/CriminalIntent2#layout-parameters-vs-widget-parameters)
- [Pixels - Screen pixel densities and dp and sp](https://github.com/mnishiguchi/CriminalIntent2#screen-pixel-densities-and-dp-and-sp)
- [RecyclerView](https://github.com/mnishiguchi/CriminalIntent2/blob/master/_notebook/recycler_view.md)
- [Shortcut keys](https://github.com/mnishiguchi/CriminalIntent2#shortcut-keys)
- [Singletons](https://github.com/mnishiguchi/CriminalIntent2/blob/master/_notebook/Singletons.md)
- [Styles, themes, and theme attributes](https://github.com/mnishiguchi/CriminalIntent2#styles-themes-and-theme-attributes)
- [ViewPager](https://github.com/mnishiguchi/CriminalIntent2/blob/master/_notebook/view_pager.md)

==

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

==

## Screen pixel densities and dp and sp

- In practice, `sp` and `dp` are used almost exclusively
- Android will translate these values into pixels at runtime

### dp (density-independent pixel)

- Typially used for margins, padding, or anything else for which you would otherwisespecify size with a pixel value
- Always 1dp = 1/160 inches on a device's screen, regardless of screen density

### sp (scale-independent pixel)

- Density-independent pixels that also take into account the user's font size preference.
- Almost always used to set display text size

==

## Layout parameters vs widget parameters

### Layout parameters
- Attributes whose names begins with layout
- directions to the widget's parent
- E.g. margin - `android:layout_marginLeft`

### widget parameters
- Attributes whose names does not begin with layout
- directions to the widget
- E.g. padding - `android:padding`

==

## Shortcut keys

- importing class - `option` + `return`

==

## Declaring an activity in the manifest

- Declare all the activities in the manifest
- Specify the launcher activity

==

## Toast

```java
String msg = "clicked";
Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
```

==

## Don't forget to add an activity to the manifest
- When creating a new activity, add it to the manifest so that the OS can start it.
- 
