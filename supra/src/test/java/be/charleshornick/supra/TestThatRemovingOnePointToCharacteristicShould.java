package be.charleshornick.supra;

import be.charleshornick.supra.define.ForLoadingSnapshot;
import be.charleshornick.supra.define.characteristic.DefineCharacteristic;
import be.charleshornick.supra.fixture.DefaultCharacterData;
import be.charleshornick.supra.fixture.SnapshotFixture;
import be.charleshornick.supra.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.race.RaceName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DisplayName("Removing a point to primary characteristic should")
class TestThatRemovingOnePointToCharacteristicShould {

    private final ForStoringSnapshot forStoringSnapshot = Result::ok;

    @Test
    @DisplayName("Fail when no race is defined")
    void failWhenNoRaceIsDefined() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(SnapshotFixture.getDefaultOne());

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byRemovingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.COURAGE)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(_ -> fail("Removing a point to Courage should not has succeed."));
    }

    @Test
    @DisplayName("Fail when the number of invested points in characteristic is 0")
    void failWhenNumberOfInvestedPointsOnCharacteristicIsZero() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(SnapshotFixture.getHighHuman());

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byRemovingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.COURAGE)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(_ -> fail("Removing a point to Courage should not has succeed."));
    }

    @Test
    @DisplayName("Succeed when the number of invested points in characteristic is higher than 0")
    void succeedWhenNumberOfInvestedPointsInCharacteristicIsHigherThanZero() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(
                SnapshotFixture.getWithRaceAndInvestedPointIn(
                        RaceName.HUMAN,
                        Map.of(PrimaryCharacteristicName.COURAGE, 1)
                )
        );

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byRemovingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.COURAGE)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onFailure(cause -> fail("Fail to remove a point in Courage: "+ cause.message()))
                .onSuccess(snapshot -> {
                    final var courageValue = snapshot.investedPoints().get(PrimaryCharacteristicName.COURAGE);

                    assertThat(courageValue)
                            .as("Courage should go from %d to %d", 1, 0)
                            .isEqualTo(0);
                });
    }

    @Test
    @DisplayName("Fail when no characteristic name is given")
    void failWhenNoCharacteristicNameIsGiven() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(SnapshotFixture.getDefaultOne());

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byRemovingOnePoint()
                .toCharacteristicNamed(null)
                .toCharacterNamed(DefaultCharacterData.NAME)
                .onSuccess(_ -> fail("Test should fail when no characteristic name is given."));
    }

    @Test
    @DisplayName("Fail when no character name is given")
    void failWhenNoCharacterNameIsGiven() {
        final ForLoadingSnapshot forLoadingSnapshot = _ -> Option.some(SnapshotFixture.getDefaultOne());

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byRemovingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.COURAGE)
                .toCharacterNamed(null)
                .onSuccess(_ -> fail("Test should fail when no character name is given."));

        new DefineCharacteristic(forLoadingSnapshot, forStoringSnapshot)
                .byRemovingOnePoint()
                .toCharacteristicNamed(PrimaryCharacteristicName.COURAGE)
                .toCharacterNamed("")
                .onSuccess(_ -> fail("Test should fail when empty character name is given."));
    }
}
