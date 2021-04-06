package io.videofirst.uitests.bddexp.gen2.junit;

import static io.videofirst.uitests.bddexp.vfa.VfaStep.step;
import static io.videofirst.uitests.bddexp.vfa.VfaStepType.and;
import static io.videofirst.uitests.bddexp.vfa.VfaStepType.given;
import static io.videofirst.uitests.bddexp.vfa.VfaStepType.then;
import static io.videofirst.uitests.bddexp.vfa.VfaStepType.when;

import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.videofirst.uitests.bddexp.vfa.VfaScenario;
import io.videofirst.uitests.bddexp.vfa.VfaTestRunner;
import io.videofirst.uitests.bddexp.vfa.common.VfaAction;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

/**
 * @author Bob Marks
 */
public abstract class VfaSteps {

    // Move this somewhere better

    private String seleniumWebBrowser = "chrome";

    private static final Map<String, Object> params = new HashMap<>();

    {
        System.setProperty("webdriver.chrome.silentOutput", "true");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        System.setProperty("webdriver.chrome.args", "--disable-logging");

        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        // Let Selenide know which browser to use
        System.setProperty("selenide.browser", seleniumWebBrowser.toString());

        if ("chrome".equals(seleniumWebBrowser)) {
            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            WebDriverRunner.setWebDriver(driver);
        }
        params.put("ui", "https://www.imdb.com");
    }

    private VfaTestRunner testRunner = new VfaTestRunner();

    // Given

    protected void given(String name) {
        testRunner.executeStep(getScenario(), step(given, name));
    }

    protected void given(String name, String... actions) {
        // TODO
    }

    protected void given(String name, VfaAction... actions) {
        testRunner.executeStep(getScenario(), step(given, name, actions));
    }

    // When

    protected void when(String name) {
        testRunner.executeStep(getScenario(), step(when, name));
    }

    protected void when(String name, String... actions) {
        // TODO
    }

    protected void when(String name, VfaAction... actions) {
        testRunner.executeStep(getScenario(), step(when, name, actions));
    }

    // Then

    protected void then(String name) {
        testRunner.executeStep(getScenario(), step(then, name));
    }

    protected void then(String name, String... actions) {
        // TODO
    }

    protected void then(String name, VfaAction... actions) {
        testRunner.executeStep(getScenario(), step(then, name, actions));
    }

    // Other

    protected void and(String name) {
        testRunner.executeStep(getScenario(), step(and, name));
    }

    protected void and(String name, String... actions) {

    }

    protected void and(String name, VfaAction... actions) {
        testRunner.executeStep(getScenario(), step(and, name, actions));
    }

    // Actions (TODO)

//    protected void action(String action) {
//    }
//
//    protected void actions(String... action) {
//    }

    // Private methods

    private VfaScenario getScenario() {
        ScenarioMethod scenarioMethod = FeatureExtension.currentScenario.get();
        return scenarioMethod.getVfaScenario();
    }

}
