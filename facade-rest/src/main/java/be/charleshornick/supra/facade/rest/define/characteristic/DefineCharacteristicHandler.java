package be.charleshornick.supra.facade.rest.define.characteristic;

import be.charleshornick.supra.chargen.define.characteristic.DefineCharacteristic;
import be.charleshornick.supra.lib.cqs.core.CommandHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Unit;

@Named
public class DefineCharacteristicHandler implements CommandHandler<DefineCharacteristicCommand> {

    private final DefineCharacteristic defineCharacteristic;

    @Inject
    public DefineCharacteristicHandler(final DefineCharacteristic defineCharacteristic) {
        this.defineCharacteristic = defineCharacteristic;
    }

    @Override
    public Result<Unit> handle(final DefineCharacteristicCommand command) {
        return this.performOperation(command.operation())
                .toCharacteristicNamed(command.characteristic())
                .toCharacterNamed(command.characterName())
                .mapToUnit();
    }

    private DefineCharacteristic.ToCharacteristic performOperation( final DefineCharacteristicCommand.PointOperation operation) {
        return switch (operation) {
            case ADD -> this.defineCharacteristic.byAddingOnePoint();
            case REMOVE -> this.defineCharacteristic.byRemovingOnePoint();
        };
    }

    @Override
    public Class<DefineCharacteristicCommand> commandType() {
        return DefineCharacteristicCommand.class;
    }
}
