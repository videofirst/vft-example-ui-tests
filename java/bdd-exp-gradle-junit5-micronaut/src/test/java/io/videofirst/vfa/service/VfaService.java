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
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.inject.Inject;

/**
 * Main VfaService - uses ThreadLocal approach so can be accessed easily from anywhere.
 */
@Context // load immediately
public class VfaService {

    // FIXME Should this be combined in a single object? e.g. meta, current, execution etc,
    private static ThreadLocal<VfaFeature> currentFeature = new ThreadLocal<>();
    private static ThreadLocal<VfaScenario> currentScenario = new ThreadLocal<>();
    private static ThreadLocal<Stack<VfaAction>> currentActionStack = new ThreadLocal<>();

    private VfaLogger logger;

    @Inject
    public VfaService(VfaLogger logger) {
        this.logger = logger;
    }

    // Before methods

    public void before(VfaFeature feature) {
        currentFeature.set(feature);
        feature.setTime(VfaTime.start());
        logger.before(feature);
    }

    public void before(VfaScenario scenario) {
        VfaFeature currentFeature = getCurrentFeature();
        scenario.setFeature(currentFeature); // link to parent
        currentScenario.set(scenario);

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

        // Update current step
        step.setScenario(currentScenario); // link to parent
        if (currentScenario.getSteps() == null) {
            currentScenario.setSteps(new ArrayList<>());
        }
        currentScenario.getSteps().add(step);
        currentScenario.setStepType(null); // reset back again

        step.setTime(VfaTime.start());

        logger.before(step);
    }

    public void before(VfaAction action) {
        VfaStep currentStep = getCurrentStep();
        currentStep.setTotalActions(currentStep.getTotalActions() + 1);
        action.setStep(currentStep); // link to step

        // Check the current action and if it has finished or not
        Stack<VfaAction> actionStack = currentActionStack.get();
        if (actionStack.isEmpty()) {
            // no parents
            if (currentStep.getActions() == null) {
                currentStep.setActions(new ArrayList<>());
            }

            currentStep.getActions().add(action);
        } else {
            // has parent action
            VfaAction parent = actionStack.peek();
            if (parent.getActions() == null) {
                parent.setActions(new ArrayList<>());
            }
            parent.getActions().add(action);
            action.setParent(parent);
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

}