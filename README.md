# t2cChallenge: API Endpoint
## Ejecución del proyecto.
 El proyecto se ha realizado en Spring Boot (Core) con los módulos básicos de PostgreSQL, JDBC, Mockito y Junit en Maven. Asumiremos, por tanto, que Maven está instalado en el ordenador del usuario ejecutando el programa. Si no, su instalación puede realizarse mediante el comando `sudo apt-get install maven` en sistemas Debian o derivados y `sudo yum install maven` en sistemas RedHat o derivados. Para otros sistemas consultar https://maven.apache.org/install.html
 
La base de datos escogida ha sido PostgreSQL. En particular no ha sido escogida por ningún requerimiento en especial y bases de datos como MySQL o OracleDB podrían haber sido usadas (la única elección directa ha sido usar bases de datos relacionales, puesto que estas dos últimas también cuentan con un gran apoyo de la comunidad y por tanto una documentación extensa en internet). PostgreSQL en particular cuenta con diversos forks para distribuir la carga de la BBDD en diversos sistemas, lo que podría ser útil en caso de necesidad de migración debido al aumento de uso de la API (caso hipotético, no relacionado con el challenge). Para que la ejecución y configuración de la base de datos sea aislada del sistema propio y pueda ser distribuido en cualquier servidor, he optado por encapsularla en un contenedor Docker.

Para ejecutar la aplicación, por tanto, primero tendremos que construir la imagen de Docker en nuestro sistema local, para hacerlo nos situaremos en el directorio raíz (en adelante `<rootFolder>` y ejecutaremos:

    sudo docker build -f docker-image/Dockerfile . -t rballeba/t2cchallenge

(o en caso de tener un usuario para docker, ejecutar sin la instrucción `sudo` en este usuario). 

Este comando construirá una imagen de docker preparada para exponer el puerto 5432 al exterior (estándar de Postgre) junto a una base de datos "concessionaire" con usuario "cars" de contraseña "cars" como administrador de la misma (usuario que usa la aplicación). Nuestro modelo de datos relacionales son dos tablas, consultables en la carpeta `<rootFolder>/sql/sqlCreation.sql`. Nuestro modelo de datos es simple y contiene dos tablas, Cars y Concessionaires, donde Cars tiene una foreign key a Concessionaires, relacionando un coche con su concesionario. Ambas tienen primary key creciente automáticamente, por lo que para añadir un nuevo elemento no se tendrá que enviar este campo. (No he utilizado la dirección como primary key ya que los strings pueden ocasionar problemas en función del formato escogido y rompe la monotocidad de elección de las primary key para que sean consistentes durante todo el programa, también rompiendo así una clase genérica que podría ser creada para utilizar la metodología Domain Driven Design.

Una vez ejecutada la imagen y en la misma carpeta, la base de datos se ejecuta con el comando:

    sudo docker run -i -p 7432:5432 rballeba/t2cchallenge

que mapea el puerto de docker 5432 con el puerto local 7432, que es el que usuará el programa.

Una vez realizado esto, el proyecto está listo para ejecutar. En el directorio

    app/Concessionaire

se pueden ejecutar todas las instrucciones de Maven para Spring Boot, en particular

    mvn spring-boot:run
para ejecutar el proyecto y 

    mvn test
para ejecutar los tests asociados.

Para comprobar en todo momento los valores de la base de datos, desde una terminal cualquiera, ejecutar

    sudo docker ps
y con la ID del servicio de docker ejecutando nuestra BBDD, lanzamos

    docker exec -it <imageExecutionId> psql concessionaire cars
.
## Decisiones de diseño y trabajo a realizar
Es importante destacar que a día de la entrega hay un error sutil en las fechas introducidas en los JSON. Al deserializar usando Jackson, el manager de JSON por defecto de Spring, en algunos verbos HTTP te devuelve una zona horaria y en otros otra (en POST devuelve CEST y en PUT devuelve CET),  por tanto, a veces podemos encontrarnos con resultados inesperados en los GETs temporales debido a la actualización de la base de datos con diferentes zonas horarias. No parece algo excesivamente difícil de arreglar pero es un punto a tener en cuenta, ya que no me ha dado tiempo a seguir trabajando en ello por el plazo de entrega (he decidido priorizar otras cosas).

Por otra parte, otro trabajo a realizar es la separación de la lógica y de la implementación programando basado en interfaces. Como véis solo hay clases en nuestro proyecto, aunque en mi forma diaria de programar (con algo más de tiempo) todas las clases que no fuesen contenedores de datos y que tuviesen algún tipo de funcionalidad estarían tipadas en una interfaz y todas las clases dependerían de la interfaz. No lo he hecho aquí debido a que me parecía prioritario implementar Dependency Injection (que ha quedado implementado) con beans y no tenía claro como asociarlos a interfaces en vez de a servicios (aunque es realizable, ya que en mi proyecto anterior estaba configurado de esta manera con módulos de Google Guice). 

Por último, otro trabajo a realizar en la arquitectura hexagonal presentada sería separar los DTO (data transfer object) en función de la capa en la que nos encontramos (estoy usando indistintamente los DTOs para la capa de endpoint como para la capa de negocio porque en este caso me ahorraba tiempo, pero considero que es mejor tener objetos por separado para no acoplar la entrada de datos a la lógica de negocio). (De hecho, otro cambio sería usar un DTO propio para la actualización, y no un único car, que puede llevar a confusión, pero necesitaba más refinamiento y por tanto era complejo en el tiempo dado).

## API
Se ha intentado implementar una API Rest lo más pura posible con los siguientes métodos (que cumplen las restricciones del enunciado).


