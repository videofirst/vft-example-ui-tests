package io.videofirst.google.actions;

import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Primary;
import io.videofirst.vfa.Action;
import io.videofirst.vfa.web.actions.WebActions;

@Context
@Primary
public class AdvancedWebActions extends WebActions {

    @Action
    public void advanced_thing(String xpath) {

    }

}