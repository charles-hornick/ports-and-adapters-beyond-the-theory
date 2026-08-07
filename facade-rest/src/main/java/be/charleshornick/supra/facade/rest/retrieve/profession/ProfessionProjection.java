package be.charleshornick.supra.facade.rest.retrieve.profession;

import be.charleshornick.supra.chargen.profession.Profession;

public record ProfessionProjection(String name, String  type, String description, int costInCreationPoint) {

    static ProfessionProjection from(final Profession profession) {
        return new ProfessionProjection(
                profession.name().name(),
                profession.type().name(),
                profession.description(),
                profession.costInCreationPoint()
        );
    }
}
