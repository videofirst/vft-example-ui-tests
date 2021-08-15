package io.videofirst.vfa.actions;

import io.videofirst.vfa.Action;
import javax.inject.Singleton;

/**
 * Move to it's own module.
 */
@Singleton
public class WebActions {

    @Action
    public WebActions open(String url) {
        return this;
    }

    @Action
    public WebActions click(String target) {
        return this;
    }

    @Action
    public WebActions type(String target, String value) {
        return this;
    }

    @Action
    public WebActions text_contains(String target, String value) {
        return this;
    }

    @Action
    public WebActions exists(String target) {
        return this;
    }

    @Action
    public WebActions timeout(int timeout) {
        return this;
    }

}