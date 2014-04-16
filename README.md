# Sample application

This is an empty, playground application.  You may start developing by changing the example servlet.

## Exercises

### Hello, World

 Make the tests in ...HelloWorldTest.java pass

### Temperature Conversion

  Make the tests in ...TemperatureConversionTest.java to pass


### Directory listing

 Make the tests in ...DirectoryListingTest.java to pass


## HOW TO RUN

Run

    script/run.sh

then

    open http://localhost:8080

Otherwise, run the it.xpug.hangman.main.Main class as a Java application.  If you run it in debug mode, you can see code changes just reloading the browser.


## HOW TO TEST

Run

    mvn test


## HOW TO CREATE ECLIPSE PROJECT FILES

Run

    mvn eclipse:clean eclipse:eclipse -DdownloadSources -DdownloadJavadocs

Then use Import... > Existing Project in Eclipse.

