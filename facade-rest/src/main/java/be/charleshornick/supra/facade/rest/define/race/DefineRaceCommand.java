package be.charleshornick.supra.facade.rest.define.race;

import be.charleshornick.supra.lib.cqs.core.Command;
import be.charleshornick.supra.race.RaceName;
import org.jspecify.annotations.Nullable;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Verify;
import org.pragmatica.lang.utils.Causes;

import java.util.Locale;
import java.util.function.Predicate;

public record DefineRaceCommand(@Nullable String characterName, @Nullable String raceName) implements Command {

    public DefineRaceCommand {
        characterName = (characterName != null) ? characterName.strip() : "";
        raceName = (raceName != null) ? raceName.strip().toUpperCase(Locale.ROOT) : "";
    }

    public RaceName race() {
        return RaceName.valueOf(this.raceName);
    }

    public Result<DefineRaceCommand> validate() {
        return Result.all(
                Verify.ensure(this.characterName, Verify.Is::notBlank, Causes.cause("characterName.blank")),
                Verify.ensure(this.raceName, Verify.Is::notBlank, Causes.cause("raceName.blank")),
                Verify.ensure(this.raceName, isRaceNameValid(), Causes.cause("raceName.invalid"))
        ).map((_, _, _) -> this);
    }

    private static Predicate<String> isRaceNameValid() {
        return (name) -> {
            try {
                RaceName.valueOf(name);
                return true;
            } catch (final IllegalArgumentException e) {
                return false;
            }
        };
    }
}
