package io.videofirst.uitests.bddexp.vfa;

import static com.diogonunes.jcolor.Ansi.colorize;

import io.videofirst.uitests.bddexp.vfa.common.VfaAction;
import io.videofirst.uitests.bddexp.vfa.common.VfaResult;
import io.videofirst.uitests.bddexp.vfa.util.CliColour;
import io.videofirst.uitests.bddexp.vfa.util.VfaUtil;

/**
 * Key class which executes VFA automation.
 *
 * @author Bob Marks
 */
public class VfaTestRunner {

    public void executeAll(VfaFeature feature) {

        logFeature(feature);

        for (VfaScenario scenario : feature.getScenarios()) {
            executeScenario(scenario);
        }
    }

    // Feature Methods

    public void logFeature(VfaFeature feature) { // FIXME MOVE
        print("Feature: ", CliColour.feature);
        println(feature.getName(), CliColour.strong);
        println();

        if (feature.getDescription() != null) {
            println("  " + feature.getDescription().trim(), CliColour.description);
            println();
        }
    }

    // Scenario methods

    public void logScenario(VfaScenario scenario) {  // FIXME MOVE
        print("  Scenario: ", CliColour.scenario);
        println(scenario.getName(), CliColour.strong);
        println();
    }

    public void executeScenario(VfaScenario scenario) {
        logScenario(scenario);

        for (VfaStep step : scenario.getSteps()) {
            executeStep(scenario, step);
        }
    }

    // Scenario methods

    public void executeStep(VfaScenario scenario, VfaStep step) {
        print(VfaUtil.padLeft(step.getType().getName(), 9) + " ",
            CliColour.step); // TODO improve
        print(step.getName());

        print(VfaUtil.pad(60 - step.getName().length())); // TODO improve

        if (step.getActions() == null) {
            return;
        }

        for (int i = 0; i < step.getActions().size(); i++) {
            VfaAction vfaAction = step.getActions().get(i);
            if (i > 0) { // new line
                print(VfaUtil.pad(10 + 60)); // TODO improve
            }
            executeAction(vfaAction);
        }
    }

    public void executeAction(VfaAction action) {
        print(": ", CliColour.actionSquare);

        print(action.getCommand(), CliColour.actionCommand);

        VfaResult result = action.execute();
        println(" " + result.getArguments(), CliColour.actionArguments);
    }

    // Print methods

    private void print(String input, CliColour cliColour) {
        System.out.print(colorize(input, cliColour.getAttribute()));
        System.out.flush();
    }

    private void print(String input) {
        print(input, CliColour.normal);
    }

    private void println(String input, CliColour cliColour) {
        print(input, cliColour);
        System.out.println();
    }

    private void println(String input) {
        println(input, CliColour.normal);
    }

    private void println() {
        System.out.println();
    }

}
