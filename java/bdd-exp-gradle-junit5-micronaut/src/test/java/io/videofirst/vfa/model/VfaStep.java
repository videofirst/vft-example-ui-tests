package io.videofirst.vfa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class VfaStep {

    private StepType type;
    private String text;

    private int totalActions;

    private VfaTime time;

    @ToString.Exclude
    private List<VfaAction> actions;

    // Parent References

    @JsonIgnore
    @ToString.Exclude
    private VfaScenario scenario; // link to parent scenario

    // Methods

    public void addAction(VfaAction action) {
        action.setStep(this); // link to parent
        if (this.actions == null) {
            this.actions = new ArrayList<>();
        }
        this.actions.add(action);
    }

}