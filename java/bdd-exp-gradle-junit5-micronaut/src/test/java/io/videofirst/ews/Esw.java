package io.videofirst.ews;

import io.videofirst.vfa.Step;
import io.videofirst.vfa.Steps;
import io.videofirst.vfa.web.actions.WebActions;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Esw extends Steps<Esw> {

    @Inject
    private WebActions web;

    @Step
    public Esw a_user_is_at_the_homepage() {
        web.open("https://esw.com");
        return this;
    }

    @Step
    public Esw a_user_clicks_the_company_menu() {
        web.click("li#menu-item-31513");
        return this;
    }

    @Step
    public Esw chooses_the_about_page() {
        web.click("li#menu-item-31514");
        return this;
    }

    @Step
    public Esw they_will_see_the_about_page() {
        web.url_equals("https://esw.com/about-us/");
        return this;
    }

}
