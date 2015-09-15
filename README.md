# simplerest
Simple angularjs front end with RESTful api using Spring boot, MVC and data JPA.

Build
You will need to configure your database details in src/main/resources/application-dev.properties. It is currently set up to use mysql, but can be changed by updating these properties and providing the appropriate connector in pom.xml.

Build with maven from the project root using the following commands:

mvn package

java -Dspring.profiles.active=dev -jar target/simplerest-0.0.1-SNAPSHOT.jar

Then navigate to http://localhost:8080.
