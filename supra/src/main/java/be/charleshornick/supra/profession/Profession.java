package be.charleshornick.supra.profession;

import be.charleshornick.supra.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.state.CreationPointConsumer;
import be.charleshornick.supra.race.Race;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.utils.Causes;

import java.util.List;
import java.util.Map;

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

    public boolean isUndefined() {
        return this.name.isTechnical();
    }

    public Result<Profession> validatePrerequisite(final Race name, final Map<PrimaryCharacteristicName, Integer> characteristics) {
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
