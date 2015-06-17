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

### -1. Install Postgresql

On Debian/Ubuntu:

    sudo apt-get install postgresql

On Mac:

    brew install postgresql

### 0. Regenerate the project to get the org.postgresql.Driver

    mvn eclipse:clean eclipse:eclipse -DdownloadSources -DdownloadJavadocs

### 1. Create the databases

    script/create_databases.sh

### 2. Run the tests

    mvn clean test

They should pass.  If they don't, you probably forgot to do step 1.

### 3. Run the application

And verify that it is broken at url http://localhost:8080/todolists

[Optional] Add a test for DatabaseTodoListRepository::getAll() in file DatabaseTodoListRepositoryTest

Implement DatabaseTodoListRepository::getAll()

Verify that http://localhost:8080/todolists now works as before.



