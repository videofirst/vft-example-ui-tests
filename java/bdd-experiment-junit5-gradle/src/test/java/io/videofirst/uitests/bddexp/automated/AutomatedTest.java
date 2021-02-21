package io.videofirst.uitests.bddexp.automated;

import static io.videofirst.uitests.bddexp.vfa.VfaStepType.and;
import static io.videofirst.uitests.bddexp.vfa.VfaStepType.given;
import static io.videofirst.uitests.bddexp.vfa.VfaStepType.then;
import static io.videofirst.uitests.bddexp.vfa.VfaStepType.when;
import static io.videofirst.uitests.bddexp.vfa.common.VfaActionFactory.create;
import static java.util.Arrays.asList;

import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.videofirst.uitests.bddexp.vfa.VfaFeature;
import io.videofirst.uitests.bddexp.vfa.VfaScenario;
import io.videofirst.uitests.bddexp.vfa.VfaStep;
import io.videofirst.uitests.bddexp.vfa.VfaTestRunner;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

/**
 * Video First automation test.
 *
 * @author Bob Marks
 */
public class AutomatedTest {

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

    // Feature: This feature will search the IMDB website for various films.

    private static VfaFeature feature = VfaFeature.builder()
        .label("Search for a film in various different ways")
        .description("This feature will search the IMDB website for various films.")
        .scenarios(asList(

            //  Scenario: Search for film "The Green Mile"
            VfaScenario.builder()
                .label("Search for film \"The Green Mile\"")
                .steps(asList(

                    // Given a user is at the home page                        [ open ${ui} ]
                    VfaStep.builder()
                        .type(given)
                        .label("a user is at the home page")
                        .actions(asList(create("open", "https://www.imdb.com")))
                        .build(),

                    // When the user types "The Green Mile" into search box   [ type #suggestion-search "The Green Mile" ]
                    VfaStep.builder()
                        .type(when)
                        .label("the user types \"The Green Mile\" into search box")
                        .actions(
                            asList(create("type", "id=suggestion-search", "The Green Mile")))
                        .build(),

                    // And the user clicks the search icon                   [ click #suggestion-search-button ]
                    VfaStep.builder()
                        .type(and)
                        .label("the user clicks the search icon")
                        .actions(asList(create("click", "#suggestion-search-button")))
                        .build(),

                    // Then I expect to see the results page                  [ text_contains .findHeader "Results for" ] [ text_contains .findSearchTerm "The Green Mile" ]
                    VfaStep.builder()
                        .type(then)
                        .label("I expect to see the results page")
                        .actions(asList(
                            create("text_contains", ".findHeader", "Results for"),
                            create("text_contains", ".findSearchTerm", "The Green Mile")
                        ))
                        .build(),

                    // And the top result only contains "The Green Mile"     [ exists ".//td[@class='result_text']/a[text() = 'The Green Mile']" ]
                    VfaStep.builder()
                        .type(and)
                        .label("the top result only contains \"The Green Mile\"")
                        .actions(asList(create("exists",
                            "xpath=.//td[@class='result_text']/a[text() = 'The Green Mile']")))
                        .build()

                ))
                .build()
        ))
        .build();

    private VfaTestRunner testRunner = new VfaTestRunner();

    @Test
    public void shouldTestFeature() {
        testRunner.executeFeature(feature);
    }

}
