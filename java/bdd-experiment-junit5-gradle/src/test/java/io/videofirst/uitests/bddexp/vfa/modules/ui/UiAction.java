package io.videofirst.uitests.bddexp.vfa.modules.ui;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.videofirst.uitests.bddexp.vfa.common.VfaAction;
import org.openqa.selenium.By;

/**
 * Abstract VFA action class.
 *
 * @author Bob Marks
 */
public abstract class UiAction implements VfaAction {

    protected String target;
    protected String value;

    protected void initTarget(String[] args) {
        if (args.length < 1) {
            // todo
        }
        this.target = args[0];
    }

    protected void initTargetValue(String[] args) {
        if (args.length < 2) {
            // todo
        }
        this.target = args[0];
        this.value = args[1];
    }

    protected static SelenideElement elm(String target) {
        return $(by(target));
    }

    protected static ElementsCollection elms(String target) {
        return $$(by(target));
    }

    protected static By by(String target) {
        if (target.startsWith("id=")) {
            return By.id(removePrefix("id=", target));
        } else if (target.startsWith("xpath=")) {
            return By.xpath(removePrefix("xpath=", target));
        } else if (target.startsWith("name=")) {
            return By.name(removePrefix("name=", target));
        } else if (target.startsWith("css=")) {
            return By.xpath(removePrefix("css", target));
        } else {
            // Assume that it's CSS ("css=" is an optional prefix)
            return By.cssSelector(target);
        }
    }

    // Private methods

    private static String removePrefix(String prefix, String target) {
        return target.replaceFirst("^" + prefix, "");
    }

}
