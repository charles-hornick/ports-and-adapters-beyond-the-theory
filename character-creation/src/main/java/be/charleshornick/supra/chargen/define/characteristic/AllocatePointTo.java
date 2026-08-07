package be.charleshornick.supra.chargen.define.characteristic;

import be.charleshornick.supra.chargen.ForStoringSnapshot;
import be.charleshornick.supra.chargen.define.ForLoadingSnapshot;
import be.charleshornick.supra.chargen.define.ToCharacter;
import be.charleshornick.supra.chargen.fault.SupraCause;
import be.charleshornick.supra.chargen.state.snapshot.Snapshot;
import org.pragmatica.lang.Result;

import java.util.function.BiFunction;

final class AllocatePointTo implements ToCharacter {

    private final ForLoadingSnapshot forLoadingSnapshot;
    private final ForStoringSnapshot forStoringSnapshot;
    private final be.charleshornick.supra.chargen.characteristic.PrimaryCharacteristicName characteristic;
    private final BiFunction<be.charleshornick.supra.chargen.define.characteristic.Character, be.charleshornick.supra.chargen.characteristic.PrimaryCharacteristicName, Result<be.charleshornick.supra.chargen.define.characteristic.Character>> operation;

    AllocatePointTo(final ForLoadingSnapshot forLoadingSnapshot,
                    final ForStoringSnapshot forStoringSnapshot,
                    final be.charleshornick.supra.chargen.characteristic.PrimaryCharacteristicName characteristic,
                    final BiFunction<be.charleshornick.supra.chargen.define.characteristic.Character, be.charleshornick.supra.chargen.characteristic.PrimaryCharacteristicName, Result<be.charleshornick.supra.chargen.define.characteristic.Character>> operation) {
        this.forLoadingSnapshot = forLoadingSnapshot;
        this.forStoringSnapshot = forStoringSnapshot;
        this.characteristic = characteristic;
        this.operation = operation;
    }

    @Override
    public Result<Snapshot> toCharacterNamed(final String name) {
            return this.getSnapshot(name)
                    .flatMap(snapshot -> this.operation.apply(be.charleshornick.supra.chargen.define.characteristic.Character.load(snapshot), this.characteristic))
                    .map(Character::doSnapshot)
                    .flatMap(this.forStoringSnapshot::store);
        }

        private Result<Snapshot> getSnapshot(final String characterName) {
            return this.forLoadingSnapshot
                    .getLastSnapshot(characterName)
                    .flatMap(opt -> opt.toResult(new SupraCause.NotFound("character", characterName)));
        }
}
