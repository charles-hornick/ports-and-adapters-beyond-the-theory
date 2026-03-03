package be.charleshornick.supra.fixture;

import be.charleshornick.supra.shared.characteristic.PrimaryCharacteristic;
import be.charleshornick.supra.shared.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.shared.race.Race;
import be.charleshornick.supra.shared.race.RaceName;

import java.util.List;

public interface RaceFixture {

    static Race get(final RaceName name) {
        if (name == null) {
            return null;
        }

        return switch (name) {
            case ELF -> elf();
            case HUMAN -> human();
            case DWARF -> dwarf();
            case HALF_ELF -> halfElf();
            case HIGH_ELF -> highElf();
            case HIGH_HUMAN -> highHuman();
            default -> Race.undefined();
        };
    }

    static Race elf() {
        return new Race(
                RaceName.ELF,
                "",
                4,
                0,
                false,
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
                )
        );
    }

    static Race dwarf() {
        return new Race(
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
                )
        );
    }

    static Race human() {
        return new Race(
                RaceName.HUMAN,
                "",
                0,
                0,
                false,
                List.of(
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.COURAGE, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.WILLPOWER, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.CHARISMA, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.BEAUTY, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.CONSTITUTION, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.INTELLIGENCE, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.PERCEPTION, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.DEXTERITY, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.STRENGTH, 8)
                )
        );
    }

    static Race halfElf() {
        return new Race(
                RaceName.HALF_ELF,
                "",
                2,
                0,
                false,
                List.of(
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.COURAGE, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.WILLPOWER, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.CHARISMA, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.BEAUTY, 9),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.CONSTITUTION, 7),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.INTELLIGENCE, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.PERCEPTION, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.DEXTERITY, 9),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.STRENGTH, 7)
                )
        );
    }

    static Race highElf() {
        return new Race(
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
                )
        );
    }

    static Race highHuman() {
        return new Race(
                RaceName.HIGH_HUMAN,
                "",
                8,
                3,
                true,
                List.of(
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.COURAGE, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.WILLPOWER, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.CHARISMA, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.BEAUTY, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.CONSTITUTION, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.INTELLIGENCE, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.PERCEPTION, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.DEXTERITY, 8),
                        PrimaryCharacteristic.with(PrimaryCharacteristicName.STRENGTH, 8)
                )
        );
    }
}
