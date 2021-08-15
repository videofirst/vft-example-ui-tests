package io.videofirst.vfa;

import io.micronaut.context.annotation.Context;
import io.videofirst.vfa.enums.StepType;
import io.videofirst.vfa.model.VfaStep;
import io.videofirst.vfa.service.VfaService;
import javax.inject.Inject;

/**
 * Collection of useful methods which can be called statically.
 * <p>
 */
@Context // load immediately
public class Vfa {

    private static VfaService vfaService; // can be accessed statically

    @Inject
    public Vfa(VfaService vfaService) {
        this.vfaService = vfaService;
    }

    // Given

    public static void given() {
        step(StepType.given);
    }

    public static void given(String text) {
        step(StepType.given, text);
    }

    // When

    public static void when() {
        step(StepType.when);
    }

    public static void when(String text) {
        step(StepType.when, text);
    }

    // Then

    public static void then() {
        step(StepType.then);
    }

    public static void then(String text) {
        step(StepType.then, text);
    }

    // And

    public static void and() {
        step(StepType.and);
    }

    public static void and(String text) {
        step(StepType.and, text);
    }

    // But

    public static void but() {
        step(StepType.but);
    }

    public static void but(String text) {
        step(StepType.but, text);
    }

    // Private methods

    private static void step(StepType type) {
        vfaService.setStepType(type);
    }

    private static void step(StepType type, String text) {
        VfaStep step = VfaStep.builder()
            .type(type)
            .text(text)
            .build();
        vfaService.before(step);
    }

}