package be.charleshornick.supra.fixture;

import be.charleshornick.supra.shared.character.snapshot.Action;
import be.charleshornick.supra.shared.character.snapshot.Snapshot;
import be.charleshornick.supra.shared.character.snapshot.SnapshotBuilder;
import be.charleshornick.supra.shared.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.shared.point.InvestedPoint;
import be.charleshornick.supra.shared.profession.ProfessionName;
import be.charleshornick.supra.shared.race.RaceName;

import java.util.Map;

public interface SnapshotFixture {

    static Snapshot getDefaultOne() {
        return SnapshotBuilder.asFirstOne(DefaultCharacterData.NAME);
    }

    static Snapshot getWithProfession(final ProfessionName name) {
        return SnapshotBuilder
                .basedOnPreviousSnapshot(getDefaultOne())
                .updateProfessionWith(ProfessionFixture.get(name))
                .getForAction(Action.DEFINE_PROFESSION);
    }

    static Snapshot getWithRace(final RaceName name) {
        return SnapshotBuilder
                .basedOnPreviousSnapshot(getDefaultOne())
                .updateRaceWith(RaceFixture.get(name))
                .getForAction(Action.DEFINE_RACE);
    }

    static Snapshot getOneWithRaceReinitialized() {
        final var base = SnapshotBuilder
                .basedOnPreviousSnapshot(getDefaultOne())
                .updateRaceWith(RaceFixture.dwarf())
                .getForAction(Action.DEFINE_RACE);

        return SnapshotBuilder
                .basedOnPreviousSnapshot(base)
                .updateRaceWith(RaceFixture.get(RaceName.UNDEFINED))
                .getForAction(Action.DEFINE_RACE);
    }

    static Snapshot getHighHuman() {
        return SnapshotBuilder
                .basedOnPreviousSnapshot(getDefaultOne())
                .updateRaceWith(RaceFixture.get(RaceName.HIGH_HUMAN))
                .getForAction(Action.DEFINE_RACE);
    }

    static Snapshot getWithRaceAndInvestedPointIn(final RaceName raceName, final Map<PrimaryCharacteristicName, Integer> points) {
        final var race = RaceFixture.get(raceName);

        final var baseWithRace = SnapshotBuilder
                .basedOnPreviousSnapshot(getDefaultOne())
                .updateRaceWith(race)
                .getForAction(Action.DEFINE_RACE);

        return SnapshotBuilder
                .basedOnPreviousSnapshot(baseWithRace)
                .updateInvestedPointsWith(InvestedPoint.with(points, race))
                .getForAction(Action.ADD_POINT_TO_CHARACTERISTIC);
    }

    static Snapshot getHumanWarrior() {
        final var race = RaceFixture.get(RaceName.HUMAN);

        final var snapshotWithRace = SnapshotBuilder
                .basedOnPreviousSnapshot(getDefaultOne())
                .updateRaceWith(race)
                .getForAction(Action.DEFINE_RACE);

        final var snapshotWithCharacteristics = SnapshotBuilder
                .basedOnPreviousSnapshot(snapshotWithRace)
                .updateInvestedPointsWith(InvestedPoint.with(
                        Map.of(
                                PrimaryCharacteristicName.COURAGE, 5,
                                PrimaryCharacteristicName.WILLPOWER, 2,
                                PrimaryCharacteristicName.CHARISMA, 2,
                                PrimaryCharacteristicName.CONSTITUTION, 5,
                                PrimaryCharacteristicName.STRENGTH, 5
                        ),
                        race
                ))
                .getForAction(Action.ADD_POINT_TO_CHARACTERISTIC);

        return SnapshotBuilder
                .basedOnPreviousSnapshot(snapshotWithCharacteristics)
                .updateProfessionWith(ProfessionFixture.get(ProfessionName.GUERRIER))
                .getForAction(Action.DEFINE_PROFESSION);
    }
}
