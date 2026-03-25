package be.charleshornick.supra.bootstrap;

import be.charleshornick.supra.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.define.profession.ForLoadingProfession;
import be.charleshornick.supra.profession.Prerequisite;
import be.charleshornick.supra.profession.Profession;
import be.charleshornick.supra.profession.ProfessionName;
import be.charleshornick.supra.profession.ProfessionType;
import org.pragmatica.lang.Option;

import java.util.List;
import java.util.Map;

import static be.charleshornick.supra.race.RaceName.*;

public class InMemoryProfessionStorage implements ForLoadingProfession {

    @Override
    public Option<Profession> getProfessionDetails(final ProfessionName professionName) {
        return switch (professionName) {
            case ELF_ADVENTURER -> Option.present(new Profession(
                    ProfessionName.ELF_ADVENTURER,
                    ProfessionType.MINOR,
                    "",
                    0,
                    Prerequisite.with(
                            Map.of(),
                            List.of(GNOME, DWARF, HALF_ELF, HIGH_DWARF, HUMAN, HIGH_HUMAN, WOODEN_ELF, HOBBIT, HALF_ORK)
                    ),
                    null,
                    List.of()
            ));

            case KRAEN_WARRIOR -> Option.present(new Profession(
                    ProfessionName.KRAEN_WARRIOR,
                    ProfessionType.MAJOR,
                    "",
                    6,
                    Prerequisite.with(
                            Map.of(
                                    PrimaryCharacteristicName.COURAGE, 12,
                                    PrimaryCharacteristicName.CONSTITUTION, 13,
                                    PrimaryCharacteristicName.STRENGTH, 14
                            ),
                            List.of(ELF, HALF_ELF, HIGH_ELF, HUMAN, HIGH_HUMAN, WOODEN_ELF, HOBBIT, HALF_ORK)
                    ),
                    null,
                    List.of()
            ));

            default -> Option.empty();
        };
    }
}
