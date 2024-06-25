# Getting Started

This project uses Kotlin, Spring Boot, and Gradle to create a simple REST API based on data from the [MET.no weather alert API](https://api.met.no/weatherapi/metalerts/2.0/current). 
The app runs on Spring Boot 3.3.1 and uses java 17. Run the app with the following command:

```./gradlew bootRun``` 

or run it in your IDE. There are two endpoints:
    
### GET /alerts:
```curl http://localhost:8080/alerts```
returns a list of all current alerts

### GET /nearby
```curl "http://localhost:8080/nearby"``` 
returns a list of all current alerts concerning the given latitude and longitude, e.g.:

```curl "http://localhost:8080/nearby?lat=59.9115&lon=10.7579"``` checks for alerts at Oslo Central Station

```localhost:8080/nearby?lat=70.239784&lon=22.348326``` checks for alerts in Øksfjord



### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.1/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.1/gradle-plugin/reference/html/#build-image)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

