package io.videofirst.uitests.bddexp.gen2.junit;

/**
 * @author Bob Marks
 */
public class BasicFeatureWriter {

//    /**
//     * Facilitates the clean-up and creation of {@link Constants#BDD_REPORTS_FOLDER} folder under
//     * the test execution's target or build directory.
//     */
//    private static class BDDReportsFolderInitializer {
//
//        private static BDDReportsFolderInitializer ME;
//
//        static synchronized BDDReportsFolderInitializer get(Class<?> anyTestClass) {
//            if (ME == null) {
//                ME = new BDDReportsFolderInitializer(anyTestClass);
//            }
//
//            return ME;
//        }
//
//        private final File bddReports;
//
//        // TODO: figure out a cleaner way to get the "target" or "build" directory
//        //       that can handle IDE quirks.
//        private BDDReportsFolderInitializer(Class<?> anyTestClass) {
//            URL url = anyTestClass.getResource("");
//
//            if (url == null) {
//                throw new RuntimeException("Unable to get the root resource folder.");
//            }
//
//            File classParentFile = new File(url.getPath());
//            int packageFolderCount = anyTestClass.getPackage().getName().split("\\.").length;
//
//            File classesFolder = classParentFile;
//            for (int c = 0; c < packageFolderCount; c++) {
//                classesFolder = classesFolder.getParentFile();
//            }
//
//            File target = classesFolder.getParentFile();
//
//            Set<String> knownTargetDirs = new HashSet<>();
//            knownTargetDirs.add("target");
//            knownTargetDirs.add("build");
//            knownTargetDirs.add("out");
//
//            // Try to handle maven/gradle/IDEs quirks.
//            if (!knownTargetDirs.contains(target.getName())) {
//                // One more attempt to see if the parent is any good.
//                File targetParent = target.getParentFile();
//                if (knownTargetDirs.contains(targetParent.getName())) {
//                    target = targetParent;
//                }
//            }
//
//            bddReports = new File(target, Constants.BDD_REPORTS_FOLDER);
//
//            if (bddReports.exists()) {
//                try {
//                    Files.walk(bddReports.toPath(), FileVisitOption.FOLLOW_LINKS)
//                        .sorted(Comparator.reverseOrder())
//                        .map(Path::toFile)
//                        .forEach(File::delete);
//
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//
//            if (!bddReports.mkdir()) {
//                throw new RuntimeException("Unable to create the folder for saving bdd reports.");
//            }
//        }
//    }
//
//    protected final StoryDetails storyDetails;
//
//    private final BDDReportsFolderInitializer initializer;
//    private final File classFile;
//
//    public BasicStoryWriter(StoryDetails storyDetails) {
//        this.storyDetails = storyDetails;
//        this.initializer = BDDReportsFolderInitializer.get(storyDetails.getStoryClass());
//
//        try {
//            classFile = new File(initializer.bddReports,
//                storyDetails.getClassName() + Constants.EXT);
//
//            if (!classFile.createNewFile()) {
//                throw new RuntimeException("Unable to create the class file for bdd report: "
//                    + storyDetails.getClassName());
//            }
//
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    public void write() throws Exception {
//
//        Json report = new Json();
//        report.put(Constants.CLASS_NAME, storyDetails.getClassName());
//        report.put(Constants.NAME, storyDetails.getName());
//        report.put(Constants.DESCRIPTION, storyDetails.getDescription());
//
//        JSONArray scenarios = new JSONArray();
//        report.put(Constants.SCENARIOS, scenarios);
//
//        writeScenarios(scenarios, report);
//
//        try (BufferedWriter writer = Files.newBufferedWriter(classFile.toPath())) {
//            writer.write(report.toJSONString());
//
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//
//        // TODO: Create a text report only when the caller asks for it - use sys variable.
//        new TextWriter().write(report, initializer.bddReports);
//    }
//
//    protected abstract void writeScenarios(JSONArray scenarios, Json report) throws Exception;
//
//    @SuppressWarnings("unchecked")
//    protected void addAnds(List<String> ands, JSONObject jsonObject) throws IOException {
//        if (!ands.isEmpty()) {
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.addAll(ands);
//
//            jsonObject.put(Constants.ANDS, jsonArray);
//        }
//    }
}
