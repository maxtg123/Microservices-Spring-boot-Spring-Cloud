* Setup the infrastructure and tool
  - You need to configure the file: docker-compose.yml
    + Let's configure some services: postgresql, pgadmin, zipkin, mongodb, mongo-express, zookeeper, kafka and mail-dev in file docker-compose.yml
    + [Instructions for installing Docker on Windows](https://docs.docker.com/desktop/install/windows-install/), If it is another environment, please choose the appropriate environment.

* Add configuration add spring cloud
  - Create Project Metadata at the following path:[Install Metadata Spring](https://start.spring.io/).According to the following options:
    + Project: Maven.
    + Spring Boot :3.3.2 or lower.
    + Packaging : Jar.
    + Java : 17.
    + Add Dependencies : Config Server. Finally download

* Create and configure the discovery server
