package be.charleshornick.supra.chargen.define.characteristic;

import be.charleshornick.supra.chargen.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.chargen.define.ForLoadingSnapshot;
import be.charleshornick.supra.chargen.ForStoringSnapshot;
import be.charleshornick.supra.chargen.define.ToCharacter;

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
        return name -> new AllocatePointTo(
                this.forLoadingSnapshot,
                this.forStoringSnapshot,
                name,
                be.charleshornick.supra.chargen.define.characteristic.Character::addPointTo
        );
    }

    public ToCharacteristic byRemovingOnePoint() {
        return name -> new AllocatePointTo(
                this.forLoadingSnapshot,
                this.forStoringSnapshot,
                name,
                Character::removePointTo
        );
    }
}
