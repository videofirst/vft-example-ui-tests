package io.videofirst.uitests.bddexp.gen1.tests;

import io.videofirst.uitests.bddexp.gen2.junit.Feature;
import io.videofirst.uitests.bddexp.gen2.junit.Scenario;

/**
 * @author Bob Marks
 */
@Feature(id = 39,
    name = "Search Films",
    description = "This feature will search the IMDB website for various films.")
public class SearchFilmsTest1 extends SearchFilm {

    @Scenario(id = 23, name = "Search for film \"The Green Mile\"")
    public void search_for_film_the_green_mile() {
        given_a_user_is_at_the_home_page();

        when_the_user_types_the_green_mile_into_search_box();
        and_the_user_clicks_the_search_icon();

        then_I_expect_to_see_the_results_page();
        and_the_top_result_only_contains_The_Green_Mile();
    }

}