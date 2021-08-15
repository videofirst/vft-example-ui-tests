package io.videofirst.vfa;

/**
 * Steps base class - add this to a Steps class so its methods can be chained together.
 */
public abstract class Steps<SELF extends Steps<?>> {

    // Given

    public SELF given() {
        Vfa.given();
        return self();
    }

    public SELF given(String text) {
        Vfa.given(text);
        return self();
    }

    // When

    public SELF when() {
        Vfa.when();
        return self();
    }

    public SELF when(String text) {
        Vfa.when(text);
        return self();
    }

    // Then

    public SELF then() {
        Vfa.then();
        return self();
    }

    public SELF then(String text) {
        Vfa.then(text);
        return self();
    }

    // And

    public SELF and() {
        Vfa.and();
        return self();
    }

    public SELF and(String text) {
        Vfa.and(text);
        return self();
    }

    // But

    public SELF but() {
        Vfa.but();
        return self();
    }

    public SELF but(String text) {
        Vfa.but(text);
        return self();
    }

    // Private methods

    @SuppressWarnings("unchecked")
    private SELF self() {
        return (SELF) this;
    }

}