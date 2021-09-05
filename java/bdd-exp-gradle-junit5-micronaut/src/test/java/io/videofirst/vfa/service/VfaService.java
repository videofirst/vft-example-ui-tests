package io.videofirst.vfa.service;

import io.micronaut.context.annotation.Context;
import io.videofirst.vfa.enums.StepType;
import io.videofirst.vfa.exceptions.VfaException;
import io.videofirst.vfa.logger.VfaLogger;
import io.videofirst.vfa.model.VfaAction;
import io.videofirst.vfa.model.VfaFeature;
import io.videofirst.vfa.model.VfaScenario;
import io.videofirst.vfa.model.VfaStep;
import io.videofirst.vfa.model.VfaTime;
import io.videofirst.vfa.properties.VfaReportsProperties;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;

/**
 * Main VfaService - uses ThreadLocal approach so can be accessed easily from anywhere.
 */
@Context // load immediately
public class VfaService {

    // FIXME Should this be combined in a single object? e.g. meta, current, execution etc,
    private static ThreadLocal<VfaFeature> currentFeature = new ThreadLocal<>();
    private static ThreadLocal<VfaScenario> currentScenario = new ThreadLocal<>();
    private static ThreadLocal<Stack<VfaAction>> currentActionStack = new ThreadLocal<>();

    @Inject
    private VfaLogger logger;

    @Inject
    private VfaReportsProperties reportsProperties;

    // Before methods

    public void before(VfaFeature feature) {
        currentFeature.set(feature);
        feature.setTime(VfaTime.start());
        logger.before(feature);

        initReportsDirectory();
    }

    public void before(VfaScenario scenario) {
        VfaFeature currentFeature = getCurrentFeature();
        currentScenario.set(scenario);
        currentFeature.addScenario(scenario);

        // reset the action stack
        currentActionStack.set(new Stack<>());
        scenario.setTime(VfaTime.start());
        logger.before(scenario);
    }

    public void before(VfaStep step) {
        VfaScenario currentScenario = getCurrentScenario();

        // See if previous step needs to be finished
        List<VfaStep> steps = currentScenario.getSteps();
        if (steps != null && !steps.isEmpty()) {
            // check if last one has finished
            VfaStep previousStep = steps.get(steps.size() - 1);
            after(previousStep);
        }

        currentScenario.addStep(step);
        currentScenario.setStepType(null); // reset back again

        step.setTime(VfaTime.start());

        logger.before(step);
    }

    public void before(VfaAction action) {
        VfaStep currentStep = getCurrentStep();
        currentStep.setTotalActions(currentStep.getTotalActions() + 1);

        // Check the current action and if it has finished or not
        Stack<VfaAction> actionStack = currentActionStack.get();

        boolean noParentAction = actionStack.isEmpty();
        if (noParentAction) {
            currentStep.addAction(action);   // just add action to step
        } else {
            VfaAction parentAction = actionStack.peek(); // other wise add this action to parent action + link to step
            parentAction.addAction(action, currentStep);
        }

        currentActionStack.get().push(action);
        action.setTime(VfaTime.start());

        logger.before(action);
    }

    // After methods

    public void after(VfaAction action) {
        action.setTime(action.getTime().finish());
        currentActionStack.get().pop();
    }

    public void after(VfaStep step) {
        VfaTime time = step.getTime();
        if (time == null) {  // this should never happen - indicates fundamental issue with code
            throw new VfaException("Step has no time set");
        }
        if (!step.getTime().isFinished()) {
            step.setTime(time.finish());
        }
    }

    public void after(VfaScenario scenario) {
        // Check if last step of this scenario needs to be closed
        List<VfaStep> steps = scenario.getSteps();
        if (steps != null || !steps.isEmpty()) {
            // check if last one has finished
            VfaStep previousStep = steps.get(steps.size() - 1);
            after(previousStep);
        }

        if (!scenario.getTime().isFinished()) {
            scenario.setTime(scenario.getTime().finish());
        }
    }

    public void after(VfaFeature feature) {
        if (!feature.getTime().isFinished()) {
            feature.setTime(feature.getTime().finish());
        }
    }

    // Other useful methods

    public void setStepType(StepType stepType) {
        VfaScenario scenario = getCurrentScenario();
        scenario.setStepType(stepType);
    }

    public VfaFeature getCurrentFeature() {
        VfaFeature feature = currentFeature.get();
        if (feature == null) {
            throw new VfaException("No feature set - cannot continue"); // shouldn't happen in theory
        }
        return feature;
    }

    public VfaScenario getCurrentScenario() {
        VfaScenario scenario = currentScenario.get();
        if (scenario == null) {
            throw new VfaException("No scenario set - cannot continue");
        }
        return scenario;
    }

    public VfaStep getCurrentStep() {
        VfaScenario scenario = getCurrentScenario();

        // Ensure at least one step exists, otherwise throw exception
        List<VfaStep> steps = scenario.getSteps();
        if (steps == null || steps.isEmpty()) {
            throw new VfaException("Please add a step before adding an action");
        }

        // Retrieve last step
        VfaStep stepModel = steps.get(steps.size() - 1);
        return stepModel;
    }

    // Private method

    private void initReportsDirectory() {
        File reportsFolder = new File(reportsProperties.getFolder()).getAbsoluteFile();
        try {
            if (!reportsFolder.exists()) {
                reportsFolder.mkdirs();
            }
            FileUtils.cleanDirectory(reportsFolder);
        } catch (IOException e) {
            throw new VfaException("Error deleting reports folder: " + reportsFolder.getAbsolutePath());
        }
    }

}