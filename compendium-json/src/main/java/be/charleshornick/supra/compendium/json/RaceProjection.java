package be.charleshornick.supra.compendium.json;

import be.charleshornick.supra.chargen.characteristic.PrimaryCharacteristic;
import be.charleshornick.supra.chargen.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.chargen.race.Race;
import be.charleshornick.supra.chargen.race.RaceName;
import java.util.ArrayList;
import java.util.Map;

record RaceProjection(String name,
                      String description,
                      int costInCreationPoint,
                      int bonusPointToCharacteristic,
                      boolean isHighRace,
                      Map<String, Integer> characteristics) {

    Race toCore() {
        return new Race(
                RaceName.valueOf(name),
                description,
                costInCreationPoint,
                bonusPointToCharacteristic,
                isHighRace,
                buildPrimaryCharacteristics(characteristics)
        );
    }

    private static ArrayList<PrimaryCharacteristic> buildPrimaryCharacteristics(final Map<String, Integer> characteristics) {
        final var primaryCharacteristics = new ArrayList<PrimaryCharacteristic>(9);
        for (final var characteristic : characteristics.entrySet()) {
            primaryCharacteristics.add(new PrimaryCharacteristic(
                    PrimaryCharacteristicName.valueOf(characteristic.getKey()),
                    characteristic.getValue()
            ));
        }
        return primaryCharacteristics;
    }
}
