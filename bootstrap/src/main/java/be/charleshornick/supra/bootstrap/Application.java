package be.charleshornick.supra.bootstrap;

import be.charleshornick.supra.create.CreateCharacter;
import be.charleshornick.supra.define.characteristic.DefineCharacteristic;
import be.charleshornick.supra.define.profession.DefineProfession;
import be.charleshornick.supra.define.race.DefineRace;
import be.charleshornick.supra.facade.test.CreateFullCharacter;
import be.charleshornick.supra.retrieve.snapshot.GetAllSnapshots;
import be.charleshornick.supra.retrieve.snapshot.GetLastestSnapshot;

public class Application {

    void main() {
        final var raceStorage = new InMemoryRaceStorage();
        final var professionStorage = new InMemoryProfessionStorage();
        final var snapshotStorage = new InMemorySnapshotStorage();

        final var createCharacter = new CreateCharacter(_ -> true, snapshotStorage);
        final var defineRace = new DefineRace(snapshotStorage, snapshotStorage, raceStorage);
        final var defineProfession = new DefineProfession(snapshotStorage, snapshotStorage, professionStorage);
        final var defineCharacteristic = new DefineCharacteristic(snapshotStorage, snapshotStorage);
        final var getLastestSnapshot = new GetLastestSnapshot(snapshotStorage);
        final var getAllSnapshots = new GetAllSnapshots(snapshotStorage);

        final var testAdapter = new CreateFullCharacter(
                createCharacter,
                defineRace,
                defineProfession,
                defineCharacteristic,
                getLastestSnapshot,
                getAllSnapshots
        );

        testAdapter.run();
    }
}
