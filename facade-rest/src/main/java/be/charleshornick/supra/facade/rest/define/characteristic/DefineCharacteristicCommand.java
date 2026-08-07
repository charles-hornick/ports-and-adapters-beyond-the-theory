package be.charleshornick.supra.facade.rest.define.characteristic;

import be.charleshornick.supra.chargen.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.chargen.fault.SupraCause;
import be.charleshornick.supra.lib.cqs.core.Command;
import org.jspecify.annotations.Nullable;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Verify;

import static be.charleshornick.supra.facade.rest.Wire.normalize;
import static be.charleshornick.supra.facade.rest.Wire.normalizeUpper;

public record DefineCharacteristicCommand(String characterName,
                                          PrimaryCharacteristicName characteristic,
                                          PointOperation operation) implements Command {

    public enum PointOperation { ADD, REMOVE }

    public static Result<DefineCharacteristicCommand> from(@Nullable final String characterName,
                                                           @Nullable final String characteristicName,
                                                           @Nullable final PointOperation operation) {
        final var name = normalize(characterName);
        final var characteristic = normalizeUpper(characteristicName);

        return Result.all(
                Verify.ensure(name, Verify.Is::notBlank, new SupraCause.InvalidInput("characterName", "blank")),
                parseCharacteristic(characteristic),
                Verify.ensure(operation, Verify.Is::notNull, new SupraCause.InvalidInput("operation", "missing"))
        ).map(DefineCharacteristicCommand::new);
    }

    private static Result<PrimaryCharacteristicName> parseCharacteristic(final String characteristic) {
        return Result.tryOf(
                () -> PrimaryCharacteristicName.valueOf(characteristic),
                _ -> new SupraCause.InvalidInput("characteristicName", "unknown")
        );
    }
}
