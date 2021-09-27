package io.videofirst.vfa.properties;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Context;
import io.micronaut.core.util.clhm.ConcurrentLinkedHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.Data;

/**
 * Configuration associated with logging.
 */
@Data
@ConfigurationProperties("vfa.exceptions")
@Context
public class VfaExceptionsProperties {

    // Injected config

    private List<String> ignores;
    private Integer linesTop;
    private Integer linesBottom;

    // Other fields

    private List<Pattern> patternIgnores;

    private ConcurrentMap<String, Boolean> ignoreExceptionLineCache = new ConcurrentLinkedHashMap.Builder<String, Boolean>()
        .maximumWeightedCapacity(10000)
        .build();

    @PostConstruct
    public void init() {
        if (this.ignores == null || this.ignores.isEmpty()) {
            return;
        }
        this.patternIgnores = new ArrayList<>();
        for (String ignore : ignores) {
            patternIgnores.add(Pattern.compile("^" + ignore + "$"));
        }
    }

    /**
     * Return filtered stack-trace from an exception (as List of String).
     */
    public List<String> getFilteredStackTrace(Throwable throwable) {
        List<String> filteredStackTrace = Arrays.stream(throwable.getStackTrace())
            .map(elm -> elm.toString())
            .filter(line -> !isIgnoreExceptionLine(line))
            .collect(Collectors.toList());
        return filteredStackTrace;
    }

    /**
     * Return true/false if this exception line is to be ignored.  Currently cached using a programmatic cache - we
     * could improve in future with a Micronaut based annotation approach.
     */
    public boolean isIgnoreExceptionLine(String line) {
        return ignoreExceptionLineCache.computeIfAbsent(line, (key) -> checkIgnorePatternForMatch(line));
    }

    // Private method

    /**
     * Iterate over each pattern and see if there is match for this line.
     */
    private boolean checkIgnorePatternForMatch(String line) {
        for (Pattern ignorePrefix : patternIgnores) {
            Matcher matcher = ignorePrefix.matcher(line);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

}