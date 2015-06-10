## Import the project in Eclipse

Install Maven.

Then run

    mvn eclipse:clean eclipse:eclipse -DdownloadSources -DdownloadJavadocs

Then use Import... > Existing Project in Eclipse.

## Run the project

In Eclipse, right-click on the it.xpug.lezione6.main.Main class and select Debug As... > Java Application.

Then observe the application at http://localhost:8080/

## ESERCIZI LEZIONE 8


# Popolare la todo-list

    POST /todolists/123/items
    text=Compra il latte

...deve creare un nuovo elemento all'interno della TODO LIST che ha id=123

Per testare: crea una nuova lista con

    curl -i -d name=Prova http://localhost:8080/todolists

Poi crea alcuni todo-items con

    curl -i -d text='Compra il latte' http://localhost:8080/todolists/0/items
    curl -i -d text='Compra il giornale' http://localhost:8080/todolists/0/items

E quindi verifica che funzioni con

    curl -i http://localhost/todolists/0

Deve restituire

    HTTP/1.1 200 OK

    {
      "name": "Prova",
      "items": [
        {
          "text": "Compra il latte",
          "status": "unchecked",
          "uri": "/todolists/0/items/0"
        },
        {
          "text": "Compra il giornale",
          "status": "unchecked",
          "uri": "/todolists/0/items/1"
        },
      ]
    }



# Check and uncheck

    POST /todolists/0/items/0
    checked=true

...deve modificare lo status di checked/unchecked del Todo Item specificato nella url.

Verifica che funzioni con `curl -i http://localhost/todolists/0`.  Deve restituire

    HTTP/1.1 200 OK

    {
      "name": "Prova",
      "items": [
        {
          "text": "Compra il latte",
          "status": "checked",
          "uri": "/todolists/0/items/0"
        },
      ]
    }

(Nota che adesso lo status è "checked").
