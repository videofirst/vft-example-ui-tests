package io.videofirst.uitests.bddexp.vfa;

/**
 * VFA step type e.g. Given, When, Then, And, But etc
 *
 * @author Bob Marks
 */
public enum VfaStepType {

    given("Given"),
    when("When"),
    then("Then"),
    and("And"),
    but("But"),
    asterisk("*");

    String name;

    VfaStepType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
