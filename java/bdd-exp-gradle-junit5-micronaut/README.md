# BDD Experiment using Gradle, Junit5 + Micronaut.


## Thoughts (1) on 25th July 2021

This experiment is a continuation of the various experiments of `bdd-experiment-junit5-gradle` - 
this time around we've changed focus slightly i.e.

1. *Move from VFA DSL to pure Java* - creating a new and usable DSL is a massive amount of work and 
   even when completed with the necessary tooling etc there is a huge risk that a lot of people may 
   not even be interested. The focus has (for now) changed to a pure Java solution, but one which is
   VERY easy and quite opinionated.  The main advantage here is the raw power of Java.  The idea 
   would be similar to e.g. JGiven, but much lighter i.e. not forcing creating `Given`, `When` and 
   `Then` classes but rather using annotations and creating much lighter "custom" action classes 
   e.g. `ImdbActions` and also avoid inheritance as much as possible.
    
2. *Light-weight DI Framework* - Using a DI framework can bring many advantages to coding including
   (1) easy management / injection of action classes, (2) AOP capabilities which can massively
   simplify test code and (3) advanced configuration capabilities out of the box.  Of course, Spring
   framework is super popular but suffers from speed and size.  Two alternatives were investigated
   (1) PicoContainer (very light) and (2) Micronaut (very small / fast). For now, Micronaut was 
   chosen as it is extremely fast, small library size, easy Action injection, OAP capabilities and
   configuration very similar to Spring. 

3. *Laser focus on debugging* - Using Java is obviously more heavy-weight than a DSL but another
   clear advantages (in addition to the raw power of Java) is debugging - especially when a test 
   breaks.  The design of the Java DSL will be influenced heavily by that e.g. instead of 
   the `given` text and `action` in one line in previous experiments e.g.
   
       given("a user clicks on the search button", ui.click("#search-button"));
       
   ... it will be spread over 2 lines ...
   
       given("a user clicks on the search button");
       ui.click("#search-button");
   
   This is more verbose and mixes the `given`, `when`, `then` labels along with action calls,
   however the big advantage is that a user can click on the start of the `ui.click` line to easily
   debug this.
   
4. *Easy to program* - it's still super important that novice programmers can pick this up as easily
   as possible.  All code design decisions will have this as a focus.  However, more advanced coders
   will appreciate the raw power of Java.
   
5. *Output as DSL* - even though we're not using a DSL as an input - the output will be the same 
   format. In the future we may reconsider this DSL as an input if users request it.  


## Thoughts (2) on 15th August 2021

A fair bit of code has now been written and impressions on this experiment are extremely positive. 
IMO this experiment iteration has been the most successful / positive so far.  It lays the 
foundations for a future framework to enable to jump start their UI automated testing.

