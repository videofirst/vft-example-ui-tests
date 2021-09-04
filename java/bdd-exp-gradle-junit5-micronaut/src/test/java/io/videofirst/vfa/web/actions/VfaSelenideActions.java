package io.videofirst.vfa.web.actions;

import com.codeborne.selenide.Browser;
import com.codeborne.selenide.Config;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.impl.ScreenShotLaboratory;
import com.codeborne.selenide.webdriver.ChromeDriverFactory;
import io.videofirst.vfa.Action;
import io.videofirst.vfa.AfterAction;
import io.videofirst.vfa.BeforeAction;
import io.videofirst.vfa.enums.VfaMediaCapture;
import io.videofirst.vfa.model.VfaAction;
import io.videofirst.vfa.properties.VfaMediaProperties;
import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.service.DriverService;

/**
 * Base VFA UI actions class.
 *
 * @author Bob Marks
 */
public abstract class VfaSelenideActions implements BeforeAction, AfterAction {

    private String seleniumWebBrowser = "chrome"; // FIXME inject from config

    @Inject
    private VfaMediaProperties mediaProperties;

    private ScreenShotLaboratory screenShotLaboratory = ScreenShotLaboratory.getInstance();

    public VfaSelenideActions() {
        // 1) This line gets rid of
        //     Aug 19, 2021 8:54:15 AM org.openqa.selenium.remote.ProtocolHandshake createSession
        //     INFO: Detected dialect: W3C
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        setup();
    }

    @Override
    public void before(VfaAction action) {
        checkScreenshot(action, true);
    }

    @Override
    public void after(VfaAction action) {
        checkScreenshot(action, false);
    }

    // Private methods

    private void checkScreenshot(VfaAction action, boolean isBefore) {
        if (mediaProperties.getCapture() != VfaMediaCapture.screenshots) {
            return; // nothing more to do
        }

        // Grab annotation and see if we take a screenshot or not
        Action actionAnnotation = getActionAnnotation(action);
        boolean beforeScreenshot = isBefore && actionAnnotation.screenshotBefore();
        boolean afterScreenshot = !isBefore && actionAnnotation.screenshotAfter();
        boolean takeScreenshot = beforeScreenshot || afterScreenshot;

        if (takeScreenshot) {
            // File file = Screenshots.takeScreenShotAsFile();
            // String pngFileName = screenshot("my_file_name");
            // check ScreenShotLaboratory - especially around setting context etc although I don't think we should be
            // doing that all the time.
            Selenide.screenshot("asdfasdf");
        }

        // Check UIAssertionError.wrapThrowable()
    }

    /**
     * FIXME Might be better in a base abstract class.
     */
    protected Action getActionAnnotation(VfaAction action) {
        Method method = action.getMethodContext().getTargetMethod();
        Action actionAnnotation = method.getAnnotation(Action.class);
        return actionAnnotation;
    }

    private void setup() {

        // Let Selenide know which browser to use
        System.setProperty("selenide.browser", seleniumWebBrowser);

        if ("chrome".equals(seleniumWebBrowser)) {
            // Not currently being used
//            WebDriverManager.chromedriver().setup();
//            WebDriver driver = new ChromeDriver();
//            WebDriverRunner.setWebDriver(driver);
            Configuration.browser = MyFactory.class.getName();
        }
    }

    // FIXME where does this stuff live?  Configuration file maybe????
    // Ref: https://mbbaig.blog/selenide-webdriverfactory/
    private static class MyFactory extends ChromeDriverFactory {

        // REF [ https://stackoverflow.com/a/54301361 ]
        // NOTE: this needs requires much more work and should feed into any experiments in future around logging.
        @Override
        @CheckReturnValue
        @NotNull
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

}
