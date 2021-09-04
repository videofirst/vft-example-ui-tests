package io.videofirst.vfa.aop;

import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.type.MutableArgumentValue;
import io.videofirst.vfa.AfterAction;
import io.videofirst.vfa.Alias;
import io.videofirst.vfa.BeforeAction;
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
    public Object intercept(MethodInvocationContext<Object, Object> methodContext) {

        // Retrieve Actions object and model
        VfaAction actionModel = getVfaAction(methodContext);

        // Life cycle is as follows: -

        // 1) High level Service before e.g. start logging /timings
        vfaService.before(actionModel);

        // 2) Optional Low level action class instance before method
        BeforeAction beforeAction = getBeforeAction(methodContext);
        if (beforeAction != null) {
            beforeAction.before(actionModel);
        }

        // 3) Run this action e.g. selenium click event
        Object object = methodContext.proceed();

        // 4) Optional Low level action class instance after method e.g. take screenshot
        AfterAction afterAction = getAfterAction(methodContext);
        if (afterAction != null) {
            afterAction.after(actionModel);
        }

        // 5) High level Service after e.g. finish logging / timings
        vfaService.after(actionModel);

        actionModel.setMethodContext(null); // remove this context again and return object
        return object;
    }

    private BeforeAction getBeforeAction(MethodInvocationContext<Object, Object> context) {
        if (context.getTarget() instanceof BeforeAction) {
            return (BeforeAction) context.getTarget();
        }
        return null;
    }

    private AfterAction getAfterAction(MethodInvocationContext<Object, Object> context) {
        if (context.getTarget() instanceof BeforeAction) {
            return (AfterAction) context.getTarget();
        }
        return null;
    }

    private VfaAction getVfaAction(MethodInvocationContext<Object, Object> methodContext) {
        // Refactor to e.g. ActionClass to make things more DRY ????
        String className = methodContext.getDeclaringType().getName();
        String alias = getAlias(methodContext);
        String methodName = methodContext.getTargetMethod().getName();
        LinkedHashMap<String, Object> params = getParams(methodContext);

        // Retrieve parent action (if applicable)
        VfaAction actionModel = VfaAction.builder()
            .className(className)
            .alias(alias)
            .methodName(methodName)
            .params(params)
            .methodContext(methodContext)
            .build();
        return actionModel;
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

    private LinkedHashMap<String, Object> getParams(MethodInvocationContext<Object, Object> context) {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        for (MutableArgumentValue param : context.getParameters().values()) {
            String paramName = param.getName();
            Object paramValue = param.getValue();
            params.put(paramName, paramValue);
        }
        return params;
    }

}