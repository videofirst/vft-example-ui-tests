package io.videofirst.vfa.properties;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Context;
import lombok.Getter;

/**
 * Main / top level configuration class.
 */
@Getter
@ConfigurationProperties("vfa")
@Context
public class VfaMainConfigProps {

    private boolean strictMode;

}