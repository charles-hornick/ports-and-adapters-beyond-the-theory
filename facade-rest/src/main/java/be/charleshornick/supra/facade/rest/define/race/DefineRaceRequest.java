package be.charleshornick.supra.facade.rest.define.race;

import org.jspecify.annotations.Nullable;
import org.pragmatica.lang.Result;

public record DefineRaceRequest(@Nullable String raceName) {

    public Result<DefineRaceCommand> toCommand(final String characterName) {
        return DefineRaceCommand.from(characterName, raceName);
    }
}
