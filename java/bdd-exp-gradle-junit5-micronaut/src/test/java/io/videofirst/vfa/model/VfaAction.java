package io.videofirst.vfa.model;

import io.micronaut.aop.MethodInvocationContext;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VfaAction {

    private String className;  // should we refactor to an object? e.g. ActionClass ???

    private String methodName;

    private String alias;

    private LinkedHashMap<String, Object> params;

    private VfaStep step; // link to parent scenario

    private VfaTime time;

    @ToString.Exclude // TODO exclude from JSON as well
    private VfaAction parent; // link to parent action (if applicable)

    @ToString.Exclude
    private List<VfaAction> actions; // children actions

    @ToString.Exclude
    private MethodInvocationContext<Object, Object> methodContext;  // raw method context

    public int countParents() {
        int numOfParents = 0;  // TODO - Java8 stream might be nicer
        VfaAction curAction = this;
        while (curAction.getParent() != null) {
            numOfParents++;
            curAction = curAction.getParent();
        }
        return numOfParents;
    }

}