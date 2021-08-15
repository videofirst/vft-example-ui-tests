package io.videofirst.google.features;

import io.videofirst.google.steps.GoogleSteps;
import io.videofirst.vfa.Feature;
import io.videofirst.vfa.Scenario;
import javax.inject.Inject;

@Feature(id = 12, description = "A variety of advanced search scenarios")
public class AdvancedSearchFeature {

    @Inject
    private GoogleSteps google;

    @Scenario
    public void search_for_single_word_Belfast() {
        google.given().I_am_at_the_homepage();

        google.when().I_search_for_$_in_the_search_box("Belfast");

        google.then().I_expect_to_see_results_with_$("Belfast");
    }

    @Scenario(id = 33, text = "Search for two words 'Best City'")
    public void search_for_two_words_Best_City() {
        google.given().I_am_at_the_homepage()

            .when().I_search_for_$_in_the_search_box("Best City")

            .then().I_expect_to_see_results_with_$("Best City");
    }

}