package be.charleshornick.supra.scenario;

import be.charleshornick.supra.ErrorCause;
import be.charleshornick.supra.create.CreateCharacter;
import be.charleshornick.supra.fixture.DefaultCharacterData;
import be.charleshornick.supra.fixture.SnapshotFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

@DisplayName("Creating a new character should")
public interface CreatingNewCharacterShould {

    CreateCharacter createCharacter(NameUnicityPreset preset);

    @Test
    @DisplayName("Succeed when the name is available")
    default void succeedWhenNameIsFreeToUse() {
        createCharacter(NameUnicityPreset.NAME_AVAILABLE)
                .named(DefaultCharacterData.NAME)
                .onSuccess(snapshot -> assertThat(snapshot).isEqualTo(SnapshotFixture.getDefaultOne()))
                .onFailure(cause -> fail("Cannot create new character: "+ cause.message()));
    }

    @Test
    @DisplayName("Failed when the name is already taken")
    default void failedWhenNameIsAlreadyTaken() {
        createCharacter(NameUnicityPreset.NAME_NOT_AVAILABLE)
                .named(DefaultCharacterData.NAME)
                .onFailure(cause -> assertThat(cause).isEqualTo(ErrorCause.NAME_ALREADY_TAKEN))
                .onSuccess(snapshot -> fail("Cannot create new character: "+ snapshot));
    }

    @Test
    @DisplayName("Failed when name is empty")
    default void failWhenNameIsEmpty() {
        createCharacter(NameUnicityPreset.NAME_AVAILABLE)
                .named("")
                .onFailure(cause -> assertThat(cause).isEqualTo(ErrorCause.NAME_EMPTY_VALUE))
                .onSuccess(snapshot -> fail("The creation should have fail. Name is " + snapshot));

        createCharacter(NameUnicityPreset.NAME_AVAILABLE)
                .named(null)
                .onFailure(cause -> assertThat(cause).isEqualTo(ErrorCause.NAME_EMPTY_VALUE))
                .onSuccess(snapshot -> fail("The creation should have fail. Name is " + snapshot));
    }
}
