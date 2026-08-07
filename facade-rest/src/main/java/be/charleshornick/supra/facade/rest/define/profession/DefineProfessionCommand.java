package be.charleshornick.supra.facade.rest.define.profession;

import be.charleshornick.supra.chargen.fault.SupraCause;
import be.charleshornick.supra.lib.cqs.core.Command;
import be.charleshornick.supra.chargen.profession.ProfessionName;
import org.jspecify.annotations.Nullable;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Verify;

import static be.charleshornick.supra.facade.rest.Wire.normalize;
import static be.charleshornick.supra.facade.rest.Wire.normalizeUpper;

public record DefineProfessionCommand(String characterName, ProfessionName profession) implements Command {

    public static Result<DefineProfessionCommand> from(@Nullable final String characterName, @Nullable final String professionName) {
        final var name = normalize(characterName);
        final var profession = normalizeUpper(professionName);

        return Result.all(
                Verify.ensure(name, Verify.Is::notBlank, new SupraCause.InvalidInput("characterName", "blank")),
                parseProfession(profession)
        ).map(DefineProfessionCommand::new);
    }

    private static Result<ProfessionName> parseProfession(final String profession) {
        return Result.tryOf(
                () -> ProfessionName.valueOf(profession),
                _ -> new SupraCause.InvalidInput("professionName", "unknown")
        );
    }
}
