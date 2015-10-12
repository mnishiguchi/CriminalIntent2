# database
- [SQLite documentation](http://www.sqlite.org}

## App's sandbox directory
- a child of the devices /data/data directory named after the app's package
- Keeping files in the sandbox protects them from being accessed by other apps or even the prying eyes of users (Unless the device has been rooted)

## Object-relational mapping(ORM)
- [Sugar ORM](http://satyan.github.io/sugar/index.html)

## Android Device Monitor
- Can look at databases on an emulator
- Tools -> Android -> Android Device Monitor

## Updating database versions
- In practice, the best thing to do is destroy the database and start over
- Just delete the app off the device by uninstalling it

## Save to database in `onPause()`
- `onPause()` is the place to save modification to database

## Application context vs activity's context
- [Context, What Context?](https://possiblemobile.com/2013/06/context/) by Dave Smith
- Always think about the lifetime of your activities as you keep a reference to them
