package io.videofirst.vfa.model;

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

    private String prefix;

    private LinkedHashMap<String, Object> params;

    private VfaStep step; // link to parent scenario

    private VfaTime time;

    @ToString.Exclude
    private VfaAction parent; // link to parent action (if applicable)

    @ToString.Exclude
    private List<VfaAction> actions; // children actions

    public int countParents() {
        int numOfParents = 0;  // TODO - Java8 stream would be nice
        VfaAction curAction = this;
        while (curAction.getParent() != null) {
            numOfParents++;
            curAction = curAction.getParent();
        }
        return numOfParents;
    }

}