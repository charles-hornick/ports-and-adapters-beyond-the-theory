package be.charleshornick.supra.define.race;

import be.charleshornick.supra.snapshot.Recorder;
import be.charleshornick.supra.snapshot.Action;
import be.charleshornick.supra.snapshot.Snapshot;
import be.charleshornick.supra.snapshot.SnapshotBuilder;
import be.charleshornick.supra.profession.Profession;
import be.charleshornick.supra.race.Race;
import org.pragmatica.lang.Result;

class Character implements Recorder {

    private final Snapshot snapshot;
    private Race race;
    private Profession profession;

    private Character(final Snapshot snapshot) {
        this.snapshot = snapshot;
        this.race = snapshot.race();
        this.profession = snapshot.profession();
    }

    static Character load(final Snapshot snapshot) {
        return new Character(snapshot);
    }

    Result<Character> defineRace(final Race race) {
        if (this.profession.isRaceForbidden(race)) {
            this.profession = Profession.undefined();
        }

        this.race = race;

        return Result.success(this);
    }

    @Override
    public Snapshot doSnapshot() {
        return SnapshotBuilder
                .basedOnPreviousSnapshot(this.snapshot)
                .updateRaceWith(this.race)
                .updateProfessionWith(this.profession)
                .getForAction(Action.DEFINE_RACE);
    }
}
