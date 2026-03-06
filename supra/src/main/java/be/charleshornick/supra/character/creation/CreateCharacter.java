package be.charleshornick.supra.character.creation;

import be.charleshornick.supra.character.ForStoringSnapshot;
import be.charleshornick.supra.shared.character.snapshot.Snapshot;
import org.pragmatica.lang.Result;

public final class CreateCharacter {

    private final ForCheckingNameUnicity forCheckingNameUnicity;
    private final ForStoringSnapshot forStoringSnapshot;

    public CreateCharacter(final ForCheckingNameUnicity forCheckingNameUnicity, final ForStoringSnapshot forStoringSnapshot) {
        this.forCheckingNameUnicity = forCheckingNameUnicity;
        this.forStoringSnapshot = forStoringSnapshot;
    }

    public Result<Snapshot> named(final String characterName) {
        return new CharacterNameValidator(this.forCheckingNameUnicity)
                .validate(characterName)
                .map(Character::withName)
                .map(Character::doSnapshot)
                .flatMap(this.forStoringSnapshot::store);
    }
}
