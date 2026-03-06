package be.charleshornick.supra.character.define.characteristic;

import be.charleshornick.supra.character.ForLoadingSnapshot;
import be.charleshornick.supra.character.ForStoringSnapshot;
import be.charleshornick.supra.character.define.ToCharacter;
import be.charleshornick.supra.shared.characteristic.PrimaryCharacteristicName;

public final class DefineCharacteristic {

    public interface ToCharacteristic {
        ToCharacter toCharacteristicNamed(PrimaryCharacteristicName characterName);
    }

    private final ForLoadingSnapshot forLoadingSnapshot;
    private final ForStoringSnapshot forStoringSnapshot;

    public DefineCharacteristic(final ForLoadingSnapshot forLoadingSnapshot, final ForStoringSnapshot forStoringSnapshot) {
        this.forLoadingSnapshot = forLoadingSnapshot;
        this.forStoringSnapshot = forStoringSnapshot;
    }

    public ToCharacteristic byAddingOnePoint() {
        return new AddOnePoint(this.forLoadingSnapshot, this.forStoringSnapshot);
    }

    public ToCharacteristic byRemovingOnePoint() {
        return new RemoveOnePoint(this.forLoadingSnapshot, this.forStoringSnapshot);
    }
}
