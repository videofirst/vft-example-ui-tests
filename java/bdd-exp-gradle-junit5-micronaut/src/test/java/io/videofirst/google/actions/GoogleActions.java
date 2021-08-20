package io.videofirst.google.actions;

import io.videofirst.vfa.Action;
import io.videofirst.vfa.web.actions.WebActions;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GoogleActions {

    @Inject
    private WebActions web;

    // Actions

    @Action
    public GoogleActions open_homepage() {
        web.open("https://www.google.com");
        return this;
    }

}
