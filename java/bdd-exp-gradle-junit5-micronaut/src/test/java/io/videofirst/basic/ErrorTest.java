package io.videofirst.basic;

import static io.videofirst.vfa.Vfa.and;
import static io.videofirst.vfa.Vfa.given;
import static io.videofirst.vfa.Vfa.then;
import static io.videofirst.vfa.Vfa.when;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import io.videofirst.vfa.Feature;
import io.videofirst.vfa.Scenario;
import io.videofirst.vfa.web.actions.WebActions;
import javax.inject.Inject;
import org.junit.jupiter.api.Disabled;

/**
 * https://junit.org/junit5/docs/current/user-guide/#extensions-test-result-processing
 *
 * @author Bob Marks
 */
@Feature
class ErrorTest {

    @Inject
    private ErrorActions errorActions;

    @Inject
    private WebActions webActions;

    @Scenario
    public void actionErrors() {
        given("a user is at the homepage");
        errorActions.error();

        and("there is something else");
        errorActions.success1();

        then("bla bla bla");
        errorActions.success2("input");
    }

    @Scenario
    public void actionFail() {
        given("a user is at the homepage");
        errorActions.success1();

        and("there is something else");
        errorActions.fail();

        then("bla bla bla");
        errorActions.success2("input");
    }

    @Scenario
    public void exception() {
        given("a user is at the homepage");

        if (true) {
            throw new RuntimeException();
        }

        when("A user is bla de bla bla");

        then("there is something there");
        and("asdfasdf");
    }

    @Scenario
    public void user_selecting_various_blas() {
        given("a user is at the homepage");
        fail();

        when("A user is bla de bla bla");

        then("there is something there");
        and("asdfasdf");
    }

    @Scenario
    public void success() {
        given("a user is at the homepage");

        when("A user is bla de bla bla");

        then("there is something there");
        and("asdfasdf");
    }

    @Scenario
    @Disabled("for demonstration purposes")
    public void skipped() {
        given("a user is at the homepage");

        when("A user is bla de bla bla");

        then("there is something there");
        and("asdfasdf");
    }

    @Scenario
    public void aborted() {
        assumeTrue(false);
        given("a user is at the homepage");

        when("A user is bla de bla bla");

        then("there is something there");
        and("asdfasdf");
    }

}
