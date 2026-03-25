package be.charleshornick.supra.facace.test;

import be.charleshornick.supra.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.create.CreateCharacter;
import be.charleshornick.supra.define.characteristic.DefineCharacteristic;
import be.charleshornick.supra.define.profession.DefineProfession;
import be.charleshornick.supra.define.race.DefineRace;
import be.charleshornick.supra.retrieve.snapshot.GetLastestSnapshot;
import org.pragmatica.lang.Cause;
import org.pragmatica.lang.utils.Causes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static be.charleshornick.supra.characteristic.PrimaryCharacteristicName.*;
import static be.charleshornick.supra.profession.ProfessionName.ELF_ADVENTURER;
import static be.charleshornick.supra.profession.ProfessionName.KRAEN_WARRIOR;
import static be.charleshornick.supra.race.RaceName.*;

public class CreateFullCharacter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateFullCharacter.class);

    private final CreateCharacter createCharacter;
    private final DefineRace defineRace;
    private final DefineProfession defineProfession;
    private final DefineCharacteristic defineCharacteristic;
    private final GetLastestSnapshot getLastestSnapshot;

    final String characterName = "Borgrim";

    public CreateFullCharacter(final CreateCharacter createCharacter,
                               final DefineRace defineRace,
                               final DefineProfession defineProfession,
                               final DefineCharacteristic defineCharacteristic,
                               final GetLastestSnapshot getLastestSnapshot) {
        this.createCharacter = createCharacter;
        this.defineRace = defineRace;
        this.defineProfession = defineProfession;
        this.defineCharacteristic = defineCharacteristic;
        this.getLastestSnapshot = getLastestSnapshot;
    }

    public void run() {

        LOGGER.info("=== Character Creation Test ===");

        this.createCharacter
                .named(this.characterName)
                .onSuccess(s -> LOGGER.info("✓ Created: {}", s.name()))
                .onFailure(CreateFullCharacter::logError);

        this.defineRace
                .named(HIGH_ELF)
                .toCharacterNamed(this.characterName)
                .onSuccess(s -> LOGGER.info("✓ Race: {}", s.race().name()))
                .onFailure(CreateFullCharacter::logError);

        this.defineProfession
                .named(ELF_ADVENTURER)
                .toCharacterNamed(this.characterName)
                .onSuccess(s -> LOGGER.info("✓ Profession: {}", s.profession().name()))
                .onFailure(CreateFullCharacter::logError);

        this.defineRace
                .named(DWARF)
                .toCharacterNamed(this.characterName)
                .filter(Causes.cause("Profession is still set"), snapshot -> !snapshot.profession().isUndefined())
                .onSuccess(s -> {
                    LOGGER.info("✓ Race: {}", s.race().name());
                    LOGGER.info("✓ Profession: {}", s.profession().name());
                })
                .onFailure(CreateFullCharacter::logError);

        this.addPoint(COURAGE);
        this.addPoint(COURAGE);
        this.addPoint(COURAGE);
        this.addPoint(CONSTITUTION);
        this.addPoint(CONSTITUTION);
        this.addPoint(CONSTITUTION);
        this.addPoint(CONSTITUTION);
        this.addPoint(STRENGTH);
        this.addPoint(STRENGTH);
        this.addPoint(STRENGTH);
        this.addPoint(STRENGTH);

        this.defineProfession
                .named(KRAEN_WARRIOR)
                .toCharacterNamed(this.characterName)
                .onSuccess(s -> LOGGER.info("✓ Profession: {}", s.profession().name()))
                .onFailure(CreateFullCharacter::logError);

        this.getLastestSnapshot
                .forCharacterNamed(this.characterName)
                .toResult(Causes.cause("Failed to get lastest snapshot"))
                .onSuccess(snapshot -> LOGGER.info("{}", snapshot))
                .onFailure(CreateFullCharacter::logError);
    }

    private void addPoint(final PrimaryCharacteristicName characteristic) {
        this.defineCharacteristic
                .byAddingOnePoint()
                .toCharacteristicNamed(characteristic)
                .toCharacterNamed(this.characterName)
                .onSuccess(_ -> LOGGER.info("✓ Point added to {}", characteristic))
                .onFailure(CreateFullCharacter::logError);
    }

    private static void logError(Cause c) {
        LOGGER.info("✗ Failed: {}", c.message());
    }
}
