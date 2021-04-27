package io.videofirst.vfa.annotation.vfa;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("io.videofirst.vfa.annotation.vfa.Vfa")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class VfaProcessor extends AbstractProcessor {

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // Iterate over annotations that this processor supports
        annotations.forEach(annotation -> {
            // Get annotated elements (should be classes)
            roundEnv.getElementsAnnotatedWith(annotation).forEach(element -> {

                // Get fully-qualified class name
                String qualifiedClassName = ((TypeElement) element).getQualifiedName().toString();

                // Extract package name
                int index = qualifiedClassName.lastIndexOf('.');
                String packageName = null;
                if (index > 0) {
                    packageName = qualifiedClassName.substring(0, index);
                }

                // Extract class name
                //String className = qualifiedClassName.substring(index + 1);
                String className = "SearchFeatureTest";

                // Get annotation
                Vfa vfa = element.getAnnotation(Vfa.class);

                // Construct builder for feature
                //String vfaClassName = vfa.name().isEmpty() ? className + "Feature" : vfa.name();
                try {
                    writeBuilder(className, packageName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        return true;
    }

    private void writeBuilder(String className, String packageName) throws IOException {

        // Create builder source file
        JavaFileObject builderSource = processingEnv.getFiler().createSourceFile(packageName + "." + className);

        try (PrintWriter out = new PrintWriter(builderSource.openWriter())) {

            // Write package name
            if (packageName != null) {
                out.printf("package %s;%n%n", packageName);
            }

            // Write class name
            out.printf("public class %s {%n", className);

            // Declare base object
            out.printf("  //private %s object = new %s();%n%n", className, className);

            // Write build method
            out.printf("  //public %s () {%n", className);
            out.printf("    //return object;%n");
            out.printf("  //}%n");

            // Close class
            out.print("}");
        }
    }
}
