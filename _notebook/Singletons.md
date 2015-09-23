# Singletons

- Commonly used on Android
- a key component of a well-architected Android app
- Make sure that anything in our singleton is truly global and has a strong reason for being there
- Use [the application context](https://possiblemobile.com/2013/06/context/)

## Upsides

- Outlive a single fragment or activity
- Will still exist across rotation
- will exist as we move between activities and fragments in our application
- Allow for an easy place to stash data with a longer life than a controller

## Downsides

- Can be misused in a way that makes an app hard to maintain
- Will be destroyed along with all of their instance variables, as Android reclaims memory at some time after we switch out of an application
- Not a long-term storage solution
- Can make our code hard to unit test

## Test

- Android developers usually use a tool called a dependency injector, which allows for objects to be shared as singletons, while still making it possible to replace them when needed.

## Examples

- https://github.com/mnishiguchi/CriminalIntent2/blob/master/app/src/main/java/com/mnishiguchi/criminalintent2/CrimeLab.java
