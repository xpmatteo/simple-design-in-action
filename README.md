
# Simple design in action!

## The problem

Woody's Mart is a big retail chain.  They recently built an ecommerce website; the website generates orders that must be executed by the Order Fulfillment Department.  We are writing a support app for the Order Fulfillment Department.

It works like this: we expose an API that receives new orders.  The operators can monitor outstanding orders on a web page.  When they fulfill an order, they mark the order as "done", and it disappears from the list.  You can think of it as a sort of "to-do list".

Once the basic functionality is done we'll handle the more complex business cases.

The inspiration for this exercise comes from the classic [kata 16 by Prag Dave](http://codekata.com/kata/kata16-business-rules/)


## What are we trying to learn?

The main thing that makes programming difficult is that the complexity of the business rules is mixed up with the complexity of setting up the infrastructure: web, database, etc.  In other words, the functional requirements are mixed up with the non-functional requirements.

Simple design overcomes this problem by clearly separating functional code (domain model) by non-functional code (non-functional code).

In addition to that, in simple design we make infrastructure code **very** simple.

So this is the learning goal: **make infrastructure code so simple that it lets us solve business problems easily.**


## How does this exercise work?

You download and install our git repository.  There are several stages, each one is a separate branch.  In each stage there are failing tests that you will have to make pass, one by one.  Once the stage is over, you can pass to the following stage.  If you didn't finish implementing the previous stage, don't worry: every stage contains a solution to the previous one.

## What are the stages?

1. Set up a web-based Hello, world!
2. Again Hello, world, but now we use Controller and View objects
3. Implement the "order shipping" scenario
4. Add a nicer UI and html templates
5. Add database persistence


## Quickstart

### Import the projects in Eclipse (the manual way)

Use Import... > Existing Project in Eclipse.


### Import the projects in Eclipse (with maven)

Install Maven.

Then run

    mvn eclipse:clean eclipse:eclipse -DdownloadSources -DdownloadJavadocs

Then use Import... > Existing Project in Eclipse.


### Import the projects in IntelliJ Idea (with maven)

Execute

    mvn idea:idea


### Run the project

In Eclipse, right-click on the it.xpug.todolists.main.Main class and select Debug As... > Java Application.

Then observe the application at `http://localhost:8080/`

In most cases, you will be able to make changes to a class and see it reloaded automatically when you refresh the browser.  In some cases, you'll have to restart the server, which should take less than a second.

Another way to run the application is to run the script

    script/run.sh


### Manual test

We have provided a test page that sends test data to the order entry API.  Find it at

    http://localhost:8080/enter-order.html

Then observe your orders at

    http://localhost:8080/orders/list

(but this last page is probably not implemented yet!)


# Stage 1

Checkout the `master` branch.

The goal of this stage is to have a well-tested "hello, world" web application.

Run all the tests.  You will see one fail.  Make it pass.  Un-ignore the next test.  Make it pass!

When all the tests pass, check that it also works in the browser!


# Stage 2

... same process, in the stage-02-hello-mvc project

