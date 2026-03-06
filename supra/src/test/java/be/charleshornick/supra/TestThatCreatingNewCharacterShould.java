package be.charleshornick.supra;

import be.charleshornick.supra.character.creation.CreateCharacter;
import be.charleshornick.supra.fixture.DefaultCharacterData;
import be.charleshornick.supra.fixture.SnapshotFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pragmatica.lang.Result;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Creating a new character should")
public class TestThatCreatingNewCharacterShould {

    @Test
    @DisplayName("Succeed when the name is available")
    void succeedWhenNameIsFreeToUse() {
        new CreateCharacter(_ -> true, Result::ok)
                .named(DefaultCharacterData.NAME)
                .onSuccess(snapshot -> assertThat(snapshot).isEqualTo(SnapshotFixture.getDefaultOne()))
                .onFailure(cause -> fail("Cannot create new character: "+ cause.message()));
    }

    @Test
    @DisplayName("Failed when the name is already taken")
    void failedWhenNameIsAlreadyTaken() {
        new CreateCharacter(_ -> false, Result::ok)
                .named(DefaultCharacterData.NAME)
                .onFailure(cause -> assertThat(cause).isEqualTo(ErrorCause.NAME_ALREADY_TAKEN))
                .onSuccess(snapshot -> fail("Cannot create new character: "+ snapshot));
    }

    @Test
    @DisplayName("Failed when name is empty")
    void failWhenNameIsEmpty() {
        new CreateCharacter(_ -> true, Result::ok)
                .named("")
                .onFailure(cause -> assertThat(cause).isEqualTo(ErrorCause.NAME_EMPTY_VALUE))
                .onSuccess(snapshot -> fail("The creation should have fail. Name is " + snapshot));

        new CreateCharacter(_ -> true, Result::ok)
                .named(null)
                .onFailure(cause -> assertThat(cause).isEqualTo(ErrorCause.NAME_EMPTY_VALUE))
                .onSuccess(snapshot -> fail("The creation should have fail. Name is " + snapshot));
    }
}
