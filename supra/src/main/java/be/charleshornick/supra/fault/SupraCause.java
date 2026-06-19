package be.charleshornick.supra.fault;

import org.pragmatica.lang.Cause;

public sealed interface SupraCause extends Cause permits SupraCause.Business, SupraCause.Technical {

    String code();

    record Business(String code) implements SupraCause {
        @Override public String message() { return code(); }
    }

    record Technical(String code, Throwable origin) implements SupraCause {
        @Override public String message() { return code(); }
    }
}
