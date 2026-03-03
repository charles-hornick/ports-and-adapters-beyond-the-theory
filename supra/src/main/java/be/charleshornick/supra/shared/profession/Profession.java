package be.charleshornick.supra.shared.profession;

import be.charleshornick.supra.shared.CreationPointConsumer;
import be.charleshornick.supra.shared.race.Race;

import java.util.List;

public record Profession(ProfessionName name,
                         ProfessionType type,
                         String description,
                         int costInCreationPoint,
                         Prerequisite prerequisite,
                         ProfessionName previousProfession,
                         List<ProfessionName> archetypes) implements CreationPointConsumer {

    public static Profession undefined() {
        return new Profession(ProfessionName.UNDEFINED, null, "", 0, Prerequisite.emptyPrerequisite(), null, List.of());
    }

    public boolean isRaceForbidden(final Race race) {
        return this.prerequisite.isRaceForbidden(race);
    }

    @Override
    public int getCostInCreationPoint() {
        return this.costInCreationPoint;
    }
}
