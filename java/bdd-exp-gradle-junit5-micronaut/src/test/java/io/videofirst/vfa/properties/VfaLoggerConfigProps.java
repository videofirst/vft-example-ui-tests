package io.videofirst.vfa.properties;

import static java.util.Arrays.asList;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Context;
import io.videofirst.vfa.enums.VfaLogLevel;
import io.videofirst.vfa.exceptions.VfaException;
import io.videofirst.vfa.properties.model.VfaTheme;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.Data;

/**
 * Configuration associated with logging.
 */
@Data
@ConfigurationProperties("vfa.logger")
@Context
public class VfaLoggerConfigProps {

    // Injected config

    private VfaLogLevel level;
    private String theme;
    private int actionDepth;
    private int rightColumnChars;
    private int indentChars;
    private List<String> ignorePrefixes = asList("web");
    private Map<String, Map<String, Object>> themes;

    // Other config

    private Map<String, VfaTheme> vfaThemes;

    @PostConstruct
    public void init() {
        // Validate current selected theme actually exists
        if (!themes.containsKey(theme)) {
            throw new VfaException("Theme [ " + theme + " ] doesn't exist - please pick a valid theme!");
        }
        this.vfaThemes = themes.entrySet().stream()
            .map(rawTheme -> VfaTheme.parse(rawTheme.getKey(), rawTheme.getValue()))
            .collect(Collectors.toMap(e -> e.getName(), e -> e));
    }

    public VfaTheme getCurrentTheme() {
        return vfaThemes.get(theme);
    }

}