package be.charleshornick.supra.facade.rest.define.race;

import org.jspecify.annotations.Nullable;

public record DefineRaceRequest(@Nullable String raceName) {

    public DefineRaceCommand toCommand(final String characterName) {
        return new DefineRaceCommand(characterName, raceName);
    }
}
