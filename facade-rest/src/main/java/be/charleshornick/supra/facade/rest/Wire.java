package be.charleshornick.supra.facade.rest;

import java.util.Locale;

public final class Wire {

    private Wire() { }

    public static String normalize(final String raw) {
        return (raw != null) ? raw.strip() : "";
    }

    public static String normalizeUpper(final String raw) {
        return normalize(raw).toUpperCase(Locale.ROOT);
    }
}
