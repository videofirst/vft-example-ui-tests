package io.videofirst.vfa.model;

import io.videofirst.vfa.enums.StepType;
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

    @ToString.Exclude
    private VfaScenario scenario; // link to parent scenario

}