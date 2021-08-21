package io.videofirst.google.actions;

import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Primary;
import io.videofirst.vfa.Action;
import io.videofirst.vfa.Alias;
import io.videofirst.vfa.web.actions.WebActions;

@Context
@Primary
@Alias("adv_web")
public class AdvancedWebActions extends WebActions {

    public AdvancedWebActions() {
        super();
    }

    @Action
    public void advanced_thing(String xpath) {

    }

}