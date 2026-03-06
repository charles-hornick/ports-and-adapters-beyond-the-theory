package be.charleshornick.supra.character.creation;

import be.charleshornick.supra.ErrorCause;
import org.apache.commons.lang3.StringUtils;
import org.pragmatica.lang.Result;

class CharacterNameValidator {

    private final ForCheckingNameUnicity nameUnicityChecker;

    CharacterNameValidator(final ForCheckingNameUnicity nameUnicityChecker) {
        this.nameUnicityChecker = nameUnicityChecker;
    }

    Result<String> validate(final String name) {
        if (StringUtils.isNotBlank(name)) {
            return (this.nameUnicityChecker.isAvailable(name))
                    ? Result.success(name)
                    : Result.failure(ErrorCause.NAME_ALREADY_TAKEN);
        }
        return Result.failure(ErrorCause.NAME_EMPTY_VALUE);
    }
}
