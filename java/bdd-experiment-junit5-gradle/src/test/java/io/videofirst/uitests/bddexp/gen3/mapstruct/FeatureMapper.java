package io.videofirst.uitests.bddexp.gen3.mapstruct;

import io.videofirst.uitests.bddexp.vfa.VfaFeature;
import org.mapstruct.Mapper;

/**
 * @author Bob Marks
 */
@Mapper
public interface FeatureMapper {

    FeatureDto carToCarDto(VfaFeature car);

}