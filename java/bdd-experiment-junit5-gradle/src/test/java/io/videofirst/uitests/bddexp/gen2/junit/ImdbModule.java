package io.videofirst.uitests.bddexp.gen2.junit;

import static io.videofirst.uitests.bddexp.gen2.junit.VfaActions.actions;

/**
 * IMDB module.
 *
 * @author Bob Marks
 */
public class ImdbModule implements VfaModule {

    private String homepage = "https://www.imdb.com";

    private UiModule ui = new UiModule();

    public VfaActions open_homepage() {
        return actions("open_homepage",
            ui.open(homepage));
    }

    public VfaActions search(String film) {
        return actions("search",
            ui.type("id=suggestion-search", film),
            ui.click("#suggestion-search-button"));
    }

    public VfaActions results_page_is_visible(String film) {
        return actions("results_page_is_visible",
            ui.text_contains(".findHeader", "Results for"),
            ui.text_contains(".findSearchTerm", film));
    }

    public VfaActions results_page_contains_only(String film) {
        return actions("results_page_contains_only",
            ui.exists("xpath=.//td[@class='result_text']/a[text() = '" + film + "']"));
    }

    @Override
    public String getName() {
        return "imdb";
    }

}
