package be.charleshornick.supra.facade.rest.define.profession;

import be.charleshornick.supra.chargen.define.profession.DefineProfession;
import be.charleshornick.supra.lib.cqs.core.CommandHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Unit;

@Named
class DefineProfessionHandler implements CommandHandler<DefineProfessionCommand> {

    private final DefineProfession defineProfession;

    @Inject
    DefineProfessionHandler(final DefineProfession defineProfession) {
        this.defineProfession = defineProfession;
    }

    @Override
    public Result<Unit> handle(final DefineProfessionCommand command) {
        return this.defineProfession
                .named(command.profession())
                .toCharacterNamed(command.characterName())
                .mapToUnit();
    }

    @Override
    public Class<DefineProfessionCommand> commandType() {
        return DefineProfessionCommand.class;
    }
}
