package be.charleshornick.supra.define.characteristic;

import be.charleshornick.supra.snapshot.Recorder;
import be.charleshornick.supra.snapshot.Action;
import be.charleshornick.supra.snapshot.Snapshot;
import be.charleshornick.supra.snapshot.SnapshotBuilder;
import be.charleshornick.supra.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.snapshot.InvestedPoint;
import be.charleshornick.supra.race.Race;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.utils.Causes;

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
        return Result.failure(Causes.cause("A race has to be defined before adding points to primary characteristic."));
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
        return Result.failure(Causes.cause("A race has to be defined before removing points to primary characteristic."));
    }

    @Override
    public Snapshot doSnapshot() {
        return SnapshotBuilder
                .basedOnPreviousSnapshot(this.snapshot)
                .updateInvestedPointsWith(this.investedPoint)
                .getForAction(this.action);
    }
}
