package be.charleshornick.supra;

import be.charleshornick.supra.character.define.race.DefineRace;
import be.charleshornick.supra.character.define.race.ForLoadingRace;
import be.charleshornick.supra.character.ForLoadingSnapshot;
import be.charleshornick.supra.character.ForStoringSnapshot;
import be.charleshornick.supra.fixture.DefaultCharacterData;
import be.charleshornick.supra.fixture.RaceFixture;
import be.charleshornick.supra.fixture.SnapshotFixture;
import be.charleshornick.supra.shared.ErrorCause;
import be.charleshornick.supra.shared.character.snapshot.Action;
import be.charleshornick.supra.shared.character.snapshot.Snapshot;
import be.charleshornick.supra.shared.character.snapshot.SnapshotBuilder;
import be.charleshornick.supra.shared.profession.ProfessionName;
import be.charleshornick.supra.shared.race.RaceName;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.utils.Causes;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

@DisplayName("Defining a race should")
class TestThatDefiningRaceShould {

    private final ForStoringSnapshot forStoringSnapshot = Result::ok;
    private final ForLoadingRace forLoadingRace = name -> Option.option(RaceFixture.get(name));

    private void assertEqualsAgainst(Snapshot snapshot, Snapshot expected) {
        assertThat(snapshot)
                .usingRecursiveComparison()
                .ignoringFields("shotAt")
                .withEqualsForFields(Object::equals, "name", "race", "profession", "creationPoints", "version", "action")
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Succeed when no profession is defined")
    void succeedWhenNoProfessionIsDefined() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(SnapshotFixture.getDefaultOne());

        final var expected = SnapshotFixture.getWithRace(RaceName.ELF);

        new DefineRace(forLoadingSnapshot, this.forStoringSnapshot, this.forLoadingRace)
                .named(RaceName.ELF)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(snapshot -> assertEqualsAgainst(snapshot, expected))
                .onFailure(cause -> fail("Failed to define new race: "+ cause.message()));
    }

    @Test
    @DisplayName("Keep the already defined profession when race is not forbidden")
    void keepAlreadyDefinedProfessionWhenRaceIsNotForbidden() {
        final var baseSnapshot = SnapshotFixture.getWithProfession(ProfessionName.AVENTURIER);
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(baseSnapshot);

        final var expected = SnapshotBuilder
                .basedOnPreviousSnapshot(baseSnapshot)
                .updateRaceWith(RaceFixture.elf())
                .getForAction(Action.DEFINE_RACE);

        new DefineRace(forLoadingSnapshot, this.forStoringSnapshot, this.forLoadingRace)
                .named(RaceName.ELF)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(snapshot -> assertEqualsAgainst(snapshot, expected))
                .onFailure(cause -> fail("Failed to define new race: "+ cause.message()));
    }

    @Test
    @DisplayName("Reset the already defined profession when race is forbidden")
    void resetAlreadyDefinedProfessionWhenRaceIsForbidden() {
        final var baseSnapshot = SnapshotFixture.getWithProfession(ProfessionName.AVENTURIER_ELFE);
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(baseSnapshot);

        final var expected = SnapshotBuilder
                .basedOnPreviousSnapshot(SnapshotFixture.getWithProfession(ProfessionName.UNDEFINED))
                .updateRaceWith(RaceFixture.dwarf())
                .getForAction(Action.DEFINE_RACE);

        new DefineRace(forLoadingSnapshot, this.forStoringSnapshot, this.forLoadingRace)
                .named(RaceName.DWARF)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(snapshot -> assertEqualsAgainst(snapshot, expected))
                .onFailure(cause -> fail("Failed to define new race: "+ cause.message()));
    }

    @Test
    @DisplayName("Reset the already defined profession when too many creation points are consumed")
    void resetAlreadyDefinedProfessionWhenTooManyCreationPointsAreConsumed() {
        final var baseSnapshot = SnapshotFixture.getWithProfession(ProfessionName.AVENTURIER);
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(baseSnapshot);

        final var expected = SnapshotBuilder
                .basedOnPreviousSnapshot(baseSnapshot)
                .updateRaceWith(RaceFixture.dwarf())
                .getForAction(Action.DEFINE_RACE);

        new DefineRace(forLoadingSnapshot, this.forStoringSnapshot, this.forLoadingRace)
                .named(RaceName.DWARF)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(snapshot -> assertEqualsAgainst(snapshot, expected))
                .onFailure(cause -> fail("Failed to define new race: " + cause.message()));
    }

