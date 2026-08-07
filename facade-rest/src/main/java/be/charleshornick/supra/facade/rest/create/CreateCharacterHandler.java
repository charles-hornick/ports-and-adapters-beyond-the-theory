package be.charleshornick.supra.facade.rest.create;

import be.charleshornick.supra.chargen.create.CreateCharacter;
import be.charleshornick.supra.lib.cqs.core.CommandHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Unit;

@Named
class CreateCharacterHandler implements CommandHandler<CreateCharacterCommand> {

    private final CreateCharacter createCharacter;

    @Inject
    CreateCharacterHandler(final CreateCharacter createCharacter) {
        this.createCharacter = createCharacter;
    }

    @Override
    public Result<Unit> handle(final CreateCharacterCommand command) {
        return this.createCharacter
                .named(command.characterName())
                .mapToUnit();
    }

    @Override
    public Class<CreateCharacterCommand> commandType() {
        return CreateCharacterCommand.class;
    }
}
