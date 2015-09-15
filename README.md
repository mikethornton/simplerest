# simplerest
Simple angularjs front end with RESTful api using Spring MVC

Build
You will need to configure your database details in src/main/resources/application-dev.properties. It is currently set up to use mysql, but can be changed by updating these properties and providing the appropriate connector in pom.xml.

Build with maven and run using spring boot, command is:

mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=dev"

Then navigate to http://localhost:8080.
