package io.videofirst.google.features;

import io.videofirst.google.Google;
import io.videofirst.vfa.Feature;
import io.videofirst.vfa.Scenario;
import javax.inject.Inject;

@Feature
public class BasicSearchFeature {

    @Inject
    private Google google;

    @Scenario
    public void search_for_single_word_Belfast() {
        google.given_I_am_at_the_homepage();

        google.when_I_search_for_$_in_the_search_box("Belfast");

        google.then_I_expect_to_see_results_$$_here("Belfast");
    }

    @Scenario(text = "Search for two words 'Best City'")
    public void search_for_two_words_Best_City() {
        google.given().I_am_at_the_homepage();

        google.when_I_search_for_$_in_the_search_box("Best City");

        google.then_I_expect_to_see_results_$$_here("Best City");
    }

}