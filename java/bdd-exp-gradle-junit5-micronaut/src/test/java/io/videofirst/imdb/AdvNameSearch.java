package io.videofirst.imdb;

import io.videofirst.vfa.Alias;
import io.videofirst.vfa.Step;
import io.videofirst.vfa.Steps;
import io.videofirst.vfa.web.actions.WebActions;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Bob Marks
 */
@Singleton
@Alias("adv_name_search")
public class AdvNameSearch extends Steps<AdvNameSearch> {

    @Inject
    private WebActions web;

    // V2

    @Step
    public void given_I_am_at_the_Advanced_Name_Search_page() {
        web.open("https://www.imdb.com/search/name");
    }

    @Step
    public void when_I_type_name_Brad_Pitt() {
        web.type("name=name", "Brad Pitt");
    }

    @Step
    public void then_I_expect_to_see_results_with_name_Brad_Pitt() {
        web.exists("xpath=//h1[@class='header' and contains(text(), 'Brad Pitt')]");
    }

    @Step
    public void and_I_click_the_search_button() {
        web.click("#main button.primary");
    }

    // V3

    @Step
    public void when_I_type_name_$(String name) {
        web.type("name=name", name);
    }

    @Step
    public void then_I_expect_to_see_results_with_name_$(String name) {
        web.exists("xpath=//h1[@class='header' and contains(text(), '" + name + "')]");
    }

    @Step
    public void when_I_type_birthday_$(String birthday) {
        web.type("name=birth_monthday", birthday);
    }

    // V4

    @Step
    public AdvNameSearch I_am_at_the_Advanced_Name_Search_page() {
        web.open("https://www.imdb.com/search/name");
        return this;
    }

    @Step
    public AdvNameSearch I_type_name_$(String name) {
        web.type("name=name", name);
        return this;
    }

    @Step
    public AdvNameSearch I_type_birthday_$(String birthday) {
        web.type("name=birth_monthday", birthday);
        return this;
    }

    @Step
    public AdvNameSearch I_click_the_search_button() {
        web.click("#main button.primary");
        return this;
    }

    @Step
    public AdvNameSearch I_expect_to_see_results_with_name_$(String name) {
        web.exists("xpath=//h1[@class='header' and contains(text(), '" + name + "')]");
        return this;
    }

}
