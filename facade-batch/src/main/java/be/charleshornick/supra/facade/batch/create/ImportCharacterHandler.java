package be.charleshornick.supra.facade.batch.create;

import be.charleshornick.supra.chargen.create.CreateCharacter;
import be.charleshornick.supra.lib.cqs.core.CommandHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Unit;

@Named
class ImportCharacterHandler implements CommandHandler<ImportCharacterCommand> {

    private final CreateCharacter createCharacter;

    @Inject
    ImportCharacterHandler(final CreateCharacter createCharacter) {
        this.createCharacter = createCharacter;
    }

    @Override
    public Result<Unit> handle(final ImportCharacterCommand command) {
        return this.createCharacter
                .named(command.characterName())
                .mapToUnit();
    }

    @Override
    public Class<ImportCharacterCommand> commandType() {
        return ImportCharacterCommand.class;
    }
}
