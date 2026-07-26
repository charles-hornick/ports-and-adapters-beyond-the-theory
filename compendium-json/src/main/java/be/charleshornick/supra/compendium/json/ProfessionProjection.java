package be.charleshornick.supra.compendium.json;

import be.charleshornick.supra.profession.Prerequisite;
import be.charleshornick.supra.profession.Profession;
import be.charleshornick.supra.profession.ProfessionName;
import be.charleshornick.supra.profession.ProfessionType;

import java.util.List;

record ProfessionProjection(String name,
                            String type,
                            List<String> archetypes,
                            String description,
                            PrerequisiteProjection prerequisite,
                            List<String> previousProfessions,
                            int costInCreationPoint) {

    Profession toCore() {
        final var prerequisite = (this.prerequisite != null)
                ? this.prerequisite.toCore()
                : Prerequisite.emptyPrerequisite();

        return new Profession(
                ProfessionName.valueOf(this.name),
                ProfessionType.valueOf(this.type),
                this.description,
                this.costInCreationPoint,
                prerequisite,
                toProfessionNames(this.previousProfessions),
                toProfessionNames(this.archetypes)
        );
    }

    private static List<ProfessionName> toProfessionNames(final List<String> names) {
        return (names != null)
                ? names.stream().map(ProfessionName::valueOf).toList()
                : List.of();
    }
}
