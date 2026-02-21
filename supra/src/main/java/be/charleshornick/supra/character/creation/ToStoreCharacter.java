package be.charleshornick.supra.character.creation;

import org.pragmatica.lang.Result;

public interface ToStoreCharacter {

    Result<String> store(String characterName);
}
