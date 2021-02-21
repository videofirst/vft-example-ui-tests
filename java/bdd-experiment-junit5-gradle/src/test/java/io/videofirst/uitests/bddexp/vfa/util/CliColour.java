package io.videofirst.uitests.bddexp.vfa.util;

import static com.diogonunes.jcolor.Attribute.TEXT_COLOR;

import com.diogonunes.jcolor.Attribute;

/**
 * CLI colour enum.
 *
 * @author Bob Marks
 */
public enum CliColour {

    normal(178, 178, 178),
    strong(238, 238, 238),
    feature(38, 165, 242),
    scenario(0, 150, 240),
    description(138, 138, 138),
    step(0, 113, 178),

    actionSquare(70, 70, 70),
    actionCommand(175, 87, 0),
    actionArguments(138, 138, 138);

    private final Attribute attribute;

    CliColour(int r, int g, int b) {
        this.attribute = TEXT_COLOR(r, g, b);
    }

    public Attribute getAttribute() {
        return attribute;
    }

}
