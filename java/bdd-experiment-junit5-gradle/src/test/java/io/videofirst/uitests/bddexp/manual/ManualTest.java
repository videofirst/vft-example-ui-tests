package io.videofirst.uitests.bddexp.manual;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.TEXT_COLOR;
import static org.junit.jupiter.api.Assertions.fail;

import com.diogonunes.jcolor.Attribute;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

/**
 * @author Bob Marks
 */
public class ManualTest {

    private static final Map<String, Object> params = new HashMap<>();

    {
        params.put("ui", "https://www.imdb.com");
    }

    // Scenario: Search for film "The Green Mile"
    @Test
    public void shouldSearchForFilmTheGreenMile() {
        // Given a user is at the home page
        //     [ ui_open ${ui} ]
        open((String) params.get("ui"));

        // When the user types "The Green Mile" into search box
        //     [ ui_type #suggestion-search "The Green Mile" ]
        $("#suggestion-search").setValue("The Green Mile");

        // And the user clicks the search icon
        //     [ ui_click #suggestion-search-button ]
        $("#suggestion-search-button").click();

        // Then I expect to see the results page
        //     [ ui_text_contains .findHeader "Results for" ]
        if (!$(".findHeader").text().contains("Results for")) {
            fail(".findHeader does not contain \"Results for\"");
        }
        //     [ ui_text_contains .findSearchTerm "The Green Mile" ]
        if (!$(".findSearchTerm").text().contains("The Green Mile")) {
            fail(".findSearchTerm does not contain \"The Green Mile\"");
        }

        $(By.xpath(".//td[@class='result_text']/a[text() = 'The Green Mile']")).exists();
        //      And the top result only contains "The Green Mile"       [ ui_xpath_count_gt ".//td[@class='result_text']/a[text() = 'The Green Mile']" 0]
    }

    @Test
    public void showColours() {
        System.out.println("█" + colorize("█", TEXT_COLOR(7)));

        for (int i = 0; i <= 255; i++) {
            Attribute txtColor = TEXT_COLOR(i);
            System.out.print(colorize(String.format("%4d █", i), txtColor));
            if (i % 16 == 15) {
                System.out.println();
            }
        }
    }

}
