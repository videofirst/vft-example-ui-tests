package io.videofirst.uitests.bddexp.vfa.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Result of an action - need better name?
 *
 * @author Bob Marks
 */
@Getter
@AllArgsConstructor
public class VfaResult {

    private String arguments; // command which will be printed out

}
