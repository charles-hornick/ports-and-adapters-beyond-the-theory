package be.charleshornick.supra.bootstrap;

import be.charleshornick.supra.characteristic.PrimaryCharacteristic;
import be.charleshornick.supra.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.define.race.ForLoadingRace;
import be.charleshornick.supra.race.Race;
import be.charleshornick.supra.race.RaceName;
import org.pragmatica.lang.Option;

import java.util.List;

public class InMemoryRaceStorage implements ForLoadingRace {

    @Override
    public Option<Race> getRaceDetails(final RaceName raceName) {
        return switch (raceName) {
            case HIGH_ELF -> Option.present(new Race(
                    RaceName.HIGH_ELF,
                    "",
                    8,
                    3,
                    true,
                    List.of(
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.COURAGE, 7),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.WILLPOWER, 7),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.CHARISMA, 8),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.BEAUTY, 9),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.CONSTITUTION, 7),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.INTELLIGENCE, 9),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.PERCEPTION, 9),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.DEXTERITY, 9),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.STRENGTH, 7)
                    )));

            case DWARF -> Option.present(new Race(
                    RaceName.DWARF,
                    "",
                    4,
                    0,
                    false,
                    List.of(
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.COURAGE, 9),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.WILLPOWER, 8),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.CHARISMA, 7),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.BEAUTY, 7),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.CONSTITUTION, 9),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.INTELLIGENCE, 8),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.PERCEPTION, 7),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.DEXTERITY, 7),
                            PrimaryCharacteristic.with(PrimaryCharacteristicName.STRENGTH, 10)
                    )));

            default -> Option.none();
        };
    }
}
