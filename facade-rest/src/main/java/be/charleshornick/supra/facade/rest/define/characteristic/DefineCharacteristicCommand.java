package be.charleshornick.supra.facade.rest.define.characteristic;

import be.charleshornick.supra.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.lib.cqs.core.Command;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Verify;
import org.pragmatica.lang.utils.Causes;

import java.util.Locale;
import java.util.function.Predicate;

public record DefineCharacteristicCommand(String characterName, String characteristicName, PointOperation operation) implements Command {

    public enum PointOperation { ADD, REMOVE }

    public DefineCharacteristicCommand {
        characterName = characterName.strip();
        characteristicName = characteristicName.strip().toUpperCase(Locale.ROOT);
    }

    public PrimaryCharacteristicName characteristic() {
        return PrimaryCharacteristicName.valueOf(this.characteristicName);
    }

    public Result<DefineCharacteristicCommand> validate() {
        return Result.all(
                Verify.ensure(this.characterName, Verify.Is::notBlank, Causes.cause("characterName.blank")),
                Verify.ensure(this.characteristicName, isCharacteristicValid(), Causes.cause("characteristic.blank")),
                Verify.ensure(this.operation, Verify.Is::notNull, Causes.cause("operation.missing"))
        ).map((_, _, _) -> this);
    }

    private static Predicate<String> isCharacteristicValid() {
        return (name) -> {
            try {
                PrimaryCharacteristicName.valueOf(name);
                return true;
            } catch (final IllegalArgumentException e) {
                return false;
            }
        };
    }
}
