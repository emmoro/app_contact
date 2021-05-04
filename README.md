## Project Kotlin with GRPC and Micronaut
* This project save the information about the Contact
* Is necessary tool to call the method in GRPC
* I used BloomRPC: https://appimage.github.io/BloomRPC/

## Requirements
You will need to following tools in order to work with this project and code:

* git (http://git-scm.com/)
* JDK 1.8 (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Maven 3.x+ (http://maven.apache.org/)
* An IDE of your choice. (Eclipse, IntelliJ, Spring STS, Netbeans, Sublime, etc.)
* BloomRPC

## Considerations
IDE used was IntelliJ

## Getting Started
To run this project locally, perform the following steps:

* Use this command to download the project to your machine: git clone https://github.com/emmoro/app_contact.git
* To install all of its dependencies and start each app, follow the instructions below:
* To run the server. Application address in BloomRPC: 0.0.0.0:50051
mvnw spring-boot:run


## Note
* To use this systems the address is 'http://localhost:8180/v1/contacts/'
* To use GRPC is necessry use a BloomRPC
