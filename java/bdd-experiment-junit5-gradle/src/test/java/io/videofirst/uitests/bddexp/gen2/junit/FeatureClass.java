package io.videofirst.uitests.bddexp.gen2.junit;

import io.videofirst.uitests.bddexp.vfa.VfaFeature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Maybe FeatureDetail is better?
 *
 * @author Bob Marks
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureClass {

    public VfaFeature vfaFeature;
    private Class featureClass;

    //private Map<String, Object> store = new HashMap<>();

}
