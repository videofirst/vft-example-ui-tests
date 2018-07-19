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
package co.videofirst.vft.uitests.features;

import co.videofirst.vft.uitests.stages.given.GivenIamAtTheAdvancedSearchPage;
import co.videofirst.vft.uitests.stages.then.ThenSearchResultsReturn;
import co.videofirst.vft.uitests.stages.when.WhenIAdvanceSearch;
import org.junit.Test;

/**
 * Google advanced search test.
 *
 * @author Bob Marks
 */
public class AdvancedSearchTest extends
    AbstractFeature<GivenIamAtTheAdvancedSearchPage, WhenIAdvanceSearch, ThenSearchResultsReturn> {

    @Test
    public void search_all_these_words() { // @formatter:off
        given().I_am_at_the_advanced_search_page();

         when().I_enter_all_these_words("Video First Testing")
         .and().perform_search();

         then().I_expect_search_box_to_contain("Video First Testing")
         .and().search_results_items_to_contain("Video First Testing");
    } // @formatter:on

    @Test
    public void search_exact_word_or_phrase() { // @formatter:off
        given().I_am_at_the_advanced_search_page();

         when().I_enter_this_exact_word_or_phrase("Fish oil supplements for a healthy heart")
         .and().perform_search();

         then().I_expect_search_box_to_contain("Fish oil supplements for a healthy heart")
         .and().search_results_items_to_contain("Fish oil supplements for a healthy heart");
    } // @formatter:on


    @Test
    public void search_both_all_words_and_exact_phrase() { // @formatter:off
        given().I_am_at_the_advanced_search_page();

         when().I_enter_all_these_words("Fish oil supplements")
         .and().I_enter_this_exact_word_or_phrase("healthy heart")
         .and().perform_search();

         then().I_expect_search_box_to_contain("Fish oil supplements for a healthy heart")
         .and().search_results_items_to_contain("Fish oil supplements for a healthy heart");
    } // @formatter:on

}
