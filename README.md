# Sample application

This is an empty, playground application.  You may start developing by changing the example servlet.

## Import the project in Eclipse

Install Maven.

Then run

    mvn eclipse:clean eclipse:eclipse -DdownloadSources -DdownloadJavadocs

Then use Import... > Existing Project in Eclipse.

## Run the project

In Eclipse, right-click on the it.xpug.lezione6.main.Main class and select Debug As... > Java Application.

Then observe the application at http://localhost:8080/

## Exercises

### Hello, World

  0. Visit http://localhost:8080/ in the browser.  You should see Hello!

  1. Change ExampleServlet so that it returns Hello, world! in big, bold letters (it should be inside a h1 header.)

  2. Change ExampleServlet so that if you visit http://localhost:8080/?name=Pippo, you see "Hello, Pippo!" instead.  Note that if no parameter name is given, it should still print "Hello, world!" (that is, every new thing that you add to the application should not break what was working before.)

### Creating new todolists

A lezione abbiamo creato una risorsa "/todolists" e abbiamo creato l'oggetto TodoListsResource per gestirla.  Quando facciamo

    GET /todolists

ad esempio, invocando

    curl -i http://localhost:8080/todolists

Ci restituisce un elenco di tutte le todolist presenti nel sistema (ovvero nessuna, inizialmente.)  Per testare se funziona possiamo temporaneamente aggiungere qualche elemento alla lista aggiungendo un

    todoLists.add("Pippo")

da qualche parte.  Fino a qui, dovrebbe già funzionare.  La parte che devi aggiungere è che se facciamo

    POST /todolists
    name=Foobar

che possiamo fare ad esempio con il comando `curl -i -d nome=Foobar http://localhost:8080/todolists`, allora deve aggiungere un nuovo elemento "Foobar" alla lista di todolist, e restituire una redirezione alla url "/todolists".  L'output di `curl http://localhost:8080/todolists` dopo un paio di inserimenti dovrebbe essere simile a questo:

    {"myLists": [
      "Pippo e pluto",
      "Topolino e Minnie"
    ]}

**Bonus points**: se il parametro name è nullo o vuoto, invece di inserire null o stringa vuota nella lista, restituiamo un errore con codice 400 (bad request) e messaggio "parameter 'name' is required".  Prendi spunto da come generiamo il 404.

### HATEOAS

Il problema è che noi vogliamo che, per il principio HATEOAS, ogni documento JSON restituito contenga le url ai documenti successivi.  Quindi non dovrebbe restituire solo i nomi, ma le url complete delle liste.  Mi aspetto di vedere un output tipo:

    {"myLists": [
      {"name": "Pippo e pluto", "uri": "/todolists/0"}
      {"name": "Topolino e Minnie", "uri": "/todolists/1"}
    ]}

Gli identificatori 0 e 1 corrispondono agli "id" che dobbiamo inserire nella lista per ottenere l'elemento corrispondente... in altre parole e' l'indice dell'elemento.  Se usassimo un database, sarebbe l'id univoco assegnato da una colonna autoincrement, o da una sequenza di Postgresql.

In aggiunta, quando visito la url `/todolists/3` dovrei ricevere un documento JSON di questa forma:

    {"name": "Nome Della Lista", "items": []}

**Bonus points**: se visito la url `/todolists/3` e la lista non contiene un elemento con quell'indice, restituire un 404 Resource Not Found invece che un 500 causato dalla IndexOutOfBoundsException
