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
    [ ] - Ensure that errors are handled correct + new status in scenario / counts in feature.
    [ ] - Refactor into modules - for now (1 platform) + (1 web).
    [ ] - Add timings to logs (if over threshold).
    [ ] - Investigate documentation.

Maybe
=====

    [ ] - Ensure test methods names are same as scenario names.
    [ ] - Add validations? - e.g. Feature cannot have @Step or @Action methods.
    [ ] - Programatic action method?