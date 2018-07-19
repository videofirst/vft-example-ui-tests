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
package co.videofirst.vft.uitests.stages;

import co.videofirst.vft.uitests.pageobjects.AdvancedSearchPage;
import co.videofirst.vft.uitests.pageobjects.HomePage;
import co.videofirst.vft.uitests.pageobjects.ResultsPage;
import com.tngtech.jgiven.Stage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract JGiven stage which all stages can extend from.
 *
 * @author Bob Marks
 */
public abstract class AbstractStage<SELF extends Stage<?>> extends Stage<SELF> {

    @Autowired
    protected HomePage homePage;

    @Autowired
    protected ResultsPage resultsPage;

    @Autowired
    protected AdvancedSearchPage advancedSearchPage;

}
