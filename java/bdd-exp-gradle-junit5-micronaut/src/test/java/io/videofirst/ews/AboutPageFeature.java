package io.videofirst.ews;

import io.videofirst.vfa.Feature;
import io.videofirst.vfa.Scenario;
import javax.inject.Inject;

@Feature(id = 123)
public class AboutPageFeature {

    @Inject
    private Esw esw;

    @Scenario(id = 456)
    public void view_about_page() {
        esw.given().a_user_is_at_the_homepage()

            .when().a_user_clicks_the_company_menu()
            .and().chooses_the_about_page()

            .then().they_will_see_the_about_page();
    }

    @Scenario
    public void view_careers_page() {
        esw.given().a_user_is_at_the_homepage()

            .when().a_user_clicks_the_company_menu()
            .and().chooses_the_about_page()

            .then().they_will_see_the_about_page();
    }

    @Scenario
    public void view_careers_page2() {

    }

}
