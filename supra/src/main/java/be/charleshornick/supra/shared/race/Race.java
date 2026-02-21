package be.charleshornick.supra.shared.race;

import be.charleshornick.supra.shared.CreationPointConsumer;
import be.charleshornick.supra.shared.characteristic.PrimaryCharacteristic;

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
