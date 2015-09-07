# CriminalIntent2
This repo is for me to practice Android Programming, using the tutorial book Android Programming(2nd ed.) by Bill Phillips, Chris Stewart, Brian Hardy and Kristin Marsicano.

=============

## Fragment

- Always use fragments.

### Support library vs built-in implementation

- Use the support library instead of Built-in implementation.

### Advantages of using the support library
- The support library is quickly updated when new features are added to the fragment API.
- We can update the version of the support library in our app and ship a new version of our app at any time
- We will likely to use the support library for some of its features other than fragments.
- No significant downsides to using the support library's fragments other than having to include in our project the support library that is a non-zero size. (Currently under a megabyte)

### Key classes from the support library
- Fragment (android.support.v4.app.Fragment)
- FragmentActivity (android.support.v4.app.FragmentActivity)

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



