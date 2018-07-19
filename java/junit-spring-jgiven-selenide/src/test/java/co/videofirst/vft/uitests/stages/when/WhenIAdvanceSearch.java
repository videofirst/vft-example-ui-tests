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
package co.videofirst.vft.uitests.stages.when;

import co.videofirst.vft.uitests.stages.AbstractStage;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;

/**
 * When Stage - searching on the google advanced search page.
 *
 * @author Bob Marks
 */
@JGivenStage
public class WhenIAdvanceSearch extends AbstractStage<WhenIAdvanceSearch> {

    public WhenIAdvanceSearch I_enter_all_these_words(@Quoted String words) {
        advancedSearchPage.enterAllTheseWords(words);
        return self();
    }

    public WhenIAdvanceSearch I_enter_this_exact_word_or_phrase(@Quoted String input) {
        advancedSearchPage.enterExactPhrase(input);
        return self();
    }

    public WhenIAdvanceSearch perform_search() {
        advancedSearchPage.clickSearch();
        return self();
    }
}
