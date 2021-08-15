package io.videofirst.vfa.actions;

import io.micronaut.context.annotation.Primary;
import io.videofirst.vfa.Action;
import javax.inject.Singleton;

@Singleton
@Primary
public class AdvancedWebActions extends WebActions {

    @Action
    public void advanced_thing(String xpath) {

    }

}