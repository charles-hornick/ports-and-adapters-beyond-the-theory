package be.charleshornick.supra.chargen.define.profession;

import be.charleshornick.supra.chargen.fault.ErrorCause;
import be.charleshornick.supra.chargen.state.Recorder;
import be.charleshornick.supra.chargen.state.snapshot.Action;
import be.charleshornick.supra.chargen.state.snapshot.Snapshot;
import be.charleshornick.supra.chargen.state.SnapshotBuilder;
import be.charleshornick.supra.chargen.state.CreationPoint;
import be.charleshornick.supra.chargen.state.InvestedPoint;
import be.charleshornick.supra.chargen.profession.Profession;
import be.charleshornick.supra.chargen.race.Race;
import org.pragmatica.lang.Result;

class Character implements Recorder {

    private final Snapshot snapshot;
    private final Race race;
    private Profession profession;
    private final InvestedPoint investedPoint;

    private Character(final Snapshot snapshot) {
        this.snapshot = snapshot;
        this.race = snapshot.race();
        this.profession = snapshot.profession();
        this.investedPoint = InvestedPoint.with(snapshot.investedPoints(), this.race);
    }

    public static Character load(final Snapshot snapshot) {
        return new Character(snapshot);
    }

    public Result<Character> defineProfession(final Profession profession) {
        final var remainingCreationPoints = CreationPoint.beginning()
                .addNewConsumer(this.race)
                .addNewConsumer(profession);

        if (remainingCreationPoints.hasEnoughCreationToAdd(profession)) {
            return profession
                    .validatePrerequisite(this.race, this.investedPoint.computeWithRace())
                    .onSuccess(prof -> this.profession = prof)
                    .map(_ -> this);
        }

        return Result.failure(ErrorCause.NOT_ENOUGH_CREATION_POINT);
    }

    @Override
    public Snapshot doSnapshot() {
        return SnapshotBuilder
                .basedOnPreviousSnapshot(this.snapshot)
                .updateProfessionWith(this.profession)
                .getForAction(Action.DEFINE_PROFESSION);
    }
}
