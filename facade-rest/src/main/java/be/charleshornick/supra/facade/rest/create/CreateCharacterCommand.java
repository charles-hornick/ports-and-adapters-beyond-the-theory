package be.charleshornick.supra.facade.rest.create;

import be.charleshornick.supra.lib.cqs.core.Command;
import org.jspecify.annotations.Nullable;

public record CreateCharacterCommand(@Nullable String characterName) implements Command {

    public CreateCharacterCommand {
        characterName = (characterName != null) ? characterName.strip() : "";
    }
}
