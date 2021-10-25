[![Apache License 2.0](https://img.shields.io/badge/license-apache2-red.svg?style=flat-square)](http://opensource.org/licenses/Apache-2.0)

## VFA (Video First Automation)

VFA (**Video First Automation**) is a Java based BDD (Behavior-Driven Development) framework from
the same folk who created [Video First](https://www.videofirst.io) (a UI testing platform which uses
video to capture and share UI tests).

VFA enables developers and testers to _quickly_ and _easily_ create beautiful automated tests with
zero loss of power or flexibility. It focuses on E2E (end-to-end) user interface tests but also
supports testing e.g. API endpoints.

## Example Test

The best way to show VFA in action is with an example. The following test searching for the film
"The Green Mile" on the [IMDB](https://www.imdb.com) (Internet Movie DataBase) website.

```java

import static io.videofirst.vfa.Vfa.*;   // e.g. given("a user is at the homepage");

import io.videofirst.vfa.Feature;
import io.videofirst.vfa.Scenario;
import io.videofirst.vfa.web.actions.WebActions;

import javax.inject.Inject;

@Feature
public class SearchFilms {

    @Inject
    private WebActions web;    // Injected low-level action class

    @Scenario
    public void search_for_film_The_Green_Mile() {
        given("a user is at the homepage");
        web.open("https://www.imdb.com");

        when("the user types the \"The Green Mile\" into search box");
        web.type("id=suggestion-search", "The Green Mile");

        and("the user clicks the search icon");
        web.click("#suggestion-search-button");

        then("I expect the see the results page");
        web.text_contains(".findHeader", "Results for");
        web.text_contains(".findSearchTerm", "The Green Mile");

        and("the top result only contains \"The Green Mile\"");
        web.exists("xpath=.//td[@class='result_text']/a[text() = 'The Green Mile']");
    }

}
```

This can be run from your IDE (we highly recommend [IntelliJ IDEA](https://www.jetbrains.com/idea/))
just like any JUnit test. The following will be displayed in the console: -

```
Feature: Search Films

  Scenario: Search for Film The Green Mile

    Given a user is at the homepage                             : open ("https://www.imdb.com") ✔
     When the user types the "The Green Mile" into search box   : type ("id=suggestion-search", "The Green Mile") ✔
      And the user clicks the search icon                       : click ("#suggestion-search-button") ✔
     Then I expect the see the results page                     : text_contains (".findHeader", "Results for") ✔
                                                                : text_contains (".findSearchTerm", "The Green Mile") ✔
      And the top result only contains "The Green Mile"         : exists ("xpath=.//td[@class='result_text']/a[text() = 'The Green Mile']") ✔

```

VFA outputs standard BDD format i.e. _Feature_ (e.g. `Search Films`) -> _Scenarios_
(e.g. `Search for Film The Green Mile`) -> _Steps_ (e.g. `Given a user is at the homepage`).    
A unique feature of VFA are actions (e.g. `open ("https://www.imdb.com")`) and how they are
displayed i.e. high level steps are shown on the left (which anyone can understand) and lower level
actions are displayed on the right-hand-side (useful for engineers / testers).

The above test can be executed like any JUnit test. During the execution, JSON files are generated
that can then be used afterwards to generate test reports.

## What Problems does VFA Solve?

VFA is a the result of chatting to many testers and engineers about their experiences creating and
running E2E user interface tests.

1. **Getting Started** - engineers often complain that creating a new UI automation projects takes a
   long time to set up. VFA removes that time with a project generator   
   [VFA Starter](https://start.videofirst.io)) which generates everything you need to get started.
2. **Complexity** - some frameworks like Cucumber require you create multiple files
   (e.g. a feature file, a class and test code) when creating your first test. This obviously puts
   off new users. With VFA you can write your first full E2E with a single method of a single class.
3. **Structure** - it can be confusing for new users to know how to properly structure tests. VFA
   enforces an opinionated structure (_feature_ -> _scenario_ -> _steps_ -> _actions_) which ensures
   a high quality structure with many advantages (including scaling the test codebase).
4. **Confusing Logs** - a common complaint is messy logs where it isn't clear what is happening at
   either a high or low level. VFA produces beautiful logs and shows high level contextual BDD
   (steps) on the left-hand-side and lower-level action logs on the right-hand-side which saves
   users valuable time when understanding / fixing broken tests.
5. **Java Stack Traces** - another complaint is massive Java exception stack traces. VFA keeps stack
   traces to a bare minimum which looks better and saves time debugging / fixing broken tests.
7. **Configurability** - another problem testers encounter is configuring their tests, especially on
   different environments. VFA uses [Micronaut](https://micronaut.io/) which provides easy and
   powerful configurability.

## What's Next

## License

VFA is published under the Apache License 2.0, see https://www.apache.org/licenses/LICENSE-2.0
for the full licence.

## Contributing

See [CONTRIBUTING](CONTRIBUTING.md)
