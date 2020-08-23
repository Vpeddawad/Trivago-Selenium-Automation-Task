# cucumber_selenium
cucumber selenium project for Trivago

# Technologies and Tools used:

      Language: Java
      Test Automation Tool: Selenium WebDriver
      Test Automation Framework: Cucumber
      Design Pattern: Page Object Model
      Build Management Tool: Maven
      Logging Framework: Log4j
      IDE: Eclipse


# Web-Testing-With-Selenium
Project to test Trivago with Selenium and Cucumber

* This project has behaviour driven architecture for test project which has some test pages under tests.
* The project is following the Page Object pattern. Where a class express the interface of a page and nothing related to a test can be inside the class. So:
   * No assert can be done inside a page object as much as possible . 
    
### Prerequisites
* **Git
* **Java Development Kit
* **maven**: Use mavan 
* **IDE**: eclipse and eclipse cucumber intregation plugin.
* **Gherkin**

### Structure

#### Application mirror
* src/main/java: Page objects and base infrastruture libary ,generating test run log , reading the testconfig form ./conf/selenium.properties and fixture Ends with Fixture -seleenium wrapers api for code reusebility .

#### Application tests

* src/test/resources/Features : Create a structure in order to have all the features of a page in the same folder.
* src/test/java/resources: Drivers are kept here .
* src/test/java/runner:  Here you have the classes to run the tests ends with Test
* src/test/java/stepDefinitions: Create a StepDef per Feature, and follow the same package structure as the related feature
  
## Configuration
* This is a mavan project, you can import it from your  IDE as mavan project.
* To verify that all is up to date with dependencies and test compilation, execute the
* mvn clean 
* mvn build 


### How to build and execute the tests?
        1. To run from an Eclipse IDE using JUnit, right click on TrivagoSearchRunnerTest.java -> Select Run As -> JUnit Test
        2. To run from an Eclipse IDE using Maven, right click on Project -> Select Run As -> Maven test OR Maven install
        3. To run from command prompt/terminal using Maven, run the command 'mvn clean install' OR 'mvn install' OR 'mvn test' 

 
### Where to find results?
        You can find an execution report inside folder /trivago-cucumber/test-output-trivago/index.html
               
### Where to find logging information?
        You can find logging information selenium.log


