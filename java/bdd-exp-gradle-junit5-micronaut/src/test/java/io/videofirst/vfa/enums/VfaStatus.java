package io.videofirst.vfa.enums;

/**
 * VFA status.  Trying to keep this fairly simple / small number of errors.
 *
 * @author Bob Marks
 */
public enum VfaStatus {

    // FIXME - should symbols be in properties ????
    passed("√", "✔"),  // (green) no problems
    failed("X", "✘"),  // (yellow) assertion error
    error("!", "❗"),     // (red) exception was thrown
    ignored("", "");   // (grey) e.g. @Disabled or aborted (if assumeTrue was false (Intelli-J treats this as ignored)

    private String standardSymbol;
    private String unicodeSymbol;

    VfaStatus(String standardSymbol, String unicodeSymbol) {
        this.standardSymbol = standardSymbol;
        this.unicodeSymbol = unicodeSymbol;
    }

    public String getSymbol(boolean unicode) {
        return unicode ? unicodeSymbol : standardSymbol;
    }

    public boolean isErrorOrFail() {
        return this == error || this == failed;
    }

}
