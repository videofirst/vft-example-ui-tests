package io.videofirst.vfa.aop;

import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.type.MutableArgumentValue;
import io.videofirst.vfa.Alias;
import io.videofirst.vfa.model.VfaAction;
import io.videofirst.vfa.service.VfaService;
import java.util.LinkedHashMap;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VfaActionInterceptor implements MethodInterceptor<Object, Object> {

    @Inject
    private VfaService vfaService;

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {

        // refactor to Java8 stream
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        for (MutableArgumentValue param : context.getParameters().values()) {
            String paramName = param.getName();
            Object paramValue = param.getValue();
            params.put(paramName, paramValue);
        }

        // Refactor to e.g. ActionClass ????
        String className = context.getDeclaringType().getName();
        String alias = getAlias(context);
        String methodName = context.getTargetMethod().getName();

        // Retrieve parent action (if applicable)
        VfaAction actionModel = VfaAction.builder()
            .className(className)
            .alias(alias)
            .methodName(methodName)
            .params(params)
            .build();

        vfaService.before(actionModel);

        // Proceed
        Object object = context.proceed();

        vfaService.after(actionModel);

        return object;
    }

    private String getAlias(MethodInvocationContext<Object, Object> context) {
        // Alias is declaring class with "...Action" postfix removed and lowercase
        Alias aliasAnnotation = context.getDeclaringType().getAnnotation(Alias.class);
        if (aliasAnnotation != null) {
            return aliasAnnotation.value().toLowerCase(); // lower case as well
        } else {
            // Generate them
            String alias = context.getDeclaringType().getSimpleName()
                .replaceAll("Actions$", "") // remove "Action" postfix
                .toLowerCase();
            return alias;
        }
    }

}