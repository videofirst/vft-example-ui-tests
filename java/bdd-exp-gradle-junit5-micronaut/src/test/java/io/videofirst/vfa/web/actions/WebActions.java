package io.videofirst.vfa.web.actions;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.micronaut.context.annotation.Context;
import io.videofirst.vfa.Action;
import org.openqa.selenium.By;

/**
 * Move to it's own module.
 */
@Context
public class WebActions extends VfaSelenideActions {

    @Action
    public WebActions open(String url) {
        Selenide.open(url);
        return this;
    }

    @Action
    public WebActions click(String target) {
        elm(target).click();
        return this;
    }

    @Action
    public WebActions type(String target, String value) {
        elm(target).setValue(value);
        return this;
    }

    @Action(isAssert = true)
    public WebActions text_contains(String target, String value) {
        String elmText = elm(target).text();
        assertThat(elmText).contains(value).withFailMessage(
            "Expecting element [ " + target + " ] with text [ " + elmText + " ] to contain [ " + value + " ]");
        return this;
    }

    @Action(isAssert = true)
    public WebActions exists(String target) {
        elm(target).should(Condition.exist);
        return this;
    }

    // Private methods

    private SelenideElement elm(String target) {
        return $(by(target));
    }

    private ElementsCollection elms(String target) {
        return $$(by(target));
    }

    private By by(String target) {
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

    private String removePrefix(String prefix, String target) {
        return target.replaceFirst("^" + prefix, "");
    }

}