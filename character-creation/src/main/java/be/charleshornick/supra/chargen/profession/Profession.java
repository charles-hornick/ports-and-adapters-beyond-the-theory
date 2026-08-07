package be.charleshornick.supra.chargen.profession;

import be.charleshornick.supra.chargen.fault.SupraCause;
import be.charleshornick.supra.chargen.state.CreationPointConsumer;
import be.charleshornick.supra.chargen.race.Race;
import org.pragmatica.lang.Result;

import java.util.List;
import java.util.Map;

public record Profession(ProfessionName name,
                         ProfessionType type,
                         String description,
                         int costInCreationPoint,
                         Prerequisite prerequisite,
                         List<ProfessionName> previousProfession,
                         List<ProfessionName> archetypes) implements CreationPointConsumer {

    public static Profession undefined() {
        return new Profession(ProfessionName.UNDEFINED, ProfessionType.UNDEFINED, "", 0, Prerequisite.emptyPrerequisite(), List.of(), List.of());
    }

    public boolean isRaceForbidden(final Race race) {
        return this.prerequisite.isRaceForbidden(race);
    }

    public boolean isUndefined() {
        return this.name.isTechnical();
    }

    public Result<Profession> validatePrerequisite(final Race name, final Map<be.charleshornick.supra.chargen.characteristic.PrimaryCharacteristicName, Integer> characteristics) {
        if (this.type.isEvolutionType() || !this.prerequisite.arePrerequisiteFulfilled(name, characteristics)) {
            return Result.failure(new SupraCause.RuleViolation("prerequisite.unfulfilled", "impossible.to.become." + this.name));
        }
        return Result.success(this);
    }

    @Override
    public int getCostInCreationPoint() {
        return this.costInCreationPoint;
    }
}
