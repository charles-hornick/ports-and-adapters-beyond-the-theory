package be.charleshornick.supra.character.creation;

import org.pragmatica.lang.Result;
import org.pragmatica.lang.utils.Causes;

class CharacterNameValidator {

    static Result<String> validate(final String characterName) {
        if (characterName == null || characterName.isBlank()) {
            return Result.failure(Causes.cause("Character name cannot be empty"));
        }

        return Result.ok(characterName);
    }
}
