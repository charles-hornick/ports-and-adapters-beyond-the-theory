package be.charleshornick.supra.facade.rest.define.characteristic;

import org.pragmatica.lang.Result;

public record AllocationRequest(DefineCharacteristicCommand.PointOperation operation) {

    public Result<DefineCharacteristicCommand> toCommand(final String characterName, final String characteristicName) {
        return DefineCharacteristicCommand.from(characterName, characteristicName, operation);
    }
}

