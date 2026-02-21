package be.charleshornick.supra.character.creation;

import be.charleshornick.supra.shared.character.snapshot.SnapshotBuilder;
import org.pragmatica.lang.Result;

public class CreateCharacter {

    private final ToStoreCharacter toStoreCharacter;

    public CreateCharacter(final ToStoreCharacter toStoreCharacter) {
        this.toStoreCharacter = toStoreCharacter;
    }

    public Result<CharacterCreated> named(final String characterName) {
        return CharacterNameValidator
                .validate(characterName)
                .flatMap(this.toStoreCharacter::store)
                .map(SnapshotBuilder::asFirstOne)
                .map(CharacterCreated::new);
    }
}
