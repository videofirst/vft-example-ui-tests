package io.videofirst.vfa.model;

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

    //private List<ScenarioModel> scenario; // do we need this ??? DELETE IF NOT THE CASE

}