package io.videofirst.vfa.model;

import io.videofirst.vfa.enums.StepType;
import java.util.ArrayList;
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
public class VfaScenario {

    private Long id;

    private String text;

    private String description;

    private String methodName;

    private List<VfaStep> steps;

    private StepType stepType;  // used with e.g. given().I_am_at_the_homepage(); // FIXME - move to service layer ????

    private VfaTime time;

    private List<String> screenshots; // all screenshots (currently Strings but might change in future).

    @ToString.Exclude
    private VfaFeature feature; // link to parent feature

    public void addStep(VfaStep step) {
        step.setScenario(this); // link to parent
        if (this.steps == null) {
            this.steps = new ArrayList<>();
        }
        this.steps.add(step);
    }

    public void addScreenshot(String screenshot) {
        if (this.screenshots == null) {
            this.screenshots = (new ArrayList<>());
            this.screenshots.add(screenshot);
        }
    }
}