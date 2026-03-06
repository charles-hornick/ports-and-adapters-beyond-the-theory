package be.charleshornick.supra;

import be.charleshornick.supra.define.profession.DefineProfession;
import be.charleshornick.supra.define.profession.ForLoadingProfession;
import be.charleshornick.supra.define.ForLoadingSnapshot;
import be.charleshornick.supra.fixture.DefaultCharacterData;
import be.charleshornick.supra.fixture.ProfessionFixture;
import be.charleshornick.supra.fixture.SnapshotFixture;
import be.charleshornick.supra.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.profession.ProfessionName;
import be.charleshornick.supra.race.RaceName;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

@DisplayName("Defining a profession should")
class TestThatDefiningProfessionShould {

    private final ForStoringSnapshot forStoringSnapshot = Result::ok;
    private final ForLoadingProfession forLoadingProfession = name -> Option.option(ProfessionFixture.get(name));

    @Test
    @DisplayName("Fail when not enough creation points are left")
    void failWhenNotEnoughCreationPointAreLeft() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(SnapshotFixture.getHighHuman());

        new DefineProfession(forLoadingSnapshot, forStoringSnapshot, forLoadingProfession)
                .named(ProfessionName.GUERRIER)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(_ -> fail("Should fail defining a Warrior profession to an High Human (not enough creation points)"));
    }

    @Test
    @DisplayName("Fail when the profession is an evolution type")
    void failWhenTheProfessionIsAnEvolutionType() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(SnapshotFixture.getHighHuman());

        new DefineProfession(forLoadingSnapshot, forStoringSnapshot, forLoadingProfession)
                .named(ProfessionName.GUERRIER)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(_ -> fail("Should fail defining a Knight profession (evolution profession cannot be define at creation)"));
    }

    @Test
    @DisplayName("Fail when race is forbidden")
    void failWhenRaceIsForbidden() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(
                SnapshotFixture.getWithRaceAndInvestedPointIn(
                        RaceName.DWARF,
                        Map.of(
                                PrimaryCharacteristicName.COURAGE, 4,
                                PrimaryCharacteristicName.WILLPOWER, 2,
                                PrimaryCharacteristicName.CHARISMA, 3,
                                PrimaryCharacteristicName.CONSTITUTION, 2,
                                PrimaryCharacteristicName.STRENGTH, 3
                        )
                )
        );

        new DefineProfession(forLoadingSnapshot, forStoringSnapshot, forLoadingProfession)
                .named(ProfessionName.GUERRIER)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(_ -> fail("Should fail defining a Warrior profession to an elf (race forbidden)"));
    }

    @Test
    @DisplayName("Succeed when all prerequisites are fulfilled")
    void succeedWhenAllPrerequisitesAreFulfilled() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(
                SnapshotFixture.getWithRaceAndInvestedPointIn(
                        RaceName.HUMAN,
                        Map.of(
                                PrimaryCharacteristicName.COURAGE, 5,
                                PrimaryCharacteristicName.WILLPOWER, 2,
                                PrimaryCharacteristicName.CHARISMA, 2,
                                PrimaryCharacteristicName.CONSTITUTION, 5,
                                PrimaryCharacteristicName.STRENGTH, 5
                        )
                )
        );

        final var expected = SnapshotFixture.getHumanWarrior();

        new DefineProfession(forLoadingSnapshot, forStoringSnapshot, forLoadingProfession)
                .named(ProfessionName.GUERRIER)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onFailure(cause -> fail("Failed to define Warrior to a human: "+ cause.message()))
                .onSuccess(snapshot -> assertThat(snapshot)
                        .usingRecursiveComparison()
                        .ignoringFields("shotAt")
                        .withEqualsForFields(Object::equals, "name", "profession", "investedPoints", "creationPoints", "version", "action")
                        .isEqualTo(expected)
                );
    }

    @Test
    @DisplayName("Fail when no profession name is given")
    void failWhenNoProfessionNameIsGiven() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(SnapshotFixture.getDefaultOne());

        new DefineProfession(forLoadingSnapshot, forStoringSnapshot, forLoadingProfession)
                .named(null)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(_ -> fail("Test should fail when no profession name is given."));
    }

    @Test
    @DisplayName("Fail when no character name is given")
    void failWhenNoCharacterNameIsGiven() {
        final ForLoadingSnapshot forLoadingSnapshot = name -> (StringUtils.isBlank(name)) ? Option.empty() : Option.some(SnapshotFixture.getDefaultOne());

        new DefineProfession(forLoadingSnapshot, forStoringSnapshot, forLoadingProfession)
                .named(ProfessionName.AVENTURIER)
                .toCharacterNamed(null)
                .onSuccess(_ -> fail("Test should fail when no character name is given."));

        new DefineProfession(forLoadingSnapshot, forStoringSnapshot, forLoadingProfession)
                .named(ProfessionName.AVENTURIER)
                .toCharacterNamed("")
                .onSuccess(_ -> fail("Test should fail when empty character name is given."));
    }
}
