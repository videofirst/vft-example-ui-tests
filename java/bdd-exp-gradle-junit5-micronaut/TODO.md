TODO
====

  [x] Initial model structure / logging 
    [x] Create model structure - FeatureModel, ScenarioModel, StepModel, ActionModel.
    [x] Get output format correct.
    [x] Add given(), when() etc methods.
    [x] Colour output.
    [x] Action indents.
    [x] Add start/finish to service layer + timings.
    [x] Sort out $ in method names.
    [x] Remove logs from Chrome driver (hacky for now).
    [x] Refactor @Prefix to @Alias.
    [x] Align Junit scenario naming to VFA naming
  [x] Screenshots   
    [x] Make actions optional / split into 2 BeforeAction / AfterAction classes.  
    [x] Check out screenshots (e.g. after @Action) / before and after action methods.
    [x] Save feature model / screenshots in a class folder.
  [x] Asserts / errors / stacktraces 
    [x] Ensure that errors are handled correct + new status in acti   on, steps and scenario.
    [x] If error occurs, ensure steps and actions are still displayed (ignored + display grey).
    [x] Create labels in configuration theme e.g. to support unicode ticks etc.
    [x] Ensure failures are handled different to errors.
    [x] Remove unnecessary classes from stacktraces.
    [x] Add new 'exceptions' configurable section.
    [x] Add method to actions to handle exceptions.
    [x] Abbreviate fully qualified class name so a configurable number of parts are displayed. 
    [x] Add `core-ignores` + optional `ignores` to configuration. 
    [x] Wrapper exceptions so we don't display output (for Intelli-J support).  
    [x] Configurable option to show full stacktrace. 
    [x] Make exception position configurable (inline / bottom) using config.
  [.] Tidy-up
    [x] Fix bug where child actions create multiple statuses.
    [x] Create different colours for main/other step types.
    [x] Add quotations to String parameters to logs + different colour.
    [x] Override automatic quoting of step String params based on annotation.
    [x] Add default add parameter quoting in config.
    [x] Add additional complex parameter support using curly brackets.
    [x] Add parameter and options functionality to static `step` methods.
    [.] Ensure stacktrace from (e.g. invalid step method) are trimmed.
  [ ] Modules
    [ ] Create new vfa repo (in bitbucket?)
    [ ] Refactor into modules - for now (1 platform) + (1 selenide).
    [ ] Create selenide hierarchy.
    [ ] put "com.codeborne.selenide.impl" into it's own property hierarchy.
  [ ] Release
    [ ] Basic docs (use adoc?)
    [ ] Aim for Alpha Release end of October.
    [ ] Add action timings to logs (if over threshold).
    [ ] Aim for Beta Release end of October.
    [ ] Aim for Video First integration end of November.
    [ ] Aim for Video First marketing end of December.
    [ ] Aim for Video First open source E2E testing.
  [ ] Extras
    [ ] Make saving screenshots / page models are configurable.
    [ ] Add counts in feature.
    [ ] IntelliJ options for not saving screenshots when in debug mode.
    [ ] Strict mode for Video First.
    [ ] Slow debugging - https://youtrack.jetbrains.com/issue/IDEA-27221.
    [ ] Log levels - show features, scenarios, steps or actions.

Maybe
=====

    [ ] Validations? - e.g. Feature cannot have @Step or @Action methods.
    [ ] Programatic action method?