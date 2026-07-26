package be.charleshornick.supra.bootstrap.config;

import be.charleshornick.supra.ForStoringSnapshot;
import be.charleshornick.supra.create.CreateCharacter;
import be.charleshornick.supra.create.ForRegisteringName;
import be.charleshornick.supra.define.ForLoadingSnapshot;
import be.charleshornick.supra.define.characteristic.DefineCharacteristic;
import be.charleshornick.supra.define.profession.DefineProfession;
import be.charleshornick.supra.define.profession.ForLoadingProfession;
import be.charleshornick.supra.define.race.DefineRace;
import be.charleshornick.supra.define.race.ForLoadingRace;
import be.charleshornick.supra.retrieve.profession.ForGettingProfession;
import be.charleshornick.supra.retrieve.profession.GetAllProfessions;
import be.charleshornick.supra.retrieve.race.ForGettingRaces;
import be.charleshornick.supra.retrieve.race.GetAllRaces;
import be.charleshornick.supra.retrieve.snapshot.ForGettingSnapshot;
import be.charleshornick.supra.retrieve.snapshot.GetAllSnapshots;
import be.charleshornick.supra.retrieve.snapshot.GetLastestSnapshot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class UseCaseComposition {

    @Bean
    CreateCharacter createCharacter(final ForRegisteringName unicityChecker, final ForStoringSnapshot storage) {
        return new CreateCharacter(unicityChecker, storage);
    }

    @Bean
    DefineRace defineRace(final ForLoadingSnapshot snapshotLoader, final ForStoringSnapshot storage, final ForLoadingRace raceLoader) {
        return new DefineRace(snapshotLoader, storage, raceLoader);
    }

    @Bean
    DefineProfession defineProfession(final ForLoadingSnapshot snapshotLoader, final ForStoringSnapshot storage, final ForLoadingProfession professionLoader) {
        return new DefineProfession(snapshotLoader, storage, professionLoader);
    }

    @Bean
    DefineCharacteristic defineCharacteristic(final ForLoadingSnapshot loader, final ForStoringSnapshot storage) {
        return new DefineCharacteristic(loader, storage);
    }

    @Bean
    GetAllSnapshots getAllSnapshots(final ForGettingSnapshot storage) {
        return new GetAllSnapshots(storage);
    }

    @Bean
    GetLastestSnapshot getLastestSnapshot(final ForGettingSnapshot storage) {
        return new GetLastestSnapshot(storage);
    }

    @Bean
    GetAllRaces getAllRaces(final ForGettingRaces raceLoader) {
        return new GetAllRaces(raceLoader);
    }

    @Bean
    GetAllProfessions getAllProfessions(final ForGettingProfession professionLoader) {
        return new GetAllProfessions(professionLoader);
    }
}
