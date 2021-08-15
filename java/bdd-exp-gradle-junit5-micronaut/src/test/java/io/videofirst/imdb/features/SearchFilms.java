package io.videofirst.imdb.features;

import static io.videofirst.vfa.Vfa.and;
import static io.videofirst.vfa.Vfa.given;
import static io.videofirst.vfa.Vfa.then;
import static io.videofirst.vfa.Vfa.when;

import io.videofirst.imdb.Imdb;
import io.videofirst.vfa.Feature;
import io.videofirst.vfa.Scenario;
import io.videofirst.vfa.actions.ApiActions;
import io.videofirst.vfa.actions.WebActions;
import javax.inject.Inject;

@Feature(id = 11, description = "Search for films in a variety of different ways")
public class SearchFilms {

    @Inject
    private Imdb imdb;

    @Inject
    private WebActions web;

    @Scenario(id = 23)
    public void search_for_film_The_Green_Mile() {
        imdb.given_a_user_is_at_the_homepage();

        when("the user types the \"The Green Mile\" into search box");
        web.type("id=suggestion-search", "The Green Mile");

        and("the user clicks the search icon");
        web.click("#suggestion-search-button");

        then("I expect the see the results page");
        web.text_contains(".findHeader", "Results for");
        web.text_contains(".findSearchTerm", "The Green Mile");

        and("the top result only contains \"The Green Mile\"");
        web.exists("xpath=.//td[@class='result_text']/a[text() = 'The Green Mile']");
    }

    @Scenario
    public void search_for_film_by_country() {
        imdb.given_a_user_is_at_the_homepage();

        when("a user selects country Northern Ireland");
        web.text_contains(".country-x", ".country");

        imdb.then_I_expect_to_see_the_results_for_film_$("The Green Mile");
    }

    @Scenario
    public void search_for_film_by_city() {
        imdb.given_a_user_is_at_the_homepage();

        when("a user selects city Belfast");
        web.text_contains(".country-x", ".country");

        imdb.then_I_expect_to_see_the_results_for_film_$("The Green Mile");
    }

    @Scenario(id = 22)
    public void search_for_basic_film() {
        given("a user is at the homepage");
        web.open("https://www.imdb.com");
    }

    @Inject
    private ApiActions api;

//    @Scenario
//    public void api_test() {
//        api.url("https://api.bla.com")
//                .header("Content-type", "application/json")
//                .body("{'name':'bla'}")
//                .get();
//
//        imdb.then_I_expect_to_see_the_results_for_film_$("The Green Mile");
//
//        web.advanced_thing("xpath bla de bla");
//    }

}