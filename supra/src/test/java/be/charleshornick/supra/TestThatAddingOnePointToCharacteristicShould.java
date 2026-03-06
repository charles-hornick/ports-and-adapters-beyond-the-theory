package be.charleshornick.supra;

import be.charleshornick.supra.character.ForLoadingSnapshot;
import be.charleshornick.supra.character.ForStoringSnapshot;
import be.charleshornick.supra.character.define.characteristic.DefineCharacteristic;
import be.charleshornick.supra.fixture.DefaultCharacterData;
import be.charleshornick.supra.fixture.SnapshotFixture;
import be.charleshornick.supra.shared.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.shared.race.RaceName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DisplayName("Adding a point to primary characteristic should")
class TestThatAddingOnePointToCharacteristicShould {

    private final ForStoringSnapshot forStoringSnapshot = Result::ok;

    @Test
    @DisplayName("Fail when no race is defined")
    void failWhenNoRaceIsDefined() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(SnapshotFixture.getDefaultOne());

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byAddingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.COURAGE)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(_ -> fail("Adding a point to a primary characteristic should fail when no race is defined."));
    }

    @Test
    @DisplayName("Succeed when no maximum is exceeded (non high race)")
    void succeedWhenNoMaximumIsExceeded() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(
                SnapshotFixture.getWithRaceAndInvestedPointIn(
                        RaceName.HUMAN,
                        Map.of(
                                PrimaryCharacteristicName.COURAGE, 4,
                                PrimaryCharacteristicName.WILLPOWER, 2,
                                PrimaryCharacteristicName.CHARISMA, 2
                        )
                )
        );

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byAddingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.COURAGE)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onFailure(cause -> fail("Failed to add a point to COURAGE: "+ cause.message()))
                .onSuccess(snapshot -> {
                    final var courageValue = snapshot.investedPoints().get(PrimaryCharacteristicName.COURAGE);

                    assertThat(courageValue)
                            .as("Courage should go from %d to %d", 4, 5)
                            .isEqualTo(5);
                });
    }

    @Test
    @DisplayName("Succeed when no maximum is exceeded (high race)")
    void succeedWhenNoMaximumIsExceededWithHighRace() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(
                SnapshotFixture.getWithRaceAndInvestedPointIn(
                        RaceName.HIGH_HUMAN,
                        Map.of(
                                PrimaryCharacteristicName.PERCEPTION, 6,
                                PrimaryCharacteristicName.DEXTERITY, 2,
                                PrimaryCharacteristicName.STRENGTH, 2
                        )
                )
        );

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byAddingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.PERCEPTION)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onFailure(cause -> fail("Failed to add a point to COURAGE: "+ cause.message()))
                .onSuccess(snapshot -> {
                    final var courageValue = snapshot.investedPoints().get(PrimaryCharacteristicName.PERCEPTION);

                    assertThat(courageValue)
                            .as("Courage should go from %d to %d", 6, 7)
                            .isEqualTo(7);
                });
    }

    @Test
    @DisplayName("Fail when max of 5 points is exceeded for specific characteristic (non high race)")
    void failWhenMaxOf5PointsIsReachedForSpecificCharacteristicWithNonHighRace() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(
                SnapshotFixture.getWithRaceAndInvestedPointIn(
                        RaceName.HUMAN,
                        Map.of(PrimaryCharacteristicName.COURAGE, 5)
                )
        );

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byAddingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.COURAGE)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(_ -> fail("Adding a point to a primary characteristic should fail when no race is defined."));
    }

    @Test
    @DisplayName("Fail when max of 9 points is exceeded for specific group of characteristics (non high race)")
    void failWhenMaxOf9PointsIsReachedForSpecificGroupOfCharacteristics() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(
                SnapshotFixture.getWithRaceAndInvestedPointIn(
                        RaceName.HUMAN,
                        Map.of(
                                PrimaryCharacteristicName.COURAGE, 5,
                                PrimaryCharacteristicName.WILLPOWER, 2,
                                PrimaryCharacteristicName.CHARISMA, 2
                        )
                )
        );

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byAddingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.CHARISMA)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(_ -> fail("Adding a point to a primary characteristic should fail when no race is defined."));
    }

    @Test
    @DisplayName("Fail when max of 7 points is exceed for specific characteristic (high race)")
    void failWhenMaxOf7PointsIsReachedForSpecificCharacteristicWithHighRace() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(
                SnapshotFixture.getWithRaceAndInvestedPointIn(
                        RaceName.HIGH_HUMAN,
                        Map.of(PrimaryCharacteristicName.COURAGE, 7)
                )
        );

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byAddingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.COURAGE)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(_ -> fail("Adding a point to a primary characteristic should fail when no race is defined."));
    }

    @Test
    @DisplayName("Fail when max of 12 points is exceed for specific group of characteristics (high race)")
    void failWhenMaxOf12PointsIsReachedForSpecificGroupOfCharacteristicsWithHighRace() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(
                SnapshotFixture.getWithRaceAndInvestedPointIn(
                        RaceName.HIGH_HUMAN,
                        Map.of(
                                PrimaryCharacteristicName.COURAGE, 7,
                                PrimaryCharacteristicName.WILLPOWER, 3,
                                PrimaryCharacteristicName.CHARISMA, 2
                        )
                )
        );

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byAddingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.CHARISMA)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(_ -> fail("Adding a point to a primary characteristic should fail when no race is defined."));
    }

    @Test
    @DisplayName("Fail when max of 30 points is exceed for all characteristics (high race)")
    void failWhenMaxOf30PointsIsReachedForAllCharacteristicsWithHighRace() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(
                SnapshotFixture.getWithRaceAndInvestedPointIn(
                        RaceName.HIGH_HUMAN,
                        Map.of(
                                PrimaryCharacteristicName.COURAGE, 7,
                                PrimaryCharacteristicName.WILLPOWER, 2,
                                PrimaryCharacteristicName.CHARISMA, 2,
                                PrimaryCharacteristicName.BEAUTY, 2,
                                PrimaryCharacteristicName.CONSTITUTION, 5,
                                PrimaryCharacteristicName.INTELLIGENCE, 2,
                                PrimaryCharacteristicName.PERCEPTION, 2,
                                PrimaryCharacteristicName.DEXTERITY, 2,
                                PrimaryCharacteristicName.STRENGTH, 6
                        )
                )
        );

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byAddingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.BEAUTY)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(_ -> fail("Adding a point to a primary characteristic should fail when no race is defined."));
    }

    @Test
    @DisplayName("Fail when no characteristic name is given")
    void failWhenNoCharacteristicNameIsGiven() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(SnapshotFixture.getDefaultOne());

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byAddingOnePoint()
                .toCharacteristicNamed(null)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(_ -> fail("Test should fail when no characteristic name is given."));
    }

    @Test
    @DisplayName("Fail when no character name is given")
    void failWhenNoCharacterNameIsGiven() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(SnapshotFixture.getDefaultOne());

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byAddingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.COURAGE)
                .toCharacterNamed(null)
                .onSuccess(_ -> fail("Test should fail when no character name is given."));

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byAddingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.COURAGE)
                .toCharacterNamed("")
                .onSuccess(_ -> fail("Test should fail when empty character name is given."));
    }
}
