package be.charleshornick.supra.facade.rest.define.race;

import be.charleshornick.supra.define.race.DefineRace;
import be.charleshornick.supra.lib.cqs.core.CommandHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Unit;

@Named
class DefineRaceHandler implements CommandHandler<DefineRaceCommand> {

    private final DefineRace defineRace;

    @Inject
    DefineRaceHandler(final DefineRace defineRace) {
        this.defineRace = defineRace;
    }

    @Override
    public Result<Unit> handle(final DefineRaceCommand command) {
        return this.defineRace
                .named(command.race())
                .toCharacterNamed(command.characterName())
                .mapToUnit();
    }

    @Override
    public Class<DefineRaceCommand> commandType() {
        return DefineRaceCommand.class;
    }
}
