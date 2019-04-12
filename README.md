# JSONSearch

Application to search for a keyword in a JSON file containing movies details as array of JSON objects.

**Prerequisite**:

Build Tool: Apache-maven or Java IDE : IntelliJ or Eclipse
API Tests: POSTMAN or Web Browser

## Using Commandline

**-Step 1-**: Check out the code from git repository.

git checkout https://github.com/vinamrajain/JSONSearch.git

**-Step 2-**: Build the project

mvn compile

**-Step 3-**: Run the tests

mvn clean test

**-Step 4-**: Run the application

mvn springboot:run

**-Step5-**: Application is up and running. Use postman or web browser

endpoint: localhost:{port}/search/{keyword}

{port} - is port where apache tomcat is running.(default is 8080)
{Keyword} - is the keyword you want to search for in the JSON database.

## Using Java IDE

**-Step 1-**: Check out the code from git repository.

git checkout https://github.com/vinamrajain/JSONSearch.git

**-Step 2-**: Import Project as existing maven project.

**-Step 3-**: Use inbuild IDE buttons to compile, run tests and run the application.

**-Step 4-**: Once the application is up and running, use postman or web browser to

endpoint: localhost:{port}/search/{keyword}

{port} - is port where apache tomcat is running.(default is 8080)
{Keyword} - is the keyword you want to search for in the JSON database.


## Assumptions & Things to remember:

1. Example JSON dataset exists in /src/main/resources/Movies.json file.
2. Database can be update: New JSON objects can be added and even new keys can be added to each JSON object.
3. Important: Each JSON Object in the Movies.json must contain a key as "title".
5. Movies.json file may contain duplicate JSON objects (primary key : "title") but application will not count
 keyuwords for duplicate JSON objects with same movie title.
4. Application is case-insensitive when searching for keyword.
5. Both recursion and iterative implemetations are available. (by default : recursive)

