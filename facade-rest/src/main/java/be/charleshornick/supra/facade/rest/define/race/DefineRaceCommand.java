package be.charleshornick.supra.facade.rest.define.race;

import be.charleshornick.supra.chargen.fault.SupraCause;
import be.charleshornick.supra.lib.cqs.core.Command;
import be.charleshornick.supra.chargen.race.RaceName;
import org.jspecify.annotations.Nullable;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Verify;

import static be.charleshornick.supra.facade.rest.Wire.*;

public record DefineRaceCommand(String characterName, RaceName race) implements Command {

    public static Result<DefineRaceCommand> from(@Nullable final String characterName, @Nullable final String raceName) {
        final var name = normalize(characterName);
        final var race = normalizeUpper(raceName);

        return Result.all(
                Verify.ensure(name, Verify.Is::notBlank, new SupraCause.InvalidInput("characterName", "blank")),
                parseRace(race)
        ).map(DefineRaceCommand::new);
    }

    private static Result<RaceName> parseRace(final String race) {
        return Result.tryOf(
                () -> RaceName.valueOf(race),
                _ -> new SupraCause.InvalidInput("raceName", "unknown")
        );
    }
}
