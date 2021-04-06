package io.videofirst.uitests.bddexp.gen2.tests;

import static io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiAssertList.Match.text_contains;

import io.videofirst.uitests.bddexp.gen2.junit.Feature;
import io.videofirst.uitests.bddexp.gen2.junit.Scenario;
import io.videofirst.uitests.bddexp.gen2.junit.UiModule;
import io.videofirst.uitests.bddexp.gen2.junit.VfaSteps;

/**
 * Search the BBC news website.
 *
 * @author Bob Marks
 */
@Feature(id = 32)
public class BbcNewsSearchTest extends VfaSteps {

    private UiModule ui = new UiModule(); // FIXME, needs injected I think ???

    @Scenario
    public void should_search_for_Belfast() {

        given("I was at the BBC News homepage", ui.open("https://news.bbc.co.uk"));

        when("I type \"Belfast\" into search box", ui.type("#orb-search-q", "Belfast"));
        and("I click the search icon", ui.click(".se-searchbox__submit"));

        then("I expect to see \"Belfast\" in the text box", ui.assert_value("#search-input", "Belfast"));
        and("list items to contain \"Belfast\"",
            ui.assert_list("main ul[role='list'] li a p", text_contains, "Belfast"));
    }

}
