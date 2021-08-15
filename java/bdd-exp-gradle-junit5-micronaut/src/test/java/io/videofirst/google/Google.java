package io.videofirst.google;

import io.videofirst.vfa.Action;
import io.videofirst.vfa.Step;
import io.videofirst.vfa.Steps;
import io.videofirst.vfa.actions.WebActions;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Google extends Steps<Google> {

    @Inject
    private WebActions web;

    @Step
    public void given_I_am_at_the_homepage() {
        open_homepage();
    }

    @Step
    public void I_am_at_the_homepage() {
        open_homepage();
    }

    @Action
    public Google open_homepage() {
        web.open("https://www.google.com");
        return this;
    }

    @Step
    public Google I_search_for_$_in_the_search_box(String input) {
        //when("I type '" + input + "' into the search box");
        web.type("name=q", input);
        and("I click the search button");
        web.click(".search-button");
        return this;
    }

    @Step
    public Google when_I_search_for_$_in_the_search_box(String input) {
        //when("I type '" + input + "' into the search box");
        web.type("name=q", input);
        and("I click the search button");
        web.click(".search-button");
        return this;
    }

    @Step("I expect to see results [ $ ] here ")
    public Google then_I_expect_to_see_results_$$_here(String input) {
        web.text_contains(".search-list", input);
        return this;
    }

}