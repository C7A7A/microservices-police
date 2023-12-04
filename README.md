
<a name="readme-top"></a>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
        <a href="#built-with">Built With</a>
    </li>
    <li>
        <a href="#how-to-launch">How to launch?</a>
    </li>
    <li>
        <a href="#detailed-description">Detailed description</a>
    </li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About the project
A microservices architecture-based project that leverages Kafka for event-driven communication. The emergencies project processes a police report received by the user. Based on the filled-in form, it decides which type of report it is dealing with and sends the relevant data to the kafka topic. The vehicles and policemen microservices receive the data from kafka, prepare the vehicles and policemen and send the appropriate data to the kafka topic. Emergencies receives the data, merges it together, sends a success message to the kafka topic and finishes working. Saga pattern is implemented to guarantee the final consistency of processing if something goes wrong in vehicles or policemen service. <br>

Project consists of:
<ul>
    <li> emergencies - REST gateway </li>
    <li> policemen - REST service </li>
    <li> vehicles - SOAP service </li>
    <li> base-domains - project for shared classes </li>
    <li> vehicles-ui - client for vehicles (used only for test purposes) </li>
</ul>

Microservices are ready to dockerize thanks to Dockerfiles and docker-compose.yml. 

### Event-driven architecture
![Architecture](/images/events-diagram.PNG)
```
- GREEN - everything goes well (EMERGENCY_ACCEPTED -> VEHICLES_READY + POLICEMEN_READY -> EMERGENCY COMPLETED)
- RED - something goes wrong in vehicles or policemen service (VEHICLES_ERROR/POLICEMEN_ERROR -> POLICEMEN_FAILED/VEHICLES_FAILED)
- PURPLE - Something went wrong and microservice needs to backward recovery (POLICEMEN_FAILED/VEHICLES_FAILED -> VEHICLES_BACKWARD_RECOVERY/POLICEMEN_BACKWARD_RECOVERY -> EMERGENCY_FAILED)
```

Microservices communicate with each other with events. These are sent to kafka topics and consumed by other services. Saga pattern is implemented to guarantee the final consistency of processing.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Built With
[![Java][Java]][Java-url]
[![Spring][Spring]][Spring-url]
[![Kafka][Kafka]][Kafka-url]
[![Docker][Docker]][Docker-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- How to launch -->
## How to launch
### Locally
  1. Set environment variable `POLICE_KAFKA_SERVER` to port on which your kafka will be listening (default: localhost:9092).
  2. Start zookeeper and kafka
  3. Launch projects (emergencies, policemen, vehicles) 
  4. Everything should be working. Test app using API platform, form (http://localhost:8083/) or Swagger (http://localhost:8083/swagger-ui/index.html#/). You can get payloadId from logs.

### Docker
  1. Start docker
  2. Build projects (emergencies, policemen, vehicles, base-domain) <br>
    - gradle: `./gradlew build -x test` <br>
    - maven (vehicles): `mvn clean install -DskipTests` <br> 
  3. Build docker image for every project: `docker build -t <jar-name> .` You will find jar-name in Dockerfile
  4. Take a look into docker-compose.yml and configure it for your needs. If you don't plan to make any changes everything should work out of the box.
  5. Move do /docker folder and start docker container `docker-compose up -d`
  6. Everything should be working. Test app using API platform, form (http://localhost:8083/) or Swagger (http://localhost:8083/swagger-ui/index.html#/). You can get payloadId from logs.


<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->

<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[Java]:https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://docs.oracle.com/en/java/
[Kafka]:https://img.shields.io/badge/Apache%20Kafka-000?style=for-the-badge&logo=apachekafka
[Kafka-url]: https://kafka.apache.org/intro
[Spring]:https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=Spring&logoColor=white
[Spring-url]: https://spring.io/projects/spring-boot
[Docker]:https://img.shields.io/badge/Docker-0db7ed?style=for-the-badge&logo=Docker&logoColor=white
[Docker-url]: https://docs.docker.com/
