# BDD Experiment using Gradle, Junit5 + Micronaut.

# Thoughts on 25th July 2021

This experiment is a continuation of the experiments done in `bdd-experiment-junit5-gradle` - this 
time around we've changed focus slightly i.e.

1. *Move from VFA DSL to pure Java* - creating a useable DSL is a massive amount of work and there 
   is a risk that a lot of people may not be interested.  The focus has (for now) changed to a pure
   Java solution, but one which is VERY easy and quite opinionated.  The main advantage here is 
   the total power of Java.  The idea would be similar to e.g. JGiven, but much lighter i.e. not 
   forcing creating `Given`, `When`, `Then` classes but rather creating much lighter "custom" action
   classes e.g. `ImdbActions`.
    
2. *Light-weight DI Framework* - Using a DI framework can bring many advantages to coding including
   (1) easy management / injection of action classes, (2) AOP capabilities which can massively
    simplify test code and (3) advanced configuration capabilities as standard.  Of course, Spring
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
   
5. *Output as DSL* - even though we're not using a DSL - the output will be the same. Means in the 
   future we may provide that as an option. 

### Next Steps

Please feel free to use / modify this project - if you've any questions you can contact the author
Bob Marks at [bob@videofirst.io](bob@videofirst.io).

