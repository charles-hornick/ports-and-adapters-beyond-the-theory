package be.charleshornick.supra.fault;

import org.pragmatica.lang.Cause;

public sealed interface SupraCause extends Cause {

    record RuleViolation(String rule, String detail) implements SupraCause {
        @Override public String message() { return rule + (detail != null ? "." + detail : ""); }
    }

    record InvalidInput(String field, String constraint) implements SupraCause {
        @Override public String message() { return field + "." + constraint; }
    }

    record NotFound(String resource, String id) implements SupraCause {
        @Override public String message() { return resource + ".not.found"; }
    }

    record Conflict(String resource, String detail) implements SupraCause {
        @Override public String message() { return resource + "." + detail; }
    }

    record Technical(String code, Throwable origin) implements SupraCause {
        @Override public String message() { return code; }
    }
}
