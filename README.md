# Sample application

This is an empty, playground application.  You may start developing by changing the example servlet.

## Import the project in Eclipse

Install Maven.

Then run

    mvn eclipse:clean eclipse:eclipse -DdownloadSources -DdownloadJavadocs

Then use Import... > Existing Project in Eclipse.

## Run the project

In Eclipse, right-click on the it.xpug.example.main.Main class and select Debug As... > Java Application.

Then observe the application at http://localhost:8080/

## Exercises

### Hello, World

  0. Visit http://localhost:8080/hello in the browser.  You should see Hello!

  1. Change ExampleServlet so that it returns Hello, world! in big, bold letters (it should be inside a h1 header.)

  2. Change ExampleServlet so that if you visit http://localhost:8080/hello?name=Pippo, you see "Hello, Pippo!" instead.  Note that if no parameter name is given, it should still print "Hello, world!" (that is, every new thing that you add to the application should not break what was working before.)

### Temperature Conversion

  When you visit http://localhost:8080/temp (note the url is different from the previous exercise) you should see a form like the following:

      +--------------------------------+
      |    Temperature Conversion      |
      |                                |
      |   Celsius                      |
      |   +-----------------------+    |
      |   |                       |    |
      |   +-----------------------+    |
      |                                |
      +--------------------------------+

That is, there should be a single text field.

  If I enter a number, for instance "0" in the text field, and press return, the page should reload and I should see

     +--------------------------------+
     |    Temperature Conversion      |
     |                                |
     |    0.0 C = 32.0 F              |
     |                                |
     |   Celsius                      |
     |   +-----------------------+    |
     |   | 0.0                   |    |
     |   +-----------------------+    |
     |                                |
     +--------------------------------+

 That is: there is a paragraph in the page that contains the conversion from Celsius to Fahrenheit.  The input field contains my previous input, with exactly one decimal digit.

 The formula for converting from Celsius to Fahrenheit is easily found with Google.

