# Aardvark

Group 40 (JAABVA): Alex Apostolu, Alex Mirabella, Amaru Izarra, Brian Latchman, Jessica Li, Vishnu Sai, Arthur the Aardvark

This is the repository for the drawing and image creation software called Aardvark. This project is built using Java and JavaFX. Below are some useful links related to the development of the project.

* [Figma Designs](https://www.figma.com/file/TGjtdbXueRo7Ui4jmofLry/Aardvark?type=design&node-id=41%3A2&mode=design&t=Hhz1FIdWzme6Wmlt-1)
* [Spec and User Stories](https://docs.google.com/document/d/1bEORd52PppURtDqi6MqqlkDdX-i_tyUgb_kfk-4wmmM/edit?usp=sharing)

## Local Development

The following steps will guide you in setting up the repo to run locally. Mainly tested using IntelliJ and Windows (but also checked to run on VS Code, MacOS).

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

Several methods could not be covered by automated unit tests due to their output being visual. Hence, they were manually tested.

![Aardvark Coverage Test Scores](/src/main/resources/images/coverage.png)
