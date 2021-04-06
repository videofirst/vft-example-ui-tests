# Generation Experiment 1

Single Test class e.g. to convert the follow `.vfa` snippet ...

    Feature: Search for Films
    
     This feature will search the IMDB website for various films.
     
     Scenario: Search for film "The Green Mile"
     
       Given a user is at the home page                        : open https://www.imdb.com
        When the user types "The Green Mile" into search box   : type id=suggestion-search "The Green Mile"
         And the user clicks the search icon                   : click #suggestion-search-button
        Then I expect to see the results page                  : text_contains .findHeader "Results for"
                                                               : text_contains .findSearchTerm "The Green Mile"
         And the top result only contains "The Green Mile"     : exists xpath=.//td[@class='result_text']/a[text() = 'The Green Mile']
         
... would look like ...

    @Feature(id = 23, name = "Search for Films", description = "This feature will search the IMDB website for various films.")
    public class SearchFilmsTest2 extends VfaSteps {
    
        private UiModule ui = new UiModule(); // low level module
        private ImdbModule imdb = new ImdbModule(); // high level module
    
        @Test
        @Scenario(id = 53, name = "Search for film \"The Green Mile\"")
        public void search_for_film_The_Green_Mile() {
            given("a user is at the home page", ui.open("https://www.imdb.com"));
    
            when("the user types \"The Green Mile\" into search box", ui.type("id=suggestion-search", "The Green Mile"));
            and("the user clicks the search icon", ui.click("#suggestion-search-button"));
    
            then("I expect to see the results page", ui.text_contains(".findHeader", "Results for"),
                ui.text_contains(".findSearchTerm", "The Green Mile"));
            and("the top results only contains \"The Green Mile\"",
                ui.exists("xpath=.//td[@class='result_text']/a[text() = 'The Green Mile']"));
        }

 
* Advantages
  1. Really fluid, very easy to convert from ".vfa" format to Java code.
  2. No repetition required of java method names.
  3. Easy to chain modules together.
  4. Should be easy to generate Java code for easy debugging.
   
* Disadvantages
  1. Less code centric than experiment 1, arguable less re-usable?
  2. More verbose than exp 1.
  

Overall, really happy with this approach.  It enables users to use a "Pure Java" approach to testing
and mix and match with `.vfa` format as well (generated in a similar style to MapStruct).

Ideally if these are totally interchangeable i.e. 

    vfa -> Java (generated) + Java -> vfa (generated).

We have this at the minute, certainly Java to `vfa` format (as this is what get's rendered).     
    