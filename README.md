## Import the project in Eclipse

Install Maven.

Then run

    mvn eclipse:clean eclipse:eclipse -DdownloadSources -DdownloadJavadocs

Then use Import... > Existing Project in Eclipse.

## Run the project

In Eclipse, right-click on the it.xpug.lezione6.main.Main class and select Debug As... > Java Application.

Then observe the application at http://localhost:8080/

## ESERCIZI LEZIONE 10


## Finish Implementing the TodoListRepository

### 0. Regenerate the project to get the org.postgresql.Driver

    mvn eclipse:clean eclipse:eclipse -DdownloadSources -DdownloadJavadocs

### 1. Run the tests

    mvn clean test

### 2. Run the application

And verify that it is broken at url http://localhost:8080/todolists

[Optional] Add a test for DatabaseTodoListRepository::getAll() in file DatabaseTodoListRepositoryTest

Implement DatabaseTodoListRepository::getAll()



