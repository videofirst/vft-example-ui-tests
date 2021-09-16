package io.videofirst.vfa.aop;

import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.type.MutableArgumentValue;
import io.videofirst.vfa.AfterAction;
import io.videofirst.vfa.Alias;
import io.videofirst.vfa.BeforeAction;
import io.videofirst.vfa.ErrorAction;
import io.videofirst.vfa.enums.VfaStatus;
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

        // 1) High level Service before e.g. start logging / timings
        vfaService.before(actionModel);

        Object object = null;
        boolean isFinished = actionModel.isFinished();
        if (isFinished) {
            // Set status as skip as we're not in progress any more
            actionModel.setStatus(VfaStatus.ignored);
        } else {
            // 2) Optional Low level action class instance before method
            BeforeAction beforeAction = getBeforeAction(methodContext);
            if (beforeAction != null) {
                // fixme - error handling? Maybe re-usable method?
                beforeAction.before(actionModel);
            }

            // 3) Run this action e.g. selenium click event
            try {
                object = methodContext.proceed();

                actionModel.setStatus(VfaStatus.passed);
            } catch (Throwable e) { // catch everything
                actionModel.setStatus(VfaStatus.error);

                // FIXME check for [ AssertionError ]
                actionModel.setThrowable(e);
                ErrorAction errorAction = getErrorAction(methodContext);
                if (errorAction != null) {
                    errorAction.error(actionModel);
                }
            }

            // 4) Optional Low level action class instance after method e.g. take screenshot
            AfterAction afterAction = getAfterAction(methodContext);
            if (afterAction != null) {
                // fixme - error handling? Maybe re-usable method?
                afterAction.after(actionModel);
            }
        }

        // 5) High level Service after e.g. finish logging / timings etc
        vfaService.after(actionModel);

        actionModel.setMethodContext(null); // remove this context again and return object
        return object;
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

    private BeforeAction getBeforeAction(MethodInvocationContext<Object, Object> context) {
        if (context.getTarget() instanceof BeforeAction) {
            return (BeforeAction) context.getTarget();
        }
        //click ("#suggestion-search-button")
        return null;
    }

    private AfterAction getAfterAction(MethodInvocationContext<Object, Object> context) {
        if (context.getTarget() instanceof AfterAction) {
            return (AfterAction) context.getTarget();
        }
        return null;
    }

    private ErrorAction getErrorAction(MethodInvocationContext<Object, Object> context) {
        if (context.getTarget() instanceof ErrorAction) {
            return (ErrorAction) context.getTarget();
        }
        return null;
    }

}