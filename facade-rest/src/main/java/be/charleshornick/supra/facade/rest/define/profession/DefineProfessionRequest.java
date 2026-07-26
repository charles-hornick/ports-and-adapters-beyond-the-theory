package be.charleshornick.supra.facade.rest.define.profession;

import org.jspecify.annotations.Nullable;

public record DefineProfessionRequest(@Nullable String professionName) {

    public DefineProfessionCommand toCommand(final String characterName) {
        return new DefineProfessionCommand(characterName, professionName);
    }
}
