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

  </body>
</html>