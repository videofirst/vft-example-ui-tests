package io.videofirst.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bob Marks
 */
public class StacktraceTest {

    private static final String[] STACKTRACE = {"org.junit.jupiter.api.AssertionUtils.fail(AssertionUtils.java:55)",
        "org.junit.jupiter.api.AssertTrue.assertTrue(AssertTrue.java:40)",
        "org.junit.jupiter.api.AssertTrue.assertTrue(AssertTrue.java:35)",
        "org.junit.jupiter.api.Assertions.assertTrue(Assertions.java:162)",
        "io.videofirst.basic.ErrorActions.fail(ErrorActions.java:21)",
        "io.videofirst.basic.$ErrorActionsDefinition$Intercepted.$$access$$fail(Unknown Source)",
        "io.videofirst.basic.$ErrorActionsDefinition$$exec2.invokeInternal(Unknown Source)",
        "io.micronaut.context.AbstractExecutableMethod.invoke(AbstractExecutableMethod.java:151)",
        "io.micronaut.aop.chain.MethodInterceptorChain.proceed(MethodInterceptorChain.java:87)",
        "io.videofirst.vfa.aop.VfaActionInterceptor.intercept(VfaActionInterceptor.java:47)",
        "io.micronaut.aop.chain.MethodInterceptorChain.proceed(MethodInterceptorChain.java:96)",
        "io.videofirst.basic.$ErrorActionsDefinition$Intercepted.fail(Unknown Source)",
        "io.videofirst.basic.ErrorTest.actionFail(ErrorTest.java:51)",
        "sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)",
        "sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)",
        "sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)",
        "java.lang.reflect.Method.invoke(Method.java:498)",
        "org.junit.platform.commons.util.ReflectionUtils.invokeMethod(ReflectionUtils.java:688)",
        "org.junit.jupiter.engine.execution.MethodInvocation.proceed(MethodInvocation.java:60)",
        "org.junit.jupiter.engine.execution.InvocationInterceptorChain$ValidatingInvocation.proceed(InvocationInterceptorChain.java:131)",
        "org.junit.jupiter.api.extension.InvocationInterceptor.interceptTestMethod(InvocationInterceptor.java:117)",
        "org.junit.jupiter.engine.execution.ExecutableInvoker$ReflectiveInterceptorCall.lambda$ofVoidMethod$0(ExecutableInvoker.java:115)",
        "org.junit.jupiter.engine.execution.ExecutableInvoker.lambda$invoke$0(ExecutableInvoker.java:105)",
        "org.junit.jupiter.engine.execution.InvocationInterceptorChain$InterceptedInvocation.proceed(InvocationInterceptorChain.java:106)",
        "org.junit.jupiter.engine.extension.TimeoutExtension.intercept(TimeoutExtension.java:149)",
        "org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestableMethod(TimeoutExtension.java:140)",
        "org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestMethod(TimeoutExtension.java:84)",
        "org.junit.jupiter.engine.execution.ExecutableInvoker$ReflectiveInterceptorCall.lambda$ofVoidMethod$0(ExecutableInvoker.java:115)",
        "org.junit.jupiter.engine.execution.ExecutableInvoker.lambda$invoke$0(ExecutableInvoker.java:105)",
        "org.junit.jupiter.engine.execution.InvocationInterceptorChain$InterceptedInvocation.proceed(InvocationInterceptorChain.java:106)",
        "org.junit.jupiter.engine.execution.InvocationInterceptorChain.proceed(InvocationInterceptorChain.java:64)",
        "org.junit.jupiter.engine.execution.InvocationInterceptorChain.chainAndInvoke(InvocationInterceptorChain.java:45)",
        "org.junit.jupiter.engine.execution.InvocationInterceptorChain.invoke(InvocationInterceptorChain.java:37)",
        "org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:104)",
        "org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:98)",
        "org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.lambda$invokeTestMethod$6(TestMethodTestDescriptor.java:210)",
        "org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)",
        "org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.invokeTestMethod(TestMethodTestDescriptor.java:206)",
        "org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:131)",
        "org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:65)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:139)",
        "org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:129)",
        "org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:127)",
        "org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:126)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:84)",
        "java.util.ArrayList.forEach(ArrayList.java:1257)",
        "org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:38)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:143)",
        "org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:129)",
        "org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:127)",
        "org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:126)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:84)",
        "java.util.ArrayList.forEach(ArrayList.java:1257)",
        "org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:38)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:143)",
        "org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:129)",
        "org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:127)",
        "org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:126)",
        "org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:84)",
        "org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.submit(SameThreadHierarchicalTestExecutorService.java:32)",
        "org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor.execute(HierarchicalTestExecutor.java:57)",
        "org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine.execute(HierarchicalTestEngine.java:51)",
        "org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:108)",
        "org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:88)",
        "org.junit.platform.launcher.core.EngineExecutionOrchestrator.lambda$execute$0(EngineExecutionOrchestrator.java:54)",
        "org.junit.platform.launcher.core.EngineExecutionOrchestrator.withInterceptedStreams(EngineExecutionOrchestrator.java:67)",
        "org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:52)",
        "org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:96)",
        "org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:75)",
        "com.intellij.junit5.JUnit5IdeaTestRunner.startRunnerWithArgs(JUnit5IdeaTestRunner.java:69)",
        "com.intellij.rt.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:33)",
        "com.intellij.rt.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:230)",
        "com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:58)"};

    private static final String[] STRACKTRACE_IGNORE_REGEXES = {
        "org\\.junit.*",
        "com\\.intellij.*",
        "sun\\.reflect.*",
        "java\\.util\\.ArrayList.*",
        "io\\.micronaut\\.(aop|context).*",
        "io\\.videofirst\\.vfa.*",
        ".*\\.\\$\\w+\\$.*"
    };
    private static final List<Pattern> patterns = new ArrayList<>();


    public static void main(String[] args) {
        int i = 1;
        for (String regex : STRACKTRACE_IGNORE_REGEXES) {
            patterns.add(Pattern.compile("^" + regex + "$"));
        }

        for (String line : STACKTRACE) {  // 82 no filtering
            if (!ignoreStacktraceLine(line)) {
                String lineNum = (i++) + "\t";
                System.out.println(lineNum + line);
            }
        }
    }

    private static boolean ignoreStacktraceLine(String line) {
        for (Pattern ignorePrefix : patterns) {
            Matcher matcher = ignorePrefix.matcher(line);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

}
