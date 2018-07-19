/*
 * Copyright (c) 2017-present, Video First Software
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package co.videofirst.vft.uitests.pageobjects;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Page object class for google home page.
 *
 * @author Bob Marks
 */
@Component
public class AdvancedSearchPage {

    @Value("${homepage.url}")
    private String homepageUrl;

    public AdvancedSearchPage visit() {
        open(homepageUrl + "/advanced_search");
        return this;
    }

    public AdvancedSearchPage enterAllTheseWords(String words) {
        $(By.name("as_q")).shouldBe(visible).setValue(words);
        return this;
    }

    public AdvancedSearchPage enterExactPhrase(String input) {
        $(By.name("as_epq")).shouldBe(visible).setValue(input);
        return this;
    }

    public AdvancedSearchPage enterAnyOfTheseWords(String words) {
        $(By.name("as_oq")).shouldBe(visible).setValue(words);
        return this;
    }

    public AdvancedSearchPage clickSearch() {
        $("input[value='Advanced Search']").shouldBe(visible).click();
        return this;
    }

}
