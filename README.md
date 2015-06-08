## Import the project in Eclipse

Install Maven.

Then run

    mvn eclipse:clean eclipse:eclipse -DdownloadSources -DdownloadJavadocs

Then use Import... > Existing Project in Eclipse.

## Run the project

In Eclipse, right-click on the it.xpug.lezione6.main.Main class and select Debug As... > Java Application.

Then observe the application at http://localhost:8080/

## ESERCIZI LEZIONE 7


# Popolare la todo-list

POST /todolists/123/items
text=Compra il latte

deve creare un nuovo elemento all'interno della TODO LIST che ha id=123



# Check and uncheck

POST /todoitems/456
checked=true

deve modificare lo status di checked/unchecked del Todo Item che ha id=456