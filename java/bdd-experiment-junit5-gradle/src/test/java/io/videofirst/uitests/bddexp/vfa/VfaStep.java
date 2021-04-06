package io.videofirst.uitests.bddexp.vfa;

import static java.util.Arrays.asList;

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
    private String name;
    private List<VfaAction> actions;

    public static VfaStep step(VfaStepType type, String name, VfaAction... actions) {
        List<VfaAction> list = actions != null ? asList(actions) : null;
        return VfaStep.builder().type(type).name(name).actions(list).build();
    }

}
