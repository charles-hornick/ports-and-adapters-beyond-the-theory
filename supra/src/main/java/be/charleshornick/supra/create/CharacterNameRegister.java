package be.charleshornick.supra.create;

import be.charleshornick.supra.fault.ErrorCause;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Verify;

class CharacterNameRegister {

    private final ForRegisteringName nameUnicityChecker;

    CharacterNameRegister(final ForRegisteringName nameUnicityChecker) {
        this.nameUnicityChecker = nameUnicityChecker;
    }

    Result<String> registering(final String name) {
        return Verify.ensure(name, Verify.Is::notNull, ErrorCause.NAME_EMPTY_VALUE)
                .filter(ErrorCause.NAME_EMPTY_VALUE, Verify.Is::notBlank)
                .flatMap(this.nameUnicityChecker::register)
                .map(_ -> name);
    }
}
