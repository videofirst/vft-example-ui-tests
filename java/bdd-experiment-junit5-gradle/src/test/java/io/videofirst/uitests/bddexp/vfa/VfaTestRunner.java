package io.videofirst.uitests.bddexp.vfa;

import static com.diogonunes.jcolor.Ansi.colorize;

import io.videofirst.uitests.bddexp.vfa.common.VfaAction;
import io.videofirst.uitests.bddexp.vfa.common.VfaResult;
import io.videofirst.uitests.bddexp.vfa.util.CliColour;
import io.videofirst.uitests.bddexp.vfa.util.VfaUtil;

/**
 * @author Bob Marks
 */
public class VfaTestRunner {

    public void executeFeature(VfaFeature feature) {

        logFeature(feature);

        for (VfaScenario scenario : feature.getScenarios()) {
            executeScenario(scenario);
        }
    }

    // Feature Methods

    private void logFeature(VfaFeature feature) {
        print("Feature: ", CliColour.feature);
        println(feature.getLabel(), CliColour.strong);
        println();

        if (feature.getDescription() != null) {
            println("  " + feature.getDescription().trim(), CliColour.description);
            println();
        }
    }

    // Scenario methods

    private void executeScenario(VfaScenario scenario) {
        print("  Scenario: ", CliColour.scenario);
        println(scenario.getLabel(), CliColour.strong);
        println();

        for (VfaStep step : scenario.getSteps()) {
            executeStep(scenario, step);
        }
    }

    // Scenario methods

    private void executeStep(VfaScenario scenario, VfaStep step) {
        int longestLabel = longestLabel(scenario);

        print(VfaUtil.padLeft(step.getType().getLabel(), 9) + " ",
            CliColour.step); // TODO improve
        print(step.getLabel());
        print(VfaUtil.pad(longestLabel - step.getLabel().length()) + "   "); // TODO improve

        for (int i = 0; i < step.getActions().size(); i++) {
            VfaAction vfaAction = step.getActions().get(i);
            if (i > 0) {
                print(VfaUtil.pad(longestLabel + 13)); // TODO improve
            }
            executeAction(i, vfaAction);
        }
    }

    private int longestLabel(VfaScenario scenario) {
        int longest = 0;
        for (VfaStep step : scenario.getSteps()) {
            if (step.getLabel().length() > longest) {
                longest = step.getLabel().length();
            }
        }
        return longest;
    }

    private void executeAction(int index, VfaAction action) {
        print("# ", CliColour.actionSquare);

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
