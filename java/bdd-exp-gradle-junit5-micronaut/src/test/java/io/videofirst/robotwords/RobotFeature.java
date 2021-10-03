package io.videofirst.robotwords;

import static io.videofirst.vfa.Vfa.and;
import static io.videofirst.vfa.Vfa.given;
import static io.videofirst.vfa.Vfa.then;
import static io.videofirst.vfa.Vfa.when;

import io.videofirst.vfa.Feature;
import io.videofirst.vfa.Scenario;
import io.videofirst.vfa.web.actions.WebActions;
import javax.inject.Inject;

/**
 * Robot feature.
 *
 * @author Bob Marks
 */
@Feature
public class RobotFeature {

    @Inject
    private WebActions web;

    @Inject
    private Robot robot;

    @Scenario(text = "Add two words (v1)")
    public void add_two_words_version_one() {
        given("I am at the homepage");
        web.open("https://robotspelling.com/");

        when("I add the word 'Belfast'");
        web.type("#wordbox", "Belfast")
            .click("#actionbtn");
        and("I add the word 'Robot'");
        web.type("#wordbox", "Robot")
            .click("#actionbtn");

        then("I expect to see the two words in the list");
    }

    @Scenario(text = "Add two words (v2)")
    public void add_two_words_version_two() {
        robot.given().I_at_the_homepage()

            .when().I_add_the_word_$("Belfast")
            .and().I_add_the_word_$("Robot")

            .then().I_expect_to_see_the_words_in_a_list("Belfast", "Robot");
    }

}
