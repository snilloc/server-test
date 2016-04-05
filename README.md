# InMotion Server Test

This repository is for forking as a basis of the InMotion server developer programming test.
It provides a basic server application skeleton using the Dropwizard framework.

The goal of this test is to show your skill with providing implementations of simple services.
There is no correct answer, we are looking for functionality as well as style. This code should
be representative of your best development effort.

For this test we want an application that manages a personal movie collection.
No UI needs to be presented for this, this is a service only provider.

A movie entry should contain at a minimum the following information : 
* name
* genre
* year released
* rating

You may manage data in a simple fashion with something like a global in-memory list, or you can earn
bonus points by using the H2 in-memory database. [http://www.h2database.com/html/quickstart.html]

You should modify this skeleton to provide the following services :

| Service Route     | Functionality |
| ---               | --- |
| /api/timeOfDay    | Returns the current local time |
| /api/movie/create | Create new movie entry |
| /api/movie/update | Updates an existing movie entry |
| /api/movie/delete | Deletes a movie entry |
| /api/movie/list   | Returns list of movie entries |

# Requirements
* You must use git and a fork of this repository. Use the "Fork" button at the top of the page.
* You must use Java.
* You must use the DropWizard framework provided.
* Requests and responses should be modeled with JSON.
* You must submit the final working server source to us.

___

## Getting Started
### Pre-requisites
* You will need an account with Github. [https://github.com/]
* You will need Java 8 SDK installed. [http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html]
* You will optionally need Gradle installed to build. [http://gradle.org/] 
The project comes with a gradle wrapper. On Windows, you may execute gradlew.bat to install the needed files.
* The build process will need to download libraries from the internet for use in the project.
* Ports 8080 and 8081 must not be in use on your local machine. You may change the ports in the application configuration, if needed.
* You may use any IDE to do your development, however the build must be done with the included gradle build spec.

### Reference
* Please refer to the Dropwizard Framework documentation. [http://www.dropwizard.io/0.9.2/docs/]

### Delivery
* You can send the URL to your forked github repository. This must be a public repository that we can access.
* Or you can send a downloaded zip of your repository. Use the "Download ZIP" button on the github repository page.