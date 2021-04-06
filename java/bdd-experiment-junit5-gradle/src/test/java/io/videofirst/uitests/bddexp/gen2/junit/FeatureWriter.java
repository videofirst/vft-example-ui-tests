package io.videofirst.uitests.bddexp.gen2.junit;

/**
 * @author Bob Marks
 */
public class FeatureWriter extends BasicFeatureWriter {
//    FeatureWriter(FeatureDetails storyDetails) {
//        super(storyDetails);
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    protected void writeScenarios(JSONArray scenarios, Json report) throws Exception {
//        for (String key : storyDetails.getStore().keySet()) {
//            Scene scene = (Scene) storyDetails.getStore().get(key);
//
//            Json scenario = new Json();
//            scenario.put(Constants.METHOD_NAME, scene.getMethodName());
//            scenario.put(Constants.NAME, scene.getDescription());
//
//            if (scene.given().length() > 0) {
//                Json given = new Json();
//                given.put(Constants.NAME, scene.given());
//                addAnds(scene.givenAnds(), given);
//
//                scenario.put(Constants.GIVEN, given);
//            }
//
//            if (scene.when().length() > 0) {
//                Json when = new Json();
//                when.put(Constants.NAME, scene.when());
//
//                scenario.put(Constants.WHEN, when);
//            }
//
//            Json then = new Json();
//            then.put(Constants.NAME, scene.then());
//            addAnds(scene.thenAnds(), then);
//
//            scenario.put(Constants.THEN, then);
//
//            scenarios.add(scenario);
//        }
//    }
}
