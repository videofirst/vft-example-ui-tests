package io.videofirst.vfa.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Immutable time class.
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class VfaTime {

    private long start;
    private Long finish;  // optional

    public static VfaTime start() {
        return VfaTime.builder().start(System.currentTimeMillis()).build();
    }

    public VfaTime finish() {
        return this.toBuilder().finish(System.currentTimeMillis()).build();
    }

    public boolean isFinished() {
        return finish != null;
    }

}