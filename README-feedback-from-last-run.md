

The problem domain is probably too complex.  
It's probably not broken enough along complexity dimensions: start with a system that deals with a single order at a time.  You don't need a list, you need only remember the last one.

Alternative problem: conference booking.  
 - If there are still places left, you can POST to /booking?conf_id and reserve your place.  The number of places left is decreased.
 - Add payment to a payment gateway
 - Add a timeout: you have a given number of minutes to complete payment or your reservation is lost
