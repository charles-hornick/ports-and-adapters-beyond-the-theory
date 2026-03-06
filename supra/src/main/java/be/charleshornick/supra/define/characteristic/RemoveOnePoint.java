package be.charleshornick.supra.define.characteristic;

import be.charleshornick.supra.define.ForLoadingSnapshot;
import be.charleshornick.supra.ForStoringSnapshot;
import be.charleshornick.supra.define.ToCharacter;
import be.charleshornick.supra.ErrorCause;
import be.charleshornick.supra.snapshot.Snapshot;
import be.charleshornick.supra.characteristic.PrimaryCharacteristicName;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Tuple;

final class RemoveOnePoint implements DefineCharacteristic.ToCharacteristic, ToCharacter {

    private final ForLoadingSnapshot forLoadingSnapshot;
    private final ForStoringSnapshot forStoringSnapshot;
    private final PrimaryCharacteristicName primaryCharacteristicName;

    RemoveOnePoint(final ForLoadingSnapshot forLoadingSnapshot, final ForStoringSnapshot forStoringSnapshot) {
        this.forLoadingSnapshot = forLoadingSnapshot;
        this.forStoringSnapshot = forStoringSnapshot;
        this.primaryCharacteristicName = null;
    }

    private RemoveOnePoint(final RemoveOnePoint removeOnePoint, final PrimaryCharacteristicName primaryCharacteristicName) {
        this.forLoadingSnapshot = removeOnePoint.forLoadingSnapshot;
        this.forStoringSnapshot = removeOnePoint.forStoringSnapshot;
        this.primaryCharacteristicName = primaryCharacteristicName;
    }

    @Override
    public ToCharacter toCharacteristicNamed(final PrimaryCharacteristicName characterName) {
        return new RemoveOnePoint(this, characterName);
    }

    @Override
    public Result<Snapshot> toCharacterNamed(final String name) {
        final var snapshot = this.getSnapshot(name);
        final var characteristic = this.getCharacteristic();

        return Result.all(snapshot, characteristic)
                .id()
                .flatMap(this::apply)
                .map(Character::doSnapshot)
                .flatMap(this.forStoringSnapshot::store);
    }

    private Result<Snapshot> getSnapshot(final String characterName) {
        return this.forLoadingSnapshot
                .getLastSnapshot(characterName)
                .toResult(ErrorCause.CHARACTER_DOES_NOT_EXIST);
    }

    private Result<PrimaryCharacteristicName> getCharacteristic() {
        return (this.primaryCharacteristicName != null)
                ? Result.ok(this.primaryCharacteristicName)
                : Result.failure(ErrorCause.UNDEFINED_PRIMARY_CHARACTERISTIC);
    }

    private Result<Character> apply(final Tuple.Tuple2<Snapshot, PrimaryCharacteristicName> data) {
        final var snapshot = data.first();
        final var characteristic = data.last();

        return Character.load(snapshot).removePointTo(characteristic);
    }
}
