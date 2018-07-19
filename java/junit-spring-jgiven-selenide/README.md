# Junit / Spring / JGiven / Selenide

This example project illustrates browser user interface testing using the following technologies: -

1. [Junit](https://junit.org/junit5) - industry standard in running Java unit tests.
2. [Spring](https://spring.io/) - used for dependency injection and configuring properties.
3. [JGiven](http://jgiven.org) - Developer friendly Java-based DSL for specifying features and
   scenarios, purely in Java code (e.g. no feature files required).
4. [Selenide](http://selenide.org/) - Wrapper for Selenium aimed at simplifying user interface
   testing.
5. [WebDriverManager](https://github.com/bonigarcia/webdrivermanager) - automates Selenium WebDriver
   management.

### Get started.

The project uses Google Search (http://www.google.com) as the target project being tested.

Run execute tests run: -

    mvn clean verify

Nice JGiven reports are available in the `target/jgiven-reports/html` folder.

### Folder / Package Structure

This project only contains test code - everything lives in the `src/test/java` and
`src/test/resources`.

The `src/test/java` folder contains the root `co.videofirst.vft.uitests` package.  The sub packages
include: -

1. `configuration` - This contains a `TestConfiguration` class which configures the tests (using
                     Spring). This includes enabling JGiven support, configuring the property files
                     and setting up selenium web drivers.
2. `features` - The high-level JGiven "feature" classes live here e.g. (`BasicSearchTest` and
                `AdvancedSearchPage`).
3. `stages` - These are mid-level JGiven "stage" classes live here.  Features use them and they help
              promote code re-use.  There are 3 types of stage classes which live in sub packages
              including: -
  * `given` - A "given" class e.g. `GivenIamAtTheHomePage` generally specify system / user setup.
  * `when`  - A "when" class e.g. `WhenISearch` mimic user actions e.g a user filling out a Google
              search.
  * `then`  - A "then" class e.g. `ThenSearchResultsReturn` verify the system is in the correct
              state from actions from the `when` classes.
4. `pageobjects` - These are the lowest level classes (Spring components) which do Selenium browser
                   actions (using the Selenide wrapper).

### Next Steps

Please feel free to use / modify this project - if you've any questions you can contact the author
Bob Marks at [bob@videofirst.co](bob@videofirst.co).