    @ParameterizedTest(name = "{index} > Defining {0} should left {1} creation points")
    @MethodSource("getRacesAndExpectedCreationPointsLeft")
    @DisplayName("Consume the right amount of creation points")
    void consumeTheRightAmountOfCreationPoints(final RaceName raceName, final int creationPointsLeft) {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(SnapshotFixture.getDefaultOne());

        new DefineRace(forLoadingSnapshot, this.forStoringSnapshot, this.forLoadingRace)
                .named(raceName)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(snapshot -> assertThat(snapshot)
                            .extracting(
                                    Snapshot::name,
                                    snap -> snap.race().name(),
                                    snap -> snap.creationPoints().getPointsLeft())
                            .doesNotContainNull()
                            .containsExactly(DefaultCharacterData.NAME, raceName, creationPointsLeft)
                )
                .onFailure(cause -> fail("Failed to define new race: "+ cause.message()));
    }

    private static Stream<Arguments> getRacesAndExpectedCreationPointsLeft() {
        return Stream.of(
                Arguments.of(RaceName.DWARF, 8),
                Arguments.of(RaceName.HUMAN, 12),
                Arguments.of(RaceName.HALF_ELF, 10),
                Arguments.of(RaceName.HIGH_ELF, 4)
        );
    }

    @Test
    @DisplayName("Set race as undefined when given race is undefined")
    void setRaceAsUndefinedWhenGivenRaceIsUndefined() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(SnapshotFixture.getWithRace(RaceName.DWARF));

        new DefineRace(forLoadingSnapshot, this.forStoringSnapshot, this.forLoadingRace)
                .named(RaceName.UNDEFINED)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(snapshot -> assertEqualsAgainst(snapshot, SnapshotFixture.getOneWithRaceReinitialized()))
                .onFailure(name -> fail("Defining null race should succeed. Snapshot: "+ name));
    }

    @Test
    @DisplayName("Fail when race is null")
    void failWhenRaceIsNull() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(SnapshotFixture.getDefaultOne());

        new DefineRace(forLoadingSnapshot, this.forStoringSnapshot, this.forLoadingRace)
                .named(null)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(name -> fail("Defining null race should not succeed. Name: "+ name))
                .onFailure(cause -> assertThat(cause).isEqualTo(Causes.composite(Result.failure(ErrorCause.RACE_DOES_NOT_EXIST))));
    }

    @Test
    @DisplayName("fail when no character's name is specified")
    void failWhenNoCharacterNameIsSpecified() {
        final ForLoadingSnapshot forLoadingSnapshot = name -> (StringUtils.isBlank(name)) ? Option.empty() : Option.some(SnapshotFixture.getDefaultOne());

        new DefineRace(forLoadingSnapshot, this.forStoringSnapshot, this.forLoadingRace)
                .named(RaceName.ELF)
                .toCharacterNamed(null)
                .onSuccess(name -> fail("Defining null race should not succeed. Name: "+ name))
                .onFailure(cause -> assertThat(cause).isEqualTo(Causes.composite(Result.failure(ErrorCause.CHARACTER_DOES_NOT_EXIST))));

        new DefineRace(forLoadingSnapshot, this.forStoringSnapshot, this.forLoadingRace)
                .named(RaceName.ELF)
                .toCharacterNamed("")
                .onSuccess(name -> fail("Defining null race should not succeed. Name: "+ name))
                .onFailure(cause -> assertThat(cause).isEqualTo(Causes.composite(Result.failure(ErrorCause.CHARACTER_DOES_NOT_EXIST))));
    }

    @Test
    @DisplayName("Fail when no character's name is specified")
    void failWhenCharacterNameDoesNotExist() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.none();

        new DefineRace(forLoadingSnapshot, this.forStoringSnapshot, this.forLoadingRace)
                .named(RaceName.ELF)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(name -> fail("Defining null race should not succeed. Name: "+ name))
                .onFailure(cause -> assertThat(cause).isEqualTo(Causes.composite(Result.failure(ErrorCause.CHARACTER_DOES_NOT_EXIST))));
    }
}
