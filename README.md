# Technical Debt Management ToolBox
In the last years, the technical debt research community has been spending increasingly large effort on the different activities of Technical Debt Management (TDM). To a large extent, this research is focusing on issues related to cost and benefit analysis of software quality management.  

Technical Debt Management ToolBox is a ToolBox developed in the context of a European Union project H2020 (SDK4ED - https://sdk4ed.eu). It provides version history analysis in all aspects of technical debt (TD principal, TD interest, TD breaking point, TD interest probability).

**Technical Debt** is a software engineering metaphor, referring to the eventual financial consequences of trade-offs between shrinking product time to market and poorly specifying, or implementing a software product, throughout all development phases. It can be an appropriate business decision to take on technical debt, but if such debt is allowed to grow, the lack of quality of the system may eventually make it too expensive to maintain, at which point the business or team may need to declare “technical bankruptcy” and rewrite the application rather than continue to try to maintain it.

More on:
* A. A. Tsintzira, A. Ampatzoglou, O. Matei, A. Ampatzoglou, A. Chatzigeorgiou, and R. Heb, “Technical Debt Quantification through Metrics: An Industrial Validation”, 15th China-Europe International Symposium on Software Engineering Education (CEISEE’ 19), IEEE, Lisbon, Portugal, May 30-31, 2019

* Areti Ampatzoglou, Alexander Michailidis, Christos Sarikyriakidis, Apostolos Ampatzoglou, Alexander Chatzigeorgiou, and Paris Avgeriou,“A Framework for Managing Interest in Technical Debt: An Industrial Validation”, 1st International Conference on Technical Debt (TechDebt 2018), Gothenburg, Sweden, May 27-28, 2018

* A. Chatzigeorgiou, A. Ampatzoglou, A. Ampatzoglou, and Theodoros Amanatidis, "Estimating the Breaking Point for Technical Debt", 7th International Workshop on Managing Technical Debt (MTD'2015), Bremen, Germany, October 2, 2015

## Prerequisites
* **SonarQube (7.9 version):** SonarQube is an automatic code review tool to detect bugs, vulnerabilities and code smells in your code. It can integrate with your existing workflow to enable continuous code inspection across your project branches and pull requests. Learn more here - https://docs.sonarqube.org/7.9/. There is a detailed explanation of how SQ works, what are the prerequisites and how to do the installation.
* **PostgreSQL:** PostgreSQL is a powerful, open source object-relational database system. PostgreSQL is being used from SonarQube to save the analysis results.
* **SonarScanner:** The SonarQube Scanner is recommended as the default launcher to analyze a project with SonarQube. Find more on SonarQube's website.
* **MySQL:** MySQL is a freely available open source Relational Database Management System (RDBMS) that uses Structured Query Language (SQL). MySQL is being used from the Technical Debt Management ToolBox to save the analysis results.
* **Java 8:** Technical-Debt-Management-ToolBox is written and compiled in Java 8

## Optional
To have a more distrubuted system and to management all the services in a more efficiency way, you can use docker containers, one for each service.

## Installation

### Step 1: Build the project
After all the above services have been installed, have been configured properly and have been tested, open a Java IDE.

Use Maven to build the project.

```bash
mvn clean package
```

or

Use IDE extract jar options.

```bash
Go to Project -> Click -> Export -> Runnable JAR File. Then, select <Export generated class files and sources> and make sure that your project is selected.
```

### Step 2: Create Database

Open MySQL Database and create a new database. Keep in a safe place your username, password and database name. As a database name I used 'analyjed_jar' but you are completly free to choose the name you prefer.

### Step 3: Create & Configure configurations.txt file

```bash
# Configurations about Database
username=user
password=pass
serverName=localhost:5555
databaseName=analyjed_jar

# Configurations about sonarQube
sonarqube_execution:sonar-scanner
project_path:/Users/Projects/Project1
sonar_url:http://localhost:9906
```

The first 4 paremeters are the username and password to connect on MySQL, the server name with the port and the database name.
Then, you have to specify the exact location of SonarScanner binary, the folder in which you have saved the source code. Tha last folder is the same 
folder in which the extracted jar is located.

### Step 4: Put them all together

Create a folder in which you move the extracted jar, the configuration.txt file and the jars file from the externalTools folder.

```bash
mkdir TechnicalDebtManagementToolBox
mv path/technicalDebtManagmentToolbox.jar TechnicalDebtManagementToolBox
mv path/externalTools/metrics_calculator.jar TechnicalDebtManagementToolBox
mv path/externalTools/interest_probabilityNoGUI.jar TechnicalDebtManagementToolBox
mv path/externalTools/metrics_calculator_noOop.jar TechnicalDebtManagementToolBox
```

## Execution
* You need to have a git repositiry with the source code. The repository can be public or private.
* **This is ONLY applied to Java Projects** Inside the repository, in the main folder, there  **MUST** be a folder named "jars". Inside this folder, you should put the execution file (jar) of the source code. The name of the jar file should be tha name of the project + version number. For example, the project name is 'TDToolbox', you have 3 versions of it and 10 commits. **ONLY** for the 3 commits that are version you need to have the jar folder. For the first version the jar name should be TDToolbox0.jar, for the second version the jar name is TDTollbox1.jar, and for the third version the jars name is TDToolbox2.jar.

## Usage

Open the folder TechnicalDebtManagementToolBox and execute the tool.

```bash
cd TechnicalDebtManagementToolBox
java -jar technicalDebtManagmentToolbox.jar projectName language versions gitUrl gitUsername gitPassword shas
```
 
where:
* projectName: This parameter defines the project name
* language: Programming language. Possible values {"Java", "C", "C++"}
* versions: Number of software versions
* gitUrl: Url from git in order to clone the source code and do the analysis
* gitUsername: Username of the git account
* gitPassword: Password of the gi account. If it is public, just type 1 character for example 1
* shas: For each version, the user should provide the commit shas. Not all commits are versions. For example if there 10 commits and 2 of them are versions, the input is those 2 commits shas seperated by comma. For example: d8254832e8as29b4d29739dceaa18dhak201b9faf,ajaj48a38qa4f9b4d29739dceaa18354c01b9faf







