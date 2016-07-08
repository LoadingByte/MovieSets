1.0.4
-----

### Fixes
* Fixed a ClassNotFoundException being thrown if QuarterBukkit has not been automatically installed yet.

1.0.3
-----

### Fixes
* Fixed the version numbers in this very changelog.

1.0.2
-----

### Notes
* Added deployment information to the pom.xml file. That means that a CI can now properly build and deploy this plugin.

1.0.1
-----

### Additions
* Instead of hackily shading QuarterBukkit-Plugin into the final plugin JAR, we now use the proper automatic installer. That means that you'll probably get a notification after the update.

### Fixes
* Fixed some build information which is important for Maven and Jenkins.

### Notes
* Maven will publish the sources and JavaDocs from this version onwards.

1.0.0
-----

### Notes
* This is the first iteration of the plugin, so there aren't any changes to mention here.
