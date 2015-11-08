<#--
  This is a Freemarker template.  Cheatsheet:

	  How to expand variables:

	    ${foobar}

	  How to give a default value to a variable:

	    ${foobar!'defaultvalue'} or ${foobar!''}

    How to print the length of a sequence:

      ${animals?size}

	  How to print a loop:

  	  <#list animals as animal>
  	    <li>${animal.name} for ${animal.price} Euros
  	  </#list>

  	Note: "animal.price" in the template calls "animal.getPrice()" in Java
-->
<html>
  <head>
    <meta charset='UTF-8' />
    <title>Woody's Orders Shipping Department</title>
    <style>
      body {
        font-family: Verdana, Geneva, sans-serif;
      }
    </style>

  </head>
  <body>
    <h1>Woody's Orders Shipping Department</h1>

      <p id='summary'>Outstanding Orders: ${orders?size}</p>

      <#list orders as order>
        <hr/>
        <div class='shippable-order'>
          <p class='order-code'>Order Code: ${order.code}</p>
          <p class='article-code'>Article Code: ${order.articleCode}</p>
          <p class='address'>Address: ${order.address}</p>
          <form action='/orders/shipped' method='post'>
            <input type='hidden' name='order_code' value='${order.code}' />
            <p><input type='submit' value='Shipped!' /></p>
          </form>
        </div>
      </#list>

  </body>
</html>