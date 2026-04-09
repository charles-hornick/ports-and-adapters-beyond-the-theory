package be.charleshornick.supra.bootstrap;

import be.charleshornick.supra.create.CreateCharacter;
import be.charleshornick.supra.scenario.CreatingNewCharacterShould;
import be.charleshornick.supra.scenario.NameUnicityPreset;
import org.pragmatica.lang.Result;

public class TestThatCreatingNewCharacterShould implements CreatingNewCharacterShould {

    @Override
    public CreateCharacter createCharacter(NameUnicityPreset preset) {
        return switch (preset) {
            case NAME_AVAILABLE -> new CreateCharacter(_ -> true, Result::ok);
            case NAME_NOT_AVAILABLE -> new CreateCharacter(_ -> false, Result::ok);
        };
    }
}
