package be.charleshornick.supra.chargen.race;

import be.charleshornick.supra.chargen.characteristic.PrimaryCharacteristic;
import be.charleshornick.supra.chargen.state.CreationPointConsumer;

import java.util.List;

public record Race(RaceName name,
                   String description,
                   int costInCreationPoint,
                   int bonusPointToCharacteristic,
                   boolean highRace,
                   List<PrimaryCharacteristic> characteristics) implements CreationPointConsumer {

    public static Race undefined() {
        return new Race(RaceName.UNDEFINED, "", 0, 0, false, List.of());
    }

    @Override
    public int getCostInCreationPoint() {
        return this.costInCreationPoint;
    }

    public boolean isDefined() {
        return !RaceName.UNDEFINED.equals(this.name);
    }
}
