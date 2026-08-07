package be.charleshornick.supra.chargen.create;

import be.charleshornick.supra.chargen.fault.ErrorCause;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Verify;

class CharacterNameRegister {

    private final ForRegisteringName nameUnicityChecker;

    CharacterNameRegister(final ForRegisteringName nameUnicityChecker) {
        this.nameUnicityChecker = nameUnicityChecker;
    }

    Result<String> registering(final String name) {
        return Verify.ensure(name, Verify.Is::notBlank, ErrorCause.NAME_EMPTY_VALUE)
                .flatMap(this.nameUnicityChecker::register)
                .map(_ -> name);
    }
}
