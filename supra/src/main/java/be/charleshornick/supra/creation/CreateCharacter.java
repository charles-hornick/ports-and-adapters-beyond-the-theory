package be.charleshornick.supra.creation;

import be.charleshornick.supra.ForStoringSnapshot;
import be.charleshornick.supra.snapshot.Snapshot;
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
