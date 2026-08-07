package be.charleshornick.supra.facade.rest.define.profession;

import org.jspecify.annotations.Nullable;
import org.pragmatica.lang.Result;

public record DefineProfessionRequest(@Nullable String professionName) {

    public Result<DefineProfessionCommand> toCommand(final String characterName) {
        return DefineProfessionCommand.from(characterName, professionName);
    }
}
