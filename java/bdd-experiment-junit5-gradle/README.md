# BDD Experiment using Junit5 + Gradle

This experiment is around creating an advanced feature file e.g. `search-for-film.vfa` (`vfa` 
extension = "Video First Automation").

### Background

The background to this experiment are thoughts around exporting features / scenarios from the 
[https://app.videofirst.io](Video First app).  From a manual test (with various steps such as BDD
steps such given, when, then etc) then we would have a feature file as follows: -

    Feature: Search for a film in various different ways
    
      This feature will search the IMDB website for various films.
    
      Scenario: Search for film "The Green Mile"
    
        Given a user is at the home page                        :
         When the user types "The Green Mile" into search box   :
          And the user clicks the search icon                   :
         Then I expect to see the results page                  :
          And the top result only contains "The Green Mile"     : 

> NOTE: Based on https://cucumber.io/docs/gherkin/reference/.

The idea is to move from manual to automated testing is simply a matter of filling in the square
brackets `[ ... ]` i.e.

    Feature: Search for a film in various different ways
    
      This feature will search the IMDB website for various films.
    
      Scenario: Search for film "The Green Mile"
    
        Given a user is at the home page                        : open https://www.imdb.com
         When the user types "The Green Mile" into search box   : type name=q "The Green Mile"
          And the user clicks the search icon                   : click id=suggestion-search-button
         Then I expect to see the results page                  : text_contains .findHeader "Results for" 
                                                                : ui_text_contains css=.findSearchTerm "The Green Mile"
          And the top result only contains "The Green Mile"     : exists "xpath=//td[@class='result_text']/a[text() = 'The Green Mile']" 

The actions in the square brackets are as follows: -

 * `open` will then hit a registered action class `UiOpen` which will run a selenium browser.
 * `type` will select the page element where `name` is equals to `q` and type `The Green Mile`.
 * `click` will select page element with `id` = `suggestion-search-button` and click it.
 * `text_contains` will ensure the element with CSS selector `.findHeader` contains text `Results for`.
 * `exists` ensures that an element with an `xpath` selector exists.
 
The idea is that non-developers can create these features files without coding experience.

### Technologies

This example project uses the following technologies: -

1. [Junit](https://junit.org/junit5) - industry standard in running Java unit tests.
2. [Selenide](http://selenide.org/) - Wrapper for Selenium aimed at simplifying user interface
   testing.
3. [WebDriverManager](https://github.com/bonigarcia/webdrivermanager) - automates Selenium WebDriver
   management.

### Get started.

The project uses IMDB as the target website being tested.

Run execute tests run: -

    ./gradlew clean build

### Folder / Package Structure

This project only contains test code - everything lives in the `src/test/java` and
`src/test/resources`.

The `src/test/java` folder contains the root `co.videofirst.vft.uitests` package.  The sub packages
include: -



### Next Steps

Please feel free to use / modify this project - if you've any questions you can contact the author
Bob Marks at [bob@videofirst.io](bob@videofirst.io).

