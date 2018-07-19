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


import co.videofirst.vft.uitests.stages.given.GivenIamAtTheHomePage;
import co.videofirst.vft.uitests.stages.then.ThenSearchResultsReturn;
import co.videofirst.vft.uitests.stages.when.WhenISearch;
import org.junit.Test;

/**
 * Google basic search test
 *
 * @author Bob Marks
 */
public class BasicSearchTest extends
    AbstractFeature<GivenIamAtTheHomePage, WhenISearch, ThenSearchResultsReturn> {

    @Test
    public void search_single_word() { // @formatter:off
        given().I_am_at_the_homepage();

         when().I_search_for("Dogs");

         then().I_expect_search_box_to_contain("Dogs")
         .and().search_results_items_to_contain("Dogs");
    } // @formatter:on

    @Test
    public void search_simple_phrase() { // @formatter:off
        given().I_am_at_the_homepage();


         when().I_search_for("Video First Testing");

         then().I_expect_search_box_to_contain("Video First Testing")
         .and().search_results_items_to_contain("Video First Testing");
    } // @formatter:on

}
