package io.videofirst.uitests.bddexp.gen2.tests;

import io.videofirst.uitests.bddexp.gen2.junit.Feature;
import io.videofirst.uitests.bddexp.gen2.junit.ImdbModule;
import io.videofirst.uitests.bddexp.gen2.junit.Scenario;
import io.videofirst.uitests.bddexp.gen2.junit.UiModule;
import io.videofirst.uitests.bddexp.gen2.junit.VfaSteps;
import org.junit.jupiter.api.Test;

/**
 * @author Bob Marks
 */
@Feature(id = 23, name = "Search for Films", description = "Search for a film in various different ways")
public class SearchFilmsTest2 extends VfaSteps {

    private UiModule ui = new UiModule(); // low level module
    private ImdbModule imdb = new ImdbModule(); // high level module

    // Feature: Search for a film in various different ways
    //
    //  This feature will search the IMDB website for various films.
    //
    //  Scenario: Search for film "The Green Mile"
    //
    //    Given a user is at the home page                        : open https://www.imdb.com
    //     When the user types "The Green Mile" into search box   : type id=suggestion-search "The Green Mile"
    //      And the user clicks the search icon                   : click #suggestion-search-button
    //     Then I expect to see the results page                  : text_contains .findHeader "Results for"
    //                                                            : text_contains .findSearchTerm "The Green Mile"
    //      And the top result only contains "The Green Mile"     : exists xpath=.//td[@class='result_text']/a[text() = 'The Green Mile']

    @Test
    @Scenario(id = 53, name = "Search for film \"The Green Mile\"") //@DisplayName("Search for film \"The Green Mile\"")
    public void search_for_film_The_Green_Mile() {
        given("a user is at the home page", ui.open("https://www.imdb.com"));

        when("the user types \"The Green Mile\" into search box", ui.type("id=suggestion-search", "The Green Mile"));
        and("the user clicks the search icon", ui.click("#suggestion-search-button"));

        then("I expect to see the results page", ui.text_contains(".findHeader", "Results for"),
            ui.text_contains(".findSearchTerm", "The Green Mile"));
        and("the top results only contains \"The Green Mile\"",
            ui.exists("xpath=.//td[@class='result_text']/a[text() = 'The Green Mile']"));
    }

    @Test
    @Scenario(id = 31)
    void search_for_film_by_country() {
        given("a user is at the home page");

    }

    //   Scenario: Search for film "Good Will Hunting"     : 3253
    //
    //    Given a user is at the home page                 : imdb.homepage.open   # this is a comment
    //     When the user searches for "Good Will Hunting"  : imdb.search "Good Will Hunting"
    //     Then I expect to see the results page           : imdb.results_page_is_visible
    //      And the top result contains "The Green Mile"   : imdb.results_page_contains_only "Good Will Hunting"

    @Scenario(id = 23, name = "Search for film \"Good Will Hunting\"")
    public void search_for_film_good_will_hunting() {
        String film = "Good Will Hunting";

        given("a user is at the home page", imdb.open_homepage()); //  "imdb open_homepage" TODO

        when("the user searches for \"" + film + "\"", imdb.search(film));

        then("I expect to see the results page", imdb.results_page_is_visible(film));
        and("the top results contains \"" + film + "\"", imdb.results_page_contains_only(film));
    }

//    @Scenario(id = 25, name = "Search for films")  - parameterised tests
//    @ParameterizedTest
//    @ValueSource(strings = {"Star Wars", "The Empire Strikes Back"})
//    public void search_for_films(String film) {
//
//        given("a user is at the home page", imdb.open_homepage());
//
//        when("the user searches for \"" + film + "\"", imdb.search(film));
//
//        then("I expect to see the results page", imdb.results_page_is_visible());
//        and("the top results contains \"" + film + "\"",
//            imdb.results_page_contains_only("Good Will Hunting"));
//    }

}
