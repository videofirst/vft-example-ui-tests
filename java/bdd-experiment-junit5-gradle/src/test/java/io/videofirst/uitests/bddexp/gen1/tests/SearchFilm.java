package io.videofirst.uitests.bddexp.gen1.tests;

import io.videofirst.uitests.bddexp.vfa.common.VfaAction;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiClick;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiExists;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiOpen;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiTextContains;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiType;

/**
 * @author Bob Marks
 */
public abstract class SearchFilm {

    // Actions
    private UI ui = new UI();
    //private ImdbActions imdb = new ImdbActions();

    protected void given_a_user_is_at_the_home_page() {
        ui.open("https://www.imdb.com");
    }

    protected void when_the_user_types_the_green_mile_into_search_box() {
        ui.type("id=suggestion-search", "The Green Mile");
    }

    protected void and_the_user_clicks_the_search_icon() {
        ui.click("#suggestion-search-button", "The Green Mile");
    }

    protected void then_I_expect_to_see_the_results_page() {
        ui.text_contains(".findHeader", "Results for");
        ui.text_contains(".findSearchTerm", "The Green Mile");
    }

    protected void and_the_top_result_only_contains_The_Green_Mile() {
        ui.exists("xpath=.//td[@class='result_text']/a[text() = 'The Green Mile']");
    }

    //

    class UI {

        private void execute(VfaAction action, String... params) {
            action.init(params);
            action.execute();
        }

        public void open(String... params) {
            execute(new UiOpen(), params);
        }

        public void type(String... params) {
            execute(new UiType(), params);
        }

        public void click(String... params) {
            execute(new UiClick(), params);
        }

        public void text_contains(String... params) {
            execute(new UiTextContains(), params);
        }

        public void exists(String... params) {
            execute(new UiExists(), params);
        }
    }

}
