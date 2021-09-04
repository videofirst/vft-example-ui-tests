package io.videofirst.vfa.properties;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Context;
import io.videofirst.vfa.enums.VfaMediaCapture;
import lombok.Data;

/**
 * Configuration associated with media (screenshots / video etc).
 */
@Data
@ConfigurationProperties("vfa.media")
@Context
public class VfaMediaProperties {

    // Injected config

    private VfaMediaCapture capture;

}