package io.videofirst.uitests.bddexp.vfa.modules.ui.actions;

import static com.codeborne.selenide.CollectionCondition.allMatch;

import io.videofirst.uitests.bddexp.vfa.common.VfaResult;
import io.videofirst.uitests.bddexp.vfa.modules.ui.UiAction;

/**
 * UI assert value of element.
 *
 * @author Bob Marks
 */
public class UiAssertList extends UiAction {

    private Match match;

    /**
     * Create a text_contains from a target and value.
     */
    public static UiAssertList assert_list(String target, Match match, String value) {
        UiAssertList assertList = new UiAssertList();
        assertList.target = target;
        assertList.match = match;
        assertList.value = value;
        return assertList;
    }

    @Override
    public UiAssertList init(String... args) {
        if (args.length < 3) {
            // todo
        }
        this.target = args[0];
        this.match = Match.valueOf(args[1]);
        this.value = args[2];
        return this;
    }

    @Override
    public VfaResult execute() {
        if (match == Match.text_contains) {
            // ensure all items in list contain this text
            elms(target).shouldHave(allMatch(target, elm -> elm.getText().contains(value)));
        } else if (match == Match.value_contains) {
            elms(target).shouldHave(allMatch(target, elm -> elm.getAttribute("value").contains(value)));
        }
        return new VfaResult(quote(target) + " " + match + " " + quote(value)); // FIXME - DOES FEEL RIGHT HERE
    }

    @Override
    public String getCommand() {
        return "assert_list";
    }

    public static enum Match {

        value_contains, text_contains

    }

}
