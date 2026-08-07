package be.charleshornick.supra.storage.sqlite;

import org.pragmatica.lang.Cause;

final class MappingException extends RuntimeException {
    private final Cause cause;

    MappingException(final Cause cause) {
        this.cause = cause;
    }

    Cause cause() {
        return this.cause;
    }
}
