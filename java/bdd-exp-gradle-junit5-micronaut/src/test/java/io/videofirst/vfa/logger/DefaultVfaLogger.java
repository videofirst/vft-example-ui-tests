package io.videofirst.vfa.logger;

import static com.diogonunes.jcolor.Ansi.colorize;
import static io.videofirst.vfa.util.VfaUtils.repeat;

import com.diogonunes.jcolor.Attribute;
import io.videofirst.vfa.enums.StepType;
import io.videofirst.vfa.model.VfaAction;
import io.videofirst.vfa.model.VfaFeature;
import io.videofirst.vfa.model.VfaScenario;
import io.videofirst.vfa.model.VfaStep;
import io.videofirst.vfa.properties.VfaLoggerProperties;
import io.videofirst.vfa.properties.model.VfaTheme;
import io.videofirst.vfa.util.VfaUtils;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * VFA Logger.
 * <p>
 * This class was built with extensibility in mind - all these methods can be extended.
 * <p>
 */
@Singleton
public class DefaultVfaLogger implements VfaLogger, VfaThemeColours {

    private static final int NONE = -1; // TODO move to a constants class ???

    // Text constants

    private static final String TEXT_FEATURE = "Feature";
    private static final String TEXT_SCENARIO = "Scenario";
    private static final String TEXT_SPACE = " ";
    private static final String TEXT_COLON = ": ";
    private static final String TEXT_DOT = ".";
    private static final String TEXT_BRACKET_OPEN = "(";
    private static final String TEXT_BRACKET_CLOSE = ")";
    private static final String TEXT_METHOD_COMMA = ", ";

    @Inject
    private VfaLoggerProperties loggerConfig;

    // Other fields

    private VfaTheme theme;
    private String indentSpaces;
    private StringBuilder line = new StringBuilder();

    @PostConstruct
    public void init() {
        this.theme = loggerConfig.getCurrentTheme();
        this.indentSpaces = repeat(TEXT_SPACE, loggerConfig.getIndentChars());
    }

    @Override
    public void before(VfaFeature feature) {
        printFeatureTextAndId(feature);
        printFeatureDescription(feature);
    }

    @Override
    public void before(VfaScenario scenario) {
        printScenarioTextAndId(scenario);
    }

    @Override
    public void before(VfaStep step) {
        printStepTypeAndText(step);
    }

    @Override
    public void before(VfaAction action) {

        if (isLogAction(action)) {
            printActionAlias(action);
            printActionName(action);
            printActionParameters(action);
        }
    }

    private boolean isLogAction(VfaAction action) {
        int actionParentAcount = action.countParents();
        int actionDepth = loggerConfig.getActionDepth();
        return actionDepth > actionParentAcount || actionDepth == NONE;
    }

    // Protected methods

    protected void printRightColon() {
        int numberOfSpaces = loggerConfig.getRightColumnChars() - line.length();
        print(repeat(TEXT_SPACE, numberOfSpaces));
        print(TEXT_COLON, COLOUR_RIGHT_COLON);
    }

    protected void printFeatureTextAndId(VfaFeature feature) {
        print(TEXT_FEATURE, COLOUR_FEATURE_LABEL);
        print(TEXT_COLON, COLOUR_FEATURE_COLON);
        print(feature.getText(), COLOUR_FEATURE_TEXT);

        if (feature.getId() != NONE) {
            printRightColon();
            print(String.valueOf(feature.getId()), COLOUR_FEATURE_ID);
        }
        println();
    }

    protected void printFeatureDescription(VfaFeature feature) {
        if (feature.getDescription() != null && !feature.getDescription().isEmpty()) {
            println();
            print(indentSpaces);
            println(feature.getDescription().trim(), COLOUR_FEATURE_DESCRIPTION);
        }
    }

    protected void printScenarioTextAndId(VfaScenario scenario) {
        resetLine(); // reset from a potential previous scenario
        print(indentSpaces);
        print(TEXT_SCENARIO, COLOUR_SCENARIO_LABEL);
        print(TEXT_COLON, COLOUR_SCENARIO_COLON);
        print(scenario.getText(), COLOUR_SCENARIO_TEXT);
        if (scenario.getId() != NONE) {
            printRightColon();
            print(String.valueOf(scenario.getId()), COLOUR_SCENARIO_ID);
        }
        println();
    }

    protected void printStepTypeAndText(VfaStep step) {
        println();

        // Step label e.g. "Given"
        StepType stepType = step.getType();
        print(indentSpaces + indentSpaces + stepType.label(), COLOUR_STEP_LABEL);

        // Step text e.g. "A user is at the homepage"
        print(TEXT_SPACE + step.getText(), COLOUR_STEP_TEXT);
    }

    protected void printActionAlias(VfaAction action) {
        boolean isFirstAction = action.getStep().getTotalActions() == 1;
        if (!isFirstAction) {
            println();
        }
        printActionColon(action);

        // Print alias (unless configured to be ignored) e.g. "web."
        String alias = action.getAlias();
        if (!isAliasIgnored(alias)) {
            print(alias, COLOUR_ACTION_ALIAS);
            print(TEXT_DOT, COLOUR_ACTION_DOT);
        }
    }

    protected void printActionName(VfaAction action) {
        String methodName = action.getMethodName();
        print(methodName, COLOUR_ACTION_METHOD);
    }

    protected void printActionColon(VfaAction action) {
        int numberOfSpaces = loggerConfig.getRightColumnChars() - line.length();
        numberOfSpaces += action.countParents() * loggerConfig.getIndentChars();  // indent for each action level
        print(repeat(TEXT_SPACE, numberOfSpaces));
        print(TEXT_COLON, COLOUR_RIGHT_COLON);
    }

    protected void printActionParameters(VfaAction action) {
        // Print parameter
        print(TEXT_SPACE);
        print(TEXT_BRACKET_OPEN, COLOUR_ACTION_BRACKETS);
        int index = 0;
        for (Map.Entry<String, Object> param : action.getParams().entrySet()) {
            if (index > 0) {
                print(TEXT_METHOD_COMMA, COLOUR_ACTION_COMMA);
            }

            Object paramValue = param.getValue();
            if (paramValue instanceof String) {
                String quotedParamValue = VfaUtils.quote((String) paramValue);
                print(quotedParamValue, COLOUR_ACTION_STRING_PARAM);
            } else {
                print(String.valueOf(paramValue), COLOUR_ACTION_OTHER_PARAM);
            }
            index++;
        }
        print(TEXT_BRACKET_CLOSE, COLOUR_ACTION_BRACKETS);
    }

    // Low level print methods

    protected void print(String input) {
        print(input, null);
    }

    protected void print(String input, String themeColour) {
        line.append(input);
        if (theme.isUseColours() && themeColour != null) {
            Attribute attribute = theme.getColourAttribute(themeColour);
            System.out.print(colorize(input, attribute));
        } else {
            System.out.print(input);
        }
        System.out.flush();
    }

    protected void println() {
        System.out.println();
        resetLine();
    }

    protected void println(String input, String themeColour) {
        print(input, themeColour);
        println();
        resetLine();
    }

    protected void resetLine() {
        line.setLength(0);
    }

    // Private methods

    private boolean isAliasIgnored(String alias) {
        if (loggerConfig.getIgnoreAliases() == null) {
            return false;
        }
        return loggerConfig.getIgnoreAliases().stream()
            .anyMatch(p -> p != null && p.trim().equalsIgnoreCase(alias));
    }

}