package io.videofirst.vfa.annotation.person;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("io.videofirst.vfa.annotation.person.Builder")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@AutoService(Processor.class)
public class BuilderProcessor extends AbstractProcessor {

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
                String className = qualifiedClassName.substring(index + 1);

                // Get annotation
                Builder builder = element.getAnnotation(Builder.class);

                // Construct builder class name
                String builderClassName = builder.name().isEmpty() ? className + "Builder" : builder.name();

                // Get fields (assume that all fields have associated setters)
                // field name [String] -> field type [String]
                Map<String, String> fields = element.getEnclosedElements().stream()
                    .filter(e -> e instanceof VariableElement)
                    .collect(Collectors.toMap(e -> e.getSimpleName().toString(), e -> e.asType().toString()));

                // Write source file
                try {
                    writeBuilder(className, builderClassName, packageName, fields, builder.methodName());
                } catch (IOException ex) {
                    System.err
                        .printf("Unable to generate builder class `%s` for `%s` class", builderClassName, className);
                }
            });
        });
        return true;
    }

    private void writeBuilder(String className, String builderClassName, String packageName, Map<String, String> fields,
        String buildMethodName) throws IOException {

        // Create builder source file
        JavaFileObject builderSource = processingEnv.getFiler().createSourceFile(packageName + "." + builderClassName);

        try (PrintWriter out = new PrintWriter(builderSource.openWriter())) {

            // Write package name
            if (packageName != null) {
                out.printf("package %s;%n%n", packageName);
            }

            // Write class name
            out.printf("public class %s {%n", builderClassName);

            // Declare base object
            out.printf("  private %s object = new %s();%n%n", className, className);

            // Write setter methods
            fields.entrySet().forEach(field -> {
                String fieldName = field.getKey();
                String fieldType = field.getValue();
                String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                out.printf("  public %s %s(%s value) {%n", builderClassName, fieldName, fieldType);
                out.printf("    object.%s(value);%n", setterName);
                out.printf("    return this;%n");
                out.printf("  }%n%n");
            });

            // Write build method
            out.printf("  public %s %s() {%n", className, buildMethodName);
            out.printf("    return object;%n");
            out.printf("  }%n");

            // Close class
            out.print("}");
        }
    }
}

//@SupportedAnnotationTypes("io.videofirst.vfa.annotation.person.BuilderProperty")
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@AutoService(Processor.class)
//public class BuilderProcessor extends AbstractProcessor {
//
//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        for (TypeElement annotation : annotations) {
//
//            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
//
//            Map<Boolean, List<Element>> annotatedMethods = annotatedElements.stream().collect(Collectors.partitioningBy(
//                element -> ((ExecutableType) element.asType()).getParameterTypes().size() == 1 && element
//                    .getSimpleName().toString().startsWith("set")));
//
//            List<Element> setters = annotatedMethods.get(true);
//            List<Element> otherMethods = annotatedMethods.get(false);
//
//            otherMethods.forEach(element -> processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
//                "@BuilderProperty must be applied to a setXxx method with a single argument", element));
//
//            if (setters.isEmpty()) {
//                continue;
//            }
//
//            String className = ((TypeElement) setters.get(0).getEnclosingElement()).getQualifiedName().toString();
//
//            Map<String, String> setterMap = setters.stream().collect(Collectors
//                .toMap(setter -> setter.getSimpleName().toString(),
//                    setter -> ((ExecutableType) setter.asType()).getParameterTypes().get(0).toString()));
//
//            try {
//                writeBuilderFile(className, setterMap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        return true;
//    }
//
//    private void writeBuilderFile(String className, Map<String, String> setterMap) throws IOException {
//
//        String packageName = null;
//        int lastDot = className.lastIndexOf('.');
//        if (lastDot > 0) {
//            packageName = className.substring(0, lastDot);
//        }
//
//        String simpleClassName = className.substring(lastDot + 1);
//        String builderClassName = className + "Builder";
//        String builderSimpleClassName = builderClassName.substring(lastDot + 1);
//
//        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);
//        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
//
//            if (packageName != null) {
//                out.print("package ");
//                out.print(packageName);
//                out.println(";");
//                out.println();
//            }
//
//            out.print("public class ");
//            out.print(builderSimpleClassName);
//            out.println(" {");
//            out.println();
//
//            out.print("    private ");
//            out.print(simpleClassName);
//            out.print(" object = new ");
//            out.print(simpleClassName);
//            out.println("();");
//            out.println();
//
//            out.print("    public ");
//            out.print(simpleClassName);
//            out.println(" build() {");
//            out.println("        return object;");
//            out.println("    }");
//            out.println();
//
//            setterMap.entrySet().forEach(setter -> {
//                String methodName = setter.getKey();
//                String argumentType = setter.getValue();
//
//                out.print("    public ");
//                out.print(builderSimpleClassName);
//                out.print(" ");
//                out.print(methodName);
//
//                out.print("(");
//
//                out.print(argumentType);
//                out.println(" value) {");
//                out.print("        object.");
//                out.print(methodName);
//                out.println("(value);");
//                out.println("        return this;");
//                out.println("    }");
//                out.println();
//            });
//
//            out.println("}");
//        }
//    }
//
//}