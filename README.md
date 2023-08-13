# Aardvark

Group 40 (JAABVA): Alex Apostolu, Alex Mirabella, Amaru Izarra, Brian Latchman, Jessica Li, Vishnu Sai and Arthur the Aardvark

![Aardvark App Banner](/src/main/resources/images/banner.png)

This is the repository for the drawing and image creation software called Aardvark. This project is built using Java 11 and JavaFX. Below are some useful links related to the development of the project.

* [Figma Designs](https://www.figma.com/file/TGjtdbXueRo7Ui4jmofLry/Aardvark?type=design&node-id=41%3A2&mode=design&t=Hhz1FIdWzme6Wmlt-1)
* [Spec and User Stories](https://docs.google.com/document/d/1bEORd52PppURtDqi6MqqlkDdX-i_tyUgb_kfk-4wmmM/edit?usp=sharing)
* [Presentation Slides](/presentation.pdf)


## Local Development

The following steps will guide you in setting up the repo to run locally. Mainly tested using IntelliJ and Windows (but also checked to run on VS Code, MacOS). Assumes Java and Gradle are installed and setup on computer.

>0. Clone the repo:
```
git clone https://github.com/CSC207-2023Y-UofT/course-project-aardvark.git
```
>
>1. Open project folder in IntelliJ
>
>2. Navigate to the Gradle panel
>
>3. Under aardvark/Tasks/build, run the 'build' command
>
>4. Under aardvark/Tasks/application, run the 'run' command
>
>5. Start making changes and drawing!


## Coverage Test

Several methods could not be covered by automated unit tests due to their output being visual. In these cases they were manually tested.

![Aardvark Coverage Test Scores](/src/main/resources/images/coverage.png)


## Design Patterns

Aardvark was developed using SOLID principles, Clean Architecture, and a collection of essential design patterns.

### Model-View-Controller

The MVC pattern separates Aardvark into three layers: The model stores canvas info, shapes, colors, properties and data. The view includes UI components like the canvas, buttons, inputs and all other user interfaces. The controller manages user interactions, updating project and account data through model communication.

### Gateway

Aardvark utilizes the Gateway design pattern for user sign-in and sign-up, employing a UserDSGateway to manage interactions with the JSON file. This separation enhances maintainability, security, and flexibility, as the pattern isolates complex authentication details from the core application logic while allowing for independent testing and potential future service changes.

### Singleton

Aardvark employs the Singleton design pattern to ensure the existence of only one instance of a particular class throughout its runtime. This pattern is particularly useful for managing resources that should be shared globally across the application.