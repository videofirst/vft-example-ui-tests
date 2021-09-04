package io.videofirst.robotwords;

import io.videofirst.vfa.Action;
import io.videofirst.vfa.Step;
import io.videofirst.vfa.Steps;
import io.videofirst.vfa.web.actions.WebActions;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Bob Marks
 */
@Singleton
public class Robot extends Steps<Robot> {

    @Inject
    private WebActions web;

    @Step
    public Robot I_at_the_homepage() {
        web.open("https://robotspelling.com/");
        return this;
    }

    @Step(text = "I add the word \"$\"")
    public Robot I_add_the_word_$(String word) {
        web.type("#wordbox", word);
        web.click("#actionbtn");
        return this;
    }

    @Step
    public Robot I_expect_to_see_the_words_in_a_list(String... words) {
        do_something();
        return this;
    }

    @Action
    public Robot do_something() {
        return this;
    }

}
