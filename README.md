# TestMercadoLibre

Esta API REST consta de dos servicios: 

/mutant

El metodo POST recibe un JSON con el formato: {"dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}. Permite determinar si el DNA de un sujeto corresponde a un mutante o a un humano, y lo guarda en la Base de datos. 

[URL_APP]/resources/mutant

/stats

El metodo GET devuelve las estadisticas de las verificaciones de DNA en un JSON con el formato:{"count_mutant_dna":40, "count_human_dna": 100: "ratio":0.4}

[URL_APP]/resources/stats

Para poder ejecutar la API es necesario deployar el War en un servidor de aplicaciones que contenga los rescursos especificados en el documento: glassfish-resources.xml. Además es necesario tener creada una BD con el script estructura_inicial.sql.

Para poder probar la aplicación, se puede usar cualquier herramienta que permita hacer peticiones POST y GET. Además en la URL [URL_APP]/test-resbeans.html se dispone de una herramienta para dicho fin.

Se pretende dejar disponible la app en la URL: [URL_APP] = http://testml.myftp.org, pero por problemas con el proveedor de internet no se ha podido aún, por lo que es necesario solicitar una URl que será válida por 8 hs, que tendrá el formato: http://[NUM_ALEATORIO].ngrok.io ([URL_APP]: http://e5eb04ca43d7.ngrok.io   (Válida hasta 22:27 30/6)).