This experiment takes a lot of influence from BDD libraries such as [JGiven](https://jgiven.org/) or
[Serenity BDD](http://www.thucydides.info/#/).  However, to take JGiven as an example, it requires a
minimum of 4 classes to get started i.e. (1) a feature class, (2) Given class, (3) When class and 
finally a (4) Then class.  This does have the advantage of enforcing a strict and potentially DRY 
approach.  However, it is felt that it has the following disadvantages: -

1. *Getting Started*- creating 4 classes is off-putting for new users.
2. *Flexibility* - a step method inside a `Given` cannot be used in a `Then` step.
3. *Unit Testing Library* - user needs to decide if Junit or TestNG is the preferred unit 
   testing framework. Also, as of writing this Junit 5 with JGiven is in experimental state.  
   Advanced users may like this flexibility, but it is another thing for less experienced users to consider / figure out.
4. *Configurability* - to add configurability a typical user would add a DI framework such as Spring.  However, this is 
   an additional complex step and requires a more advanced user to understand.
5. *Selenium Libraries* - testing user interfaces requires considerable setup e.g. which Java 
   library for Selenium do we use, again off-putting for new users. 
   
However, even with all these disadvantages of JGiven, the fact that it enables the creation of BDD 
scenarios as pure Java is still (IMO) a fantastic idea and much more efficient that writing Cucumber 
style feature files (and the required complex Java Step regexes to link the two together). 
    
A key success principle of Video First Automation is "the ability to convert users from manual UI
testing to automated UI testing as quickly and as easily as possible".

With all this in mind, the focus of this experiment was to take the best bits of JGiven but also
enable users to create a scenario in a single class so they can get started extremely quickly. 

```java
import static io.videofirst.vfa.Vfa.*;  // given, when, then, and methods

import io.videofirst.vfa.Feature;
import io.videofirst.vfa.Scenario;
import io.videofirst.vfa.web.actions.WebActions;

import javax.inject.Inject;    // Used to inject action classes.

@Feature
public class SearchFilms {

    @Inject
    private WebActions web;

    @Scenario
    public void search_for_film_The_Green_Mile() {
        given("a user is at the homepage");
        web.type("id=suggestion-search", "The Green Mile");

        when("the user types the \"The Green Mile\" into the search box");
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

When this is run it will produce the following output: -

```
Feature: Search Films

  Scenario: Search for film The Green Mile

    Given a user is at the homepage                             : open ("https://www.imdb.com")
     When the user types the "The Green Mile" into search box   : type ("id=suggestion-search", "The Green Mile")
      And the user clicks the search icon                       : click ("#suggestion-search-button")
     Then I expect the see the results page                     : text_contains (".findHeader", "Results for")
                                                                : text_contains (".findSearchTerm", "The Green Mile")
      And the top result only contains "The Green Mile"         : exists ("xpath=.//td[@class='result_text']/a[text() = 'The Green Mile']")
```

As you can see it's extremely fast to get started and users can create a full blown end-to-end test
in a single method of a single class.  The feature and scenario text is generated automatically from
the class / method names.  The text on the left is standard [Gherkin](https://cucumber.io/docs/gherkin)
syntax, the text on the right of the `colon` is "action" text.   

The user can of course refactor this example to multiple classes to make the tests more re-usable / 
DRY.

VFA has an opinionated structure and enforces 4 levels of abstraction: -

1. **Feature** (e.g. _"Search Films"_) - In VFA, this is a Java class which is annotated with
   `@Feature`.  A feature contains 1 or more scenarios.
2. **Scenario** (e.g. _"Search for film The Green Mile"_) - In VFA, this is a Java method annotated 
   with `@Scenario`.  A scenario contains 1 or more steps.
3. **Step** (e.g. _"Given a user is at the homepage"_) - A step is defined with standard Guekin BDD 
   syntax i.e. starting with _given, when, then, and OR but_.  In VFA a step is called either using
   (a) static methods (like the example above) or (b) annotating a method with `@Step`. A step 
   contains 1 or more actions.
4. **Action** (e.g. `web.open("https://www.imdb.com")`) - In VFA a lower level action class can be
   injected via the Java `@Inject` annotation ([JSR330](https://jcp.org/en/jsr/detail?id=330)).  The 
   above example injects the VFA `WebActions` action class (which contains Selenium web actions). 
   All action methods are annotated with `@Action`.  Note, actions can call other lower-level 
   actions and the _depth_ of action logging can be controlled with configuration.
   
When a test executes, all these sections are automatically timed.  In addition, screenshots are 
automatically captured for each action.
   
The `@Feature` and `@Scenario` annotations also support `id` fields so that the source code exported 
from Video First is guaranteed to stay aligned with the Video First app (even if the method name 
changes in the future).

```java
@Feature(id = 3830)
public class SearchFilms {

    @Scenario(id = 19843)
    public void search_for_film_The_Green_Mile() {
```

As the number of tests scenarios grow - it is easy to refactor into separate classes / methods. For
example, if we had more than one scenario which had the following at the top: -

```java
given("a user is at the homepage");
web.type("id=suggestion-search", "The Green Mile"); 
```

Then we could create a class e.g. `Imdb` with the following method: -

```java
import io.videofirst.vfa.Step;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Imdb {

    @Inject
    private WebActions web;

    @Step
    public void given_a_user_is_at_the_homepage() {
        web.open("https://www.imdb.com");
    }
```

To create a re-usable step method it must be annotated with the `@Step` annotation and the method 
name must start with `given_`, `when_`, `then_`, `and_` or `but_`.  Just like scenario text from 
scenario methods, the step text will be automatically generated from the method name (underscores
are converted to spaces).

This new `Imdb` class can now be injected and the `given_a_user_is_at_the_homepage` method can 
now be used into our feature class i.e.

```java
@Feature
public class SearchFilms {

    @Inject
    private WebActions web;          // low level actions
    
    @Inject
    private Imdb imdb;               // higher level

    @Scenario
    public void search_for_film_The_Green_Mile() {
        imdb.given_a_user_is_at_the_homepage();

        when("the user types the \"The Green Mile\" into the search box");
        web.type("id=suggestion-search", "The Green Mile");
        
        ...
```

This makes it reusable from multiple tests.  One disadvantage though is the fact that the method 
must start with `given_`.  An alternative approach is for the `Imdb` class to extend `Steps` which 
gives it access to methods such as `given()`, `when()` etc.  

```java
@Singleton
public class Imdb extends Steps<Imdb> {

    @Inject
    private WebActions web;

    @Step
    public Imdb a_user_is_at_the_homepage() {
        web.open("https://www.imdb.com");
        
        return this;
    }

    @Step
    public Imdb a_user_searches_for_film(String film) {
        web.type ("id=suggestion-search", film);
        web.click ("#suggestion-search-button");
        return this;                 // Returning the same classes makes this fluent
    }
```

You will notice that the `Imdb` class extends `Steps<Imdb>`.  There is also a new step method 
`a_user_searches_for_film` which does not start with e.g. `given_`.  This new method also
returns `Imdb` and and last line has `return this`.  This enables the method to be called in a 
"fluent" manner which can make the code faster and more readable.

This new method can now be used as follows: -

```java
    @Scenario
    public void search_for_film_The_Green_Mile() {
        imdb.given().a_user_is_at_the_homepage();
        
        imdb.when().a_user_searches_for_film("The Green Mile");
        
        ...
```

This method can also be written completely fluently (each method chained together) if the user
prefers: -

```java
    @Scenario
    public void search_for_film_The_Green_Mile() {
        imdb.given().a_user_is_at_the_homepage()
        
            .when().a_user_searches_for_film("The Green Mile");
        
        ...
```

The `a_user_searches_for_film` method can also be used in a variety of ways (e.g. `.and()` or 
`.then()`) making this approach more flexible (than simply prefixing a method with `given_`).

Also, note that `.when().a_user_searches_for_film("The Green Mile");` method has a par will output
the following: -

    When a user searches for film                    type ("id=suggestion-search", "The Green Mile")

The parameter value `The Green Mile` is missing from the step text on the left (although we can see 
it on the low-level action on the right).  To include the values of parameters in the step output 
we simply add the `$` parameter character to the method name as follows: -   

```java
    @Step
    public Imdb a_user_searches_for_film_$ (String film) {
        web.type ("id=suggestion-search", film);
        web.click ("#suggestion-search-button");
        return this;                 // Returning the same classes makes this fluent
    }
```

Calling this method now will output the following: -

    When a user searches for film The Green Mile     type ("id=suggestion-search", "The Green Mile")

The step text can be further customised by setting the value of the  `@Step`. This can be useful 
when step text starts with numbers (as the Java language spec doesn't allow methods starting with 
numbers).

```java
    @Step("4 users are added")
    public void four_users_are_added () {
        ...
    }
```

Again, the `$` symbol can also be used in the `@Step` annotation text to inject parameters values.

```java
      @Step("Type [ $ ] execute")
      public void type_execute (String type) {
          ...
      }
```

> Note the use of special symbols like square brackets which again wouldn't be allowed in method 
names.


### Next Steps

Please feel free to use / modify this project - if you've any questions you can contact the author
Bob Marks at [bob@videofirst.io](bob@videofirst.io).

