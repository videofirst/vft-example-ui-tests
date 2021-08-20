package io.videofirst.vfa.web.actions;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeborne.selenide.Browser;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Config;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.webdriver.ChromeDriverFactory;
import com.codeborne.selenide.webdriver.MergeableCapabilities;
import io.micronaut.context.annotation.Context;
import io.videofirst.vfa.Action;
import java.io.File;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.service.DriverService;

/**
 * Move to it's own module.
 */
@Context
public class WebActions {

    private String seleniumWebBrowser = "chrome"; // FIXME inject from config

//    @Inject
//    private

    public WebActions() {
        setup();
    }

    // Ref: https://mbbaig.blog/selenide-webdriverfactory/
    private static class MyFactory extends ChromeDriverFactory {

        @Override
        @CheckReturnValue
        @Nonnull
        public MutableCapabilities createCapabilities(Config config, Browser browser, @Nullable Proxy proxy,
            File browserDownloadsFolder) {
            ChromeOptions options = new ChromeOptions();

            // No options then Chrome displays the following: -  (7 log lines)
            //
            //     Starting ChromeDriver 92.0.4515.107 (87a818b10553a07434ea9e2b6dccf3cbe7895134-refs/branch-heads/4515@{#1634}) on port 36449
            //     Only local connections are allowed.
            //     Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.
            //     ChromeDriver was started successfully.
            //     Aug 19, 2021 8:52:06 AM org.openqa.selenium.remote.ProtocolHandshake createSession
            //     INFO: Detected dialect: W3C
            //     )

            // 1) If we set property "webdriver.chrome.silentOutput" then ...    (3 log lines)
            System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
            //
            //     ChromeDriver was started successfully.
            //     Aug 19, 2021 8:54:15 AM org.openqa.selenium.remote.ProtocolHandshake createSession
            //     INFO: Detected dialect: W3C

            // 2) If set set the following log property to off then ... (1 log line)
            Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
            //
            //     ChromeDriver was started successfully.

            // 3) Final log line is done with the `WebDriver create` method.

            // 4) Also, might be useful in future e.g. drive from config
            // options.addArguments("--incognito", "--incognito");

            return new MergeableCapabilities(options, createCommonCapabilities(config, browser, proxy));
        }

        // REF [ https://stackoverflow.com/a/54301361 ]
        // NOTE: this needs requires much more work and should feed into any experiments in future around logging.
        @Override
        @CheckReturnValue
        @Nonnull
        @SuppressWarnings("deprecation")
        public WebDriver create(Config config, Browser browser, @Nullable Proxy proxy, File browserDownloadsFolder) {

            DriverService.Builder serviceBuilder = new ChromeDriverService.Builder().withSilent(true);
            ChromeOptions options = new ChromeOptions();
            ChromeDriverService chromeDriverService = (ChromeDriverService) serviceBuilder.build();
            chromeDriverService.sendOutputTo(new OutputStream() { // !!!!!!!! THIS WORKS !!!!!!!!
                @Override
                public void write(int b) {

                }
            });

            ChromeDriver driver = new ChromeDriver(chromeDriverService, options);
            return driver;
        }
    }


    private void setup() {
//        System.setProperty("webdriver.chrome.silentOutput", "true");
//        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
//        //System.setProperty("webdriver.chrome.args", "--disable-logging");
//        System.setProperty("webdriver.chrome.args", "--log-level=3");
//
//        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
//
//        // Let Selenide know which browser to use
//        System.setProperty("selenide.browser", seleniumWebBrowser);

//        if ("chrome".equals(seleniumWebBrowser)) {
//            WebDriverManager.chromedriver().setup();
//            WebDriver driver = new ChromeDriver();
//            WebDriverRunner.setWebDriver(driver);
//        }

        Configuration.browser = MyFactory.class.getName();
    }

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

    @Action
    public WebActions text_contains(String target, String value) {
        String elmText = elm(target).text();
        assertThat(elmText).contains(value).withFailMessage(
            "Expecting element [ " + target + " ] with text [ " + elmText + " ] to contain [ " + value + " ]");
        return this;
    }

    @Action
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