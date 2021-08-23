package io.videofirst.vfa.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VfaFeature {

    private Long id;

    private String text;

    private String description;

    private String className;

    private VfaTime time;

    private List<VfaScenario> scenarios = new ArrayList<>(); // do we need this ??? DELETE IF NOT THE CASE

}