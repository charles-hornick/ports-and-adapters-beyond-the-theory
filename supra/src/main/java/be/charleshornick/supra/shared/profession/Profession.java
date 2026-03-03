package be.charleshornick.supra.shared.profession;

import be.charleshornick.supra.shared.CreationPointConsumer;
import be.charleshornick.supra.shared.point.InvestedPoint;
import be.charleshornick.supra.shared.race.Race;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.utils.Causes;

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

    public Result<Profession> validatePrerequisite(final Race name, final InvestedPoint characteristics) {
        if (this.type.isEvolutionType() || !this.prerequisite.arePrerequisiteFulfilled(name, characteristics)) {
            return Result.failure(Causes.cause("Prerequisite not fulfilled to become a " + this.name));
        }
        return Result.success(this);
    }

    @Override
    public int getCostInCreationPoint() {
        return this.costInCreationPoint;
    }
}
