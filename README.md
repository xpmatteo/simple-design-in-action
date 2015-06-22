## Import the project in Eclipse

Install Maven.

Then run

    mvn eclipse:clean eclipse:eclipse -DdownloadSources -DdownloadJavadocs

Then use Import... > Existing Project in Eclipse.

## Run the project

In Eclipse, right-click on the it.xpug.lezione6.main.Main class and select Debug As... > Java Application.

Then observe the application at http://localhost:8080/

## ESERCIZI LEZIONE 11


## Finish Implementing the TodoListRepository


### 0. Run the tests

    mvn clean test

One test fails.  Fix it.

### 1. Implement the creation of new todoitems

Add the necessary UI code to create new todoitems

### 2. Implement checking and unchecking todoItems

You must add both server-side and UI-side code.
