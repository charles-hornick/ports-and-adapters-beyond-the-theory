package be.charleshornick.supra.facade.rest.create;

import be.charleshornick.supra.fault.SupraCause;
import be.charleshornick.supra.lib.cqs.core.Command;
import org.jspecify.annotations.Nullable;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Verify;

public record CreateCharacterCommand(@Nullable String characterName) implements Command {

    public CreateCharacterCommand {
        characterName = (characterName != null) ? characterName.strip() : "";
    }

    public Result<CreateCharacterCommand> validate() {
        return Verify
                .ensure(this.characterName, Verify.Is::notNull, new SupraCause.InvalidInput("characterName", "canont.be.null"))
                .map(_ -> this);
    }
}
