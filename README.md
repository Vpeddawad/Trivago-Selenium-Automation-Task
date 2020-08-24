### Cucumber_selenium
Cucumber Selenium Project for Trivago Hotel Search
Full Project can be accessed on the Git Hub. Please find Git Hub link below -
https://github.com/Vpeddawad/Trivago-Selenium-Automation-Task

### Technologies and Tools used:

      Language: Java
      Test Automation Tool: Selenium WebDriver
      Test Automation Framework: Cucumber
      Design Pattern: Page Object Model
      Build Management Tool: Maven
      Logging Framework: Log4j
      IDE: Eclipse


### Web-Testing-With-Selenium
Project to Test Trivago Hotel Search with Selenium & Cucumber

* This project has Behaviour Data Driven architecture for test project which has some test pages under tests.
* The project is following the Page Object pattern. Where a class express the interface of a page and nothing related to a test can be inside the class. 
* So, No assert can be done inside a page object as much as possible . 
    
### Prerequisites
* **Git
* **Java Development Kit
* **maven**: Use mavan 
* **IDE**: eclipse and eclipse cucumber intregation plugin.
* **Gherkin**

### Structure

### Application mirror
* src/main/java: Page objects and base infrastruture libary ,generating test run log , reading the testconfig form ./conf/selenium.properties and fixture Ends with Fixture -seleenium wrapers api for code reusebility .

### Application tests

* src/test/resources/Features : Created a structure in order to have all the features of a page in the same folder.
* src/test/java/resources: Drivers are kept here .
* src/test/java/runner:  Here you have the classes to run the tests ends with Test
* src/test/java/stepDefinitions: Created a Step Defination per Feature, and follow the same package structure as the related feature
  
### Configuration
* This is a mavan project, you can import it from your  IDE as mavan project.
* To verify that all is up to date with dependencies and test compilation, execute 
* mvn clean 
* mvn build 


### How to build and execute the tests?
        1. To run from an Eclipse IDE using Maven, right click on Project -> Select Run As -> Maven test OR Maven install
        2. To run from an Eclipse IDE using JUnit, right click on TrivagoSearchRunnerTest.java -> Select Run As -> JUnit Test
        3. To run from command prompt/terminal using Maven, run the command 'mvn clean install' OR 'mvn install' OR 'mvn test' 

 
### Where to find results?
        You can find an execution report inside folder /trivago-cucumber/test-output-trivago/index.html
               
### Where to find logging information?
        You can find logging information selenium.log
        
### Challenges Faced Any?
      Trivago does not allow automated browsers for redirection to another web site of advertiser partner. 
      When we click on any of the lowest hotel rates it will re-direct to the advertiser partners website but this action is not allowed by Trivago 
      when its accessed through any automation tools. 
      Whenever such actions are performed it gives below access denied error -
           " --
            Access Denied
            You don't have permission to access "http://www.trivago.com/forward.php?" on this server.
            Reference #18.8c851502.1598206786.13c0bfb1
            
            --"
  ### How did overcome the Challenges Faced?
        To overcome above access denied error, whenever above actions are performed using automated browser , the redirecting URL has been captured and 
        then that URL is opened in new browser to get rid of Access denied error. 
        Also we could have done with alternative options to automate using web services as well.


