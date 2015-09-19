## Import the project in Eclipse (the manual way)

## Import the project in Eclipse (with maven)

Install Maven.

Then run

    mvn eclipse:clean eclipse:eclipse -DdownloadSources -DdownloadJavadocs

Then use Import... > Existing Project in Eclipse.

## Run the project

In Eclipse, right-click on the it.xpug.todolists.main.Main class and select Debug As... > Java Application.

Then observe the application at http://localhost:8080/

## EXERCISES FOR LESSON 12


### 0. Pass the login screen

The server knows only one hardcoded user.  Its email is "x@x.com" and its name is "x".  Add JavaScript code so that the login form

  - sends the credentials to the server
  - if the credentials are accepted, it hides the login form and shows the "my lists" page
  - otherwise, it shows an error message and keeps asking for valid credentials

### 1. Implement a database-backed user repository

  - The tests of the DatabaseUserRepositoryTest are failing.  Make them pass!
  - Remember, never save the cleartext password in the database.  You may use SHA256 to encrypt it.

### 2. Use the DatabaseUserRepositoryTest in the LoginResource

  - The LoginResource knows a single, hardcoded user.  This is not satisfactory!  Change it to use the DatabaseUserRepository to look up users.
  - We don't have a registration form yet, but we don't need it to make progress.  You can save a few test users in the database directly.
  - Check that you can log in with the credentials of the test users in the database.

