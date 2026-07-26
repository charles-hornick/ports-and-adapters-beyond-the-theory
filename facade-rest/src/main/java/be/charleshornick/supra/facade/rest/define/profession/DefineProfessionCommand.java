package be.charleshornick.supra.facade.rest.define.profession;

import be.charleshornick.supra.lib.cqs.core.Command;
import be.charleshornick.supra.profession.ProfessionName;
import org.jspecify.annotations.Nullable;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Verify;
import org.pragmatica.lang.utils.Causes;

import java.util.function.Predicate;

public record DefineProfessionCommand(@Nullable String characterName, @Nullable String professionName) implements Command {

    public DefineProfessionCommand {
        characterName = (characterName != null) ? characterName.strip() : "";
        professionName = (professionName != null) ? professionName.strip() : "";
    }

    public ProfessionName profession() {
        return ProfessionName.valueOf(this.professionName);
    }

    public Result<DefineProfessionCommand> validate() {
        return Result.all(
                Verify.ensure(this.characterName, Verify.Is::notBlank, Causes.cause("characterName.blank")),
                Verify.ensure(this.professionName, Verify.Is::notBlank, Causes.cause("professionName.blank")),
                Verify.ensure(this.professionName, isProfessionNameValid(), Causes.cause("professionName.invalid"))
        ).map((_, _, _) -> this);
    }

    private static Predicate<String> isProfessionNameValid() {
        return (name) -> {
            try {
                ProfessionName.valueOf(name);
                return true;
            } catch (final IllegalArgumentException e) {
                return false;
            }
        };
    }
}
