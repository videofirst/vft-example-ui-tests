package io.videofirst.uitests.bddexp.vfa;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VFA feature.
 *
 * Based closely on https://cucumber.io/docs/gherkin/reference/.
 *
 * @author Bob Marks
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VfaFeature {

    private String label;
    private String description;
    private List<VfaScenario> scenarios;

}
