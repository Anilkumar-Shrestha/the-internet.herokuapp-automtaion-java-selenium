# the-internet.herokuapp-automation-java-selenium
This project is designed to enhance test automation skills through practical exercises using Java, Selenium WebDriver, and TestNG. The target website for testing is [https://the-internet.herokuapp.com/ ], which provides a variety of scenarios for testing.

Objective
The primary goal of this project is to improve proficiency in writing automated tests by practicing various scenarios on a real website. By leveraging Selenium WebDriver and TestNG, participants can gain hands-on experience in web application automation.


#### Technology Used: 
* [Java](https://www.java.com/en/) - programming language 
* [TestNG](https://github.com/cbeust/testng) - Testing framework
* [Selenium](https://github.com/SeleniumHQ/selenium) - Browser automation framework
    * [Selenium Grid](https://www.selenium.dev/documentation/grid/) - Server to run web browser instances on remote machines.
* [Maven](https://maven.apache.org/) - Build management tool
* [Allure](https://docs.qameta.io/allure/) - Reporting Framework
* [ExtentReport](https://www.extentreports.com/docs/versions/5/java/index.html) - Reporting Framework
* [Logback](https://logback.qos.ch/) - Logger


#### Framework Used: Page Object Model(POM).


# Table of Contents
1. [Pre-requisites](#preReq)
1. [Requirements and Installation](#req)
2. [Usage](#usage)
2. [TestRail Integration](#TestRail)
3. [Logging and Reporting](#Report)
4. [Folder Structure definition](#folderstructure)
5. [Email checks](#emails)
5. [Load Testing Setup](#loadTesting)
5. [Credits and Useful Links](#credits)



## Pre-requisites <a name="preReq"></a>
To Run and Contribute in the project,
You need to install git( [How to install git??](https://www.stanleyulili.com/git/how-to-install-git-bash-on-windows/)) and clone this project in your local computer.
```
git clone https://github.com/Anilkumar-Shrestha/the-internet.herokuapp.automation-java-selenium
```
## Requirements and Installation <a name="req"></a>
<P> In order to utilise this project, you need to have the following below installed on your system.

1. [X] Java >10 recommended version OR Java Software Development Kit (SDK).
1. [X] Set JAVA_HOME environmental variable
1. [X] Install Apache maven [Installation Guide](https://maven.apache.org/install.html)


## Usage <a name="usage"></a>

#### 1. Defining the Browser::
Below checked are the currently supported browser.

1. [X] Chrome
1. [X] Firefox
1. [ ] Safari
1. [ ] Edge
1. [ ] Internet Explorer


- By default, the project will take Chrome browser, if no browser specified.
- To express a specific browser type, you can either 
  * edit the [config.properties](src/main/resources/config.properties) and add `Browser=firefox` line.
  * pass argument `-Dbrowser=firefox` at runtime.
  
- To express a specific browser type for each testcase in a testsuite then
  * add `<parameter name="browser" value="firefox"></parameter>` in each test in your suite xml file.
  
[!] ***Browser set at [config.properties](src/main/resources/config.properties) takes precedences over the browser parameter set at testcase/testsuites level or send at runtime.***


#### 2. Running tests.
You can run the project locally or remotely.
  1. Running tests locally

      - Go to [config.properties](src/main/resources/config.properties) file and set `remoteRun=false`.
      - Open the project root folder in command line.
      - Run `mvn clean test -DsuiteXmlFile=suiteFiles/e2eTests.xml` command for running EndToEnd tests
 1. Running test in Jenkins
     - Can be run on [Jenkins](https://www.jenkins.io/doc/book/pipeline/getting-started/#defining-a-pipeline-in-scm)
     - Select `Build with parameters` while setting up and select/provide the build parameters.

<H3>Debugging::</h3>

For debugging, you can use command line as: `mvn clean -Dmaven.surefire.debug test -DsuiteXmlFile=suiteFiles/e2eTests.xml`
and the tests will automatically pause and await a remote debugger on port 5005. You can then attach to the running tests using your IDE(IntelliJe/Eclipse). You can setup a "Remote Java Application" launch configuration via the menu command "Run" > "Open Debug Dialog..."


## Logging and Reporting <a name="Report"></a>
#### 1. Logging

Logging can be easily added to the tests by using the static methods from the Log class. Need to import `import framework.utility.loggerator.Logger.getLogger;`

Available logging levels are:

|Logging Level|Method                                       |
|:------------|:--------------------------------------------|
|Info         |getLogger().info("This is an info level message");   |
|Warn         |getLogger().warn("This is a warning level message"); |
|Error        |getLogger().error("This is an error level message");  |
|Debug        |getLogger().debug("This is a debug level message");   |
|Trace        |getLogger().trace("This is a trace level message");   |
|Fatal        |getLogger().trace("This is a fatal level message");   |

Note: If you want to change the log to be printed in file level or console level then you can do by editing the file [logback.xml](src/main/resources/logback.xml).

#### 1. Reporting
This project framework uses two report frameworks: 
1. This project framework uses the ***[extentreports](https://www.extentreports.com/docs/versions/5/java/index.html)*** custom report library. By default, screenshots are taken after each step and after a failure (if any), which will display on each step of the report.
If you want to take screenshot in any point to save the screenshot of browser then you can call `takeScreenshot` method with filepath and filename parameters.

Report will be created with the date appended to the report html file. ex: `TestReport_2024_05_27.html`

screenshots will be inside the script run dateFolder/MethodName. ex: `2021-10-23/MethodName/fail_SS_15_07_46.png`

 
2. Second report tool is ***[Allure](https://docs.qameta.io/allure/)*** . To open the allure report after the test run is finished (or in runtime) - run `mvn allure:serve` in the project root folder. Report will be opened automatically in the default browser.
   
**Note**:

* if you want to exclude screenshot for Passed TestCase then one can do by going to [config.properties](src/main/resources/config.properties) file and set `excludeScreenshot=true`.
* Report will be overwritten after each run on same day.

## Folder Structure definition <a name="folderstructure"></a>
We are using the Page Object Model(POM) design pattern, so the folder structure is define accordingly.
 
 ```text
                                   
├───allure-results      <----------- This folder will contains allure results
├───reports             <----------- This folder wil contains extent report
├─── SuiteFiles         <----------- Here placed test suites   
└───src
    ├───main
    │   ├───java
    │   │   ├───application     <--- This folder contains application page wrappers, actions/steps, data wrappers... 
    │   │   │   ├───actions     <--- This folder contains pages action/events
    |   |   |   |───api         <--- This folder contains API request methods
    │   │   │   ├───enum        <--- This folder contains enum classes for using in application
    │   │   │   └───pages       <--- This folder contains pages locator
    |   |   |   |───TestData    <--- This folder contains Test data 
    |   |   |   |───TestBase    <--- This class contains reusable methods in each test                              
    │   │   ├───framework       <--- This folder contains code regarding framework core level
    │   │   │   ├───driver      <--- Here placed driver wrapper and other classes for driver level
    │   │   │   ├───elements    <--- Here placed wrappers for web elements
    │   │   │   ├───models      <--- Here placed Assertion model to store assertion status/description.
    │   │   │   └───utility     <--- Here placed utility classes for working with API, files, properties, etc...
    │   │   ├───listeners       <--- Here placed listeners included in test run                                                               
    │   └───resources           <--- This folder contains configs and resources regarding application level
    └───test                    
         └───java
           └───TestCases       <--- Here placed test classes with tests

 
```


## Contributions
Contributions to this project are welcome! If you have suggestions for improving existing tests, adding new scenarios, or enhancing the documentation, please feel free to submit a pull request.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

  

