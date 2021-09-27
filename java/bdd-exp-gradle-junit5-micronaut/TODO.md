TODO
====

    [x] - Create model structure - FeatureModel, ScenarioModel, StepModel, ActionModel.
    [x] - Get output format correct.
    [x] - Add given(), when() etc methods.
    [x] - Colour output.
    [x] - Action indents.
    [x] - Add start/finish to service layer + timings.
    [x] - Sort out $ in method names.
    [x] - Remove logs from Chrome driver (hacky for now).
    [x] - Refactor @Prefix to @Alias.
    [x] - Align Junit scenario naming to VFA naming
    [x] - Make actions optional / split into 2 BeforeAction / AfterAction classes.  
    [x] - Check out screenshots (e.g. after @Action) / before and after action methods.
    [x] - Save feature model / screenshots in a class folder.
    [x] - Ensure that errors are handled correct + new status in acti   on, steps and scenario.
    [x] - If error occurs, ensure steps and actions are still displayed (ignored + display grey).
    [x] - Create labels in configuration theme e.g. to support unicode ticks etc.
    [x] - Ensure failures are handled different to errors.
    [x] - Remove unnecessary classes from stacktraces.
    [x] - Add new 'exceptions' configurable section.
    [x] - Add method to actions to handle exceptions.
    [x] - Abbreviate fully qualified class name so a configurable number of parts are displayed. 
    [.] - Make exception position configurable (inline / bottom).
    [ ] - Wrapper exceptions inside an exception which doesn't output (for Intelli-J support).  
    [ ] - Make when screenshots / page models are configurable.
    [ ] - Add counts in feature.
    [ ] - Refactor into modules - for now (1 platform) + (1 web).
    [ ] - put "com.codeborne.selenide.impl" into it's own property hierarchy.
    [ ] - Add action timings to logs (if over threshold).
    [ ] - Investigate documentation.
    [ ] - Aim for Alpha Release end of September.
    [ ] - Aim for Beta Release end of October.
    [ ] - Aim for Video First integration end of November.
    [ ] - Aim for Video First marketing end of December.
    [ ] - Aim for Video First open source E2E testing.

Maybe
=====
    
    [ ] - Validations? - e.g. Feature cannot have @Step or @Action methods.
    [ ] - Programatic action method?