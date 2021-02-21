package io.videofirst.uitests.bddexp.vfa;

import io.videofirst.uitests.bddexp.vfa.common.VfaAction;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VFA step.
 *
 * @author Bob Marks
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VfaStep {

    private VfaStepType type;
    private String label;
    private List<VfaAction> actions;

}
