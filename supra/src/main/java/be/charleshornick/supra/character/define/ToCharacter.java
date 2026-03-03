package be.charleshornick.supra.character.define;

import be.charleshornick.supra.shared.character.snapshot.Snapshot;
import org.pragmatica.lang.Result;

public interface ToCharacter {
    Result<Snapshot> toCharacterNamed(String name);
}
