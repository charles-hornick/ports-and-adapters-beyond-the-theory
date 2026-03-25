package be.charleshornick.supra.create;

import be.charleshornick.supra.ErrorCause;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Verify;

class CharacterNameValidator {

    private final ForCheckingNameUnicity nameUnicityChecker;

    CharacterNameValidator(final ForCheckingNameUnicity nameUnicityChecker) {
        this.nameUnicityChecker = nameUnicityChecker;
    }

    Result<String> validate(final String name) {
        return Verify.ensure(name, Verify.Is::notNull, ErrorCause.NAME_EMPTY_VALUE)
                .filter(ErrorCause.NAME_EMPTY_VALUE, Verify.Is::notBlank)
                .filter(ErrorCause.NAME_ALREADY_TAKEN, this.nameUnicityChecker::isAvailable);
    }
}
