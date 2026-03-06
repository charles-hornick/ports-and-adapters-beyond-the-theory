package be.charleshornick.supra.character.define.characteristic;

import be.charleshornick.supra.character.ForLoadingSnapshot;
import be.charleshornick.supra.character.ForStoringSnapshot;
import be.charleshornick.supra.character.define.ToCharacter;
import be.charleshornick.supra.shared.ErrorCause;
import be.charleshornick.supra.shared.character.snapshot.Snapshot;
import be.charleshornick.supra.shared.characteristic.PrimaryCharacteristicName;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Tuple;

final class AddOnePoint implements DefineCharacteristic.ToCharacteristic, ToCharacter {

    private final ForLoadingSnapshot forLoadingSnapshot;
    private final ForStoringSnapshot forStoringSnapshot;
    private final PrimaryCharacteristicName primaryCharacteristicName;

    AddOnePoint(final ForLoadingSnapshot forLoadingSnapshot, final ForStoringSnapshot forStoringSnapshot) {
        this.forLoadingSnapshot = forLoadingSnapshot;
        this.forStoringSnapshot = forStoringSnapshot;
        this.primaryCharacteristicName = null;
    }

    private AddOnePoint(final AddOnePoint addOnePoint, final PrimaryCharacteristicName primaryCharacteristicName) {
        this.forLoadingSnapshot = addOnePoint.forLoadingSnapshot;
        this.forStoringSnapshot = addOnePoint.forStoringSnapshot;
        this.primaryCharacteristicName = primaryCharacteristicName;
    }

    @Override
    public ToCharacter toCharacteristicNamed(final PrimaryCharacteristicName characterName) {
        return new AddOnePoint(this, characterName);
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

        return Character.load(snapshot).addPointTo(characteristic);
    }
}
