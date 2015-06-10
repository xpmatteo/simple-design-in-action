## Import the project in Eclipse

Install Maven.

Then run

    mvn eclipse:clean eclipse:eclipse -DdownloadSources -DdownloadJavadocs

Then use Import... > Existing Project in Eclipse.

## Run the project

In Eclipse, right-click on the it.xpug.lezione6.main.Main class and select Debug As... > Java Application.

Then observe the application at http://localhost:8080/

## ESERCIZI LEZIONE 8

# Scrivere un semplice test

... che verifica che se provo a create una lista con nome null, ottengo 400.

(Nota che c'e' gia' un test simile per nome vuoto.  Null e' un caso diverso.)

# Testare la url /todolists

Nel caso in cui ci sia una todolist, il JSON generato non e' corretto.  Scrivere il test,
e poi correggere il difetto.


# Testare due todo items

Abbiamo un test che verifica come viene restituita una lista con un item.

Modifica il test per creare due items e verificare che la uri del
secondo item sia corretta.


# Testare il checked-unchecked

    POST /todolists/0/items/0
    checked=true

...deve modificare lo status di checked/unchecked del Todo Item specificato nella url.

Scrivi i seguenti test:

 - Data una lista con due todo item, se faccio post su `POST /todolists/0/items/1`
   con il parametro checked=true, allora il secondo item cambia stato a checked

 - Come il precedente, passando da checked a unchecked

 - Prova che se cerco di mettere a "checked" un item che e' gia' in stato checked,
   il risultato non cambia.

