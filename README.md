## Import the project in Eclipse

Install Maven.

Then run

    mvn eclipse:clean eclipse:eclipse -DdownloadSources -DdownloadJavadocs

Then use Import... > Existing Project in Eclipse.

## Run the project

In Eclipse, right-click on the it.xpug.lezione6.main.Main class and select Debug As... > Java Application.

Then observe the application at http://localhost:8080/

## ESERCIZI LEZIONE 9


## Deploy to heroku

There are many steps and things to learn.  Don't despair if something doesn't work: ask for help to collegues, to google (expecially stackoverflow.com), to your teacher.  Try experiments.  Above all, try to understand what you are doing.

First thing: if you deployed this project as a zip file, scrap it and download again using git.  First install git, then use

    git clone https://github.com/xpmatteo/aw-servlet-exercises
    cd aw-servlet-exercises
    git checkout lesson-9-2015

Check that the application works from the command line.  Execute

    script/run.sh

then open the browser to http://localhost:8080/todolists.

Register yourself to Heroku.  It's free.  Then go to Heroku's dashboard on the web and create an application.  Call it "aw-todo-list-123456" where 123456 is your student ID number.  You will be given your application's URL; you will need it.

Download the heroku tools.  There are instructions on Heroku's web site for how to download the correct version for your system (Mac OS or Ubuntu).  If you did it correctly, you should be able to run the

    heroku

command and see some help messages.  Then run

    heroku login

This will ask you to create a digital key if you don't have one already.

Now you will connect your repository for this project, to heroku.  Do this with the command

    git add remote heroku git@heroku.com:XXX

where XXX is your application's URL (see above).  Then you deploy to heroku with

    git push heroku master

This will take some time.  When it's finished, it will print your application's URL (this is the url for interacting with the app; the other url we used before was the Heroku deployment url.)  It will be something like

    http://aw-temperature-conversion-123456.herokuapp.com/

Open it.  It will probably be an error.  This is normal :)  Try to understand what the error is by reading your application's logs with

    heroku logs

The solution is here: there is an error in the Procfile.  This is the file that tells Heroku how it should run our application.  It is of the form

    web: java XXX

where "java XXX" should be the command that we used before to run the application (we don't need to tell to Heroku to run maven; that is done automatically by Heroku).  You will see that there is an error in the arguments to the java command in the Procfile.  Fix it.  Then commit your change to git with

    git add -A
    git commit

Then deploy again with

    git push heroku master

This time it should take less time.  This time the application should work.

## Exercise 1: make it work

We have the UI in the index.html file, but it does not work.  Your task is to add behaviour in JavaScript so that the index page calls the server with Ajax to perform the temperature unit conversion work.



