package be.charleshornick.supra.fixture;

import be.charleshornick.supra.shared.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.shared.profession.Prerequisite;
import be.charleshornick.supra.shared.profession.Profession;
import be.charleshornick.supra.shared.profession.ProfessionName;
import be.charleshornick.supra.shared.profession.ProfessionType;
import be.charleshornick.supra.shared.race.RaceName;

import java.util.List;
import java.util.Map;

public interface ProfessionFixture {

    static Profession get(final ProfessionName name) {
        if (name == null) {
            return null;
        }

        return switch (name) {
            case AVENTURIER -> adventurer();
            case AVENTURIER_ELFE -> elfAdventurer();
            case GUERRIER -> warrior();
            case CHEVALIER -> chevalier();
            default -> Profession.undefined();
        };
    }

    static Profession chevalier() {
        return new Profession(
                ProfessionName.CHEVALIER,
                ProfessionType.MAJOR_EVOLUTION,
                "",
                0,
                Prerequisite.with(
                        Map.of(
                                PrimaryCharacteristicName.COURAGE, 14,
                                PrimaryCharacteristicName.WILLPOWER, 11,
                                PrimaryCharacteristicName.CHARISMA, 11,
                                PrimaryCharacteristicName.CONSTITUTION, 13,
                                PrimaryCharacteristicName.INTELLIGENCE, 11,
                                PrimaryCharacteristicName.DEXTERITY, 11,
                                PrimaryCharacteristicName.STRENGTH, 15
                        ),
                        List.of(RaceName.ELF, RaceName.HIGH_ELF, RaceName.WOODEN_ELF, RaceName.GNOME, RaceName.DWARF, RaceName.HIGH_DWARF, RaceName.HOBBIT)
                ),
                null,
                List.of()
        );
    }

    private static Profession warrior() {
        return new Profession(
                ProfessionName.GUERRIER,
                ProfessionType.MAJOR,
                "",
                6,
                Prerequisite.with(
                        Map.of(
                                PrimaryCharacteristicName.COURAGE, 13,
                                PrimaryCharacteristicName.WILLPOWER, 10,
                                PrimaryCharacteristicName.CHARISMA, 10,
                                PrimaryCharacteristicName.CONSTITUTION, 12,
                                PrimaryCharacteristicName.STRENGTH, 13
                        ),
                        List.of(RaceName.ELF, RaceName.HIGH_ELF, RaceName.WOODEN_ELF, RaceName.GNOME, RaceName.DWARF, RaceName.HIGH_DWARF, RaceName.HOBBIT)
                ),
                null,
                List.of()
        );
    }

    static Profession adventurer() {
        return new Profession(
                ProfessionName.AVENTURIER,
                ProfessionType.MINOR,
                "",
                0,
                Prerequisite.emptyPrerequisite(),
                null,
                List.of()
        );
    }

    private static Profession elfAdventurer() {
        return new Profession(
                ProfessionName.AVENTURIER_ELFE,
                ProfessionType.MINOR,
                "",
                0,
                Prerequisite.with(
                        Map.of(),
                        List.of(RaceName.GNOME, RaceName.DWARF, RaceName.HALF_ELF, RaceName.HIGH_DWARF, RaceName.HUMAN, RaceName.HIGH_HUMAN, RaceName.WOODEN_ELF, RaceName.HOBBIT, RaceName.HALF_ORK)
                ),
                null,
                List.of()
        );
    }
}
