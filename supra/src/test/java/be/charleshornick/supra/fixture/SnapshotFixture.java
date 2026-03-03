package be.charleshornick.supra.fixture;

import be.charleshornick.supra.shared.character.snapshot.Action;
import be.charleshornick.supra.shared.character.snapshot.Snapshot;
import be.charleshornick.supra.shared.character.snapshot.SnapshotBuilder;
import be.charleshornick.supra.shared.profession.ProfessionName;
import be.charleshornick.supra.shared.race.RaceName;

public class SnapshotFixture {

    public static Snapshot getDefaultOne() {
        return SnapshotBuilder.asFirstOne(DefaultCharacterData.NAME);
    }

    public static Snapshot getWithProfession(final ProfessionName name) {
        return SnapshotBuilder
                .basedOnPreviousSnapshot(getDefaultOne())
                .updateProfessionWith(ProfessionFixture.get(name))
                .getForAction(Action.DEFINE_PROFESSION);
    }

    public static Snapshot getWithRace(final RaceName name) {
        return SnapshotBuilder
                .basedOnPreviousSnapshot(getDefaultOne())
                .updateRaceWith(RaceFixture.get(name))
                .getForAction(Action.DEFINE_RACE);
    }

    public static Snapshot getOneWithRaceReinitialized() {
        final var base = SnapshotBuilder
                .basedOnPreviousSnapshot(getDefaultOne())
                .updateRaceWith(RaceFixture.dwarf())
                .getForAction(Action.DEFINE_RACE);

        return SnapshotBuilder
                .basedOnPreviousSnapshot(base)
                .updateRaceWith(RaceFixture.get(RaceName.UNDEFINED))
                .getForAction(Action.DEFINE_RACE);
    }
}
