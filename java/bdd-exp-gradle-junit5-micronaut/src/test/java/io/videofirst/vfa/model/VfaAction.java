package io.videofirst.vfa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.aop.MethodInvocationContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VfaAction {

    private String className;  // should we refactor to an object? e.g. ActionClass ???

    private String methodName;

    private String alias;

    private LinkedHashMap<String, Object> params;

    private VfaTime time;

    private List<String> screenshots;

    @ToString.Exclude
    private List<VfaAction> actions; // children actions

    // Parent / context objects

    @JsonIgnore
    private VfaStep step; // link to parent scenario

    @JsonIgnore
    @ToString.Exclude // TODO exclude from JSON as well
    private VfaAction parent; // link to parent action (if applicable)

    @JsonIgnore
    @ToString.Exclude
    private MethodInvocationContext<Object, Object> methodContext;  // raw method context

    // Methods

    public int countParents() {
        int numOfParents = 0;  // TODO - Java8 stream might be nicer
        VfaAction curAction = this;
        while (curAction.getParent() != null) {
            numOfParents++;
            curAction = curAction.getParent();
        }
        return numOfParents;
    }

    public void addAction(VfaAction action, VfaStep step) {
        action.setParent(this);  // link to this
        action.setStep(step); // link to step
        if (this.actions == null) {
            this.actions = new ArrayList<>();
        }
        this.actions.add(action);
    }

    public void addScreenshot(String screenshot) {
        if (this.screenshots == null) {
            this.screenshots = new ArrayList<>();
            this.screenshots.add(screenshot);
        }
        if (this.step != null && this.step.getScenario() != null) {
            VfaScenario scenario = this.step.getScenario();
            scenario.addScreenshot(screenshot);
        }
    }
}