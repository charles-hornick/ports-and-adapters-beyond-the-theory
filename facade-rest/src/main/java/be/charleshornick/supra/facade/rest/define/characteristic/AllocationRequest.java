package be.charleshornick.supra.facade.rest.define.characteristic;

public record AllocationRequest(DefineCharacteristicCommand.PointOperation operation) {

    public DefineCharacteristicCommand toCommand(final String characterName, final String characteristicName) {
        return new DefineCharacteristicCommand(characterName, characteristicName, operation);
    }
}

