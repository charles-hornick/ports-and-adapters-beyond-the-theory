package be.charleshornick.supra.chargen.define.characteristic;

import be.charleshornick.supra.chargen.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.chargen.fault.SupraCause;
import be.charleshornick.supra.chargen.state.Recorder;
import be.charleshornick.supra.chargen.state.snapshot.Action;
import be.charleshornick.supra.chargen.state.snapshot.Snapshot;
import be.charleshornick.supra.chargen.state.SnapshotBuilder;
import be.charleshornick.supra.chargen.state.InvestedPoint;
import be.charleshornick.supra.chargen.race.Race;
import org.pragmatica.lang.Result;

class Character implements Recorder {

    private final Snapshot snapshot;
    private final Race race;
    private InvestedPoint investedPoint;
    private Action action;

    private Character(final Snapshot snapshot) {
        this.snapshot = snapshot;
        this.race = snapshot.race();
        this.investedPoint = InvestedPoint.with(snapshot.investedPoints(), this.race);
    }

    public static Character load(final Snapshot snapshot) {
        return new Character(snapshot);
    }

    public Result<Character> addPointTo(final PrimaryCharacteristicName name) {
        if (this.race.isDefined()) {
            return this.investedPoint
                    .addPointToCharacteristic(name)
                    .onSuccess(points -> {
                        this.investedPoint = points;
                        this.action = Action.ADD_POINT_TO_CHARACTERISTIC;
                    })
                    .map(_ -> this);
        }
        return Result.failure(new SupraCause.RuleViolation("race.mandatory", "cannot.add.without.race"));
    }

    public Result<Character> removePointTo(final PrimaryCharacteristicName name) {
        if (this.race.isDefined()) {
            return this.investedPoint
                    .removePointToCharacteristic(name)
                    .onSuccess(points -> {
                        this.investedPoint = points;
                        this.action = Action.REMOVE_POINT_TO_CHARACTERISTIC;
                    })
                    .map(_ -> this);
        }
        return Result.failure(new SupraCause.RuleViolation("race.mandatory", "cannot.remove.without.race"));
    }

    @Override
    public Snapshot doSnapshot() {
        return SnapshotBuilder
                .basedOnPreviousSnapshot(this.snapshot)
                .updateInvestedPointsWith(this.investedPoint)
                .getForAction(this.action);
    }
}
