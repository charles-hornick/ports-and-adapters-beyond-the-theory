package be.charleshornick.supra.facade.batch.create;

import be.charleshornick.supra.lib.cqs.core.Command;
import org.jspecify.annotations.Nullable;

public record ImportCharacterCommand(@Nullable String characterName) implements Command {

    public ImportCharacterCommand {
        characterName = (characterName != null) ? characterName.strip() : "";
    }
}
