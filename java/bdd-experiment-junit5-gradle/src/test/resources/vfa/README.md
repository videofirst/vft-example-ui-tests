## VFA - VIDEO FIRST AUTOMATION 

### What is it?

VFA is a proposed syntax, very similar to Cucumber BDD style but with 
actions embedded right inside the file i.e.

    Feature: Search for a film in various different ways        : 93
    
      This feature will search the IMDB website for various films.
    
      Scenario: Search for film "The Green Mile"                : 3992
    
        Given a user is at the home page                        : open ${ui}
         When the user types "The Green Mile" into search box   : type name=q "The Green Mile"
          And the user clicks the search icon                   : click id=suggestion-search-button
         Then I expect to see the results page                  : text_contains .findHeader "Results for"
                                                                : ui_text_contains css=.findSearchTerm "The Green Mile"
          And the top result only contains "The Green Mile"     : exists "xpath=//td[@class='result_text']/a[text() = 'The Green Mile']"

It extends cucumber BDD with one or more actions on then right hand side after a colon `:` character.


 

### What Problem Does it Solve?

* Cucumber style BDD is very nice for showing what we're trying to achieve at high level.
* However, 