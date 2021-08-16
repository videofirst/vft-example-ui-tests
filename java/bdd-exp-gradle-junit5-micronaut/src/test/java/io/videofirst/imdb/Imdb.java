package io.videofirst.imdb;

import io.micronaut.context.annotation.Value;
import io.videofirst.vfa.Step;
import io.videofirst.vfa.Steps;
import io.videofirst.vfa.actions.AdvancedWebActions;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Imdb extends Steps<Imdb> {

    @Value("${imdb.homepage}")
    private String homepage;

    @Inject
    private AdvancedWebActions web;

    // Steps (Given)

    @Step
    public void given_a_user_is_at_the_homepage() {
        web.open(homepage);
    }

    @Step
    public void a_user_is_at_the_homepage() {
        web.open(homepage);
    }

    // Steps (When)

    @Step
    public void when_a_user_searches_for_$(String input) {
        web.type("id=suggestion-search", "The Green Mile");
        web.click("#suggestion-search-button");
    }

    // Steps (Then)

    public void then_I_expect_to_see_the_results_for_film_$(String film) {
        then("I expect to see the results for film [ " + film + " ]");
        web.text_contains(".findHeader", "Results for");
        web.text_contains(".findSearchTerm", film);
    }

}