package io.videofirst.google.steps;

import io.videofirst.google.actions.GoogleActions;
import io.videofirst.vfa.Step;
import io.videofirst.vfa.Steps;
import io.videofirst.vfa.web.actions.WebActions;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GoogleSteps extends Steps<GoogleSteps> {

    @Inject
    private GoogleActions google;

    @Inject
    private WebActions web;

    @Step
    public GoogleSteps I_am_at_the_homepage() {
        google.open_homepage();
        return this;
    }

    @Step("I search for [ $ ] in the search box")
    public GoogleSteps I_search_for_$_in_the_search_box(String input) {
        web.type("name=q", input);
        and("I click the search button");
        web.click(".search-button");
        return this;
    }

    @Step
    public GoogleSteps I_expect_to_see_results_with_$(String input) {
        web.text_contains(".search-list", input);
        return this;
    }

    @Step(text = "I expect to see results with [ $ ] ")
    public GoogleSteps I_expect_to_see_results_with_$_formatted(String input) {
        web.text_contains(".search-list", input);
        return this;
    }

    @Step
    public GoogleSteps I_expect_to_see_$_and_$$_show_first_dollar(String input, int value) {
        web.text_contains(".search-list", input);
        return this;
    }

    @Step("I expect to see $ and $$")
    public GoogleSteps I_expect_to_see_$_and_$$_show_first_dollar_formatted(String input, int value) {
        web.text_contains(".search-list", input);
        return this;
    }

}