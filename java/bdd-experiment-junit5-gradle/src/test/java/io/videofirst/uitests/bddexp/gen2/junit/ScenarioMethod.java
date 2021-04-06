package io.videofirst.uitests.bddexp.gen2.junit;

import io.videofirst.uitests.bddexp.vfa.VfaScenario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bob Marks
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScenarioMethod {

    private String methodName;
    private VfaScenario vfaScenario;

}
