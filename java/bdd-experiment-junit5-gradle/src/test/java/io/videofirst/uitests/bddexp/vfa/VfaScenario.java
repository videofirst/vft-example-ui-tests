package io.videofirst.uitests.bddexp.vfa;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VFA Scenario.
 *
 * @author Bob Marks
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VfaScenario {

    private long id;
    private String name;
    private List<VfaStep> steps;

}
