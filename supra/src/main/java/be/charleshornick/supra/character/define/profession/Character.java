package be.charleshornick.supra.character.define.profession;

import be.charleshornick.supra.shared.ErrorCause;
import be.charleshornick.supra.shared.Recorder;
import be.charleshornick.supra.shared.character.snapshot.Action;
import be.charleshornick.supra.shared.character.snapshot.Snapshot;
import be.charleshornick.supra.shared.character.snapshot.SnapshotBuilder;
import be.charleshornick.supra.shared.point.CreationPoint;
import be.charleshornick.supra.shared.point.InvestedPoint;
import be.charleshornick.supra.shared.profession.Profession;
import be.charleshornick.supra.shared.race.Race;
import org.pragmatica.lang.Result;

class Character implements Recorder {

    private final Snapshot snapshot;
    private CreationPoint creationPoint;
    private final Race race;
    private Profession profession;
    private final InvestedPoint investedPoint;

    private Character(final Snapshot snapshot) {
        this.snapshot = snapshot;
        this.race = snapshot.race();
        this.profession = snapshot.profession();
        this.investedPoint = InvestedPoint.with(snapshot.investedPoints(), this.race);
        this.creationPoint = CreationPoint.beginning()
                .addNewConsumer(this.race)
                .addNewConsumer(this.profession);
    }

    public static Character load(final Snapshot snapshot) {
        return new Character(snapshot);
    }

    public Result<Character> defineProfession(final Profession profession) {
        if (this.creationPoint.hasEnoughCreationToAdd(profession)) {
            return profession
                    .validatePrerequisite(this.race, this.investedPoint)
                    .onSuccess(prof -> {
                        this.profession = prof;
                        this.creationPoint = CreationPoint.beginning()
                                .addNewConsumer(this.race)
                                .addNewConsumer(this.profession);
                    })
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
