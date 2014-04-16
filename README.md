# Hangman

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

