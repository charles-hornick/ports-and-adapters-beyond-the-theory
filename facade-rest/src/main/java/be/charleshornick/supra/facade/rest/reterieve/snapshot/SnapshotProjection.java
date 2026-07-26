package be.charleshornick.supra.facade.rest.reterieve.snapshot;

import be.charleshornick.supra.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.state.snapshot.Snapshot;

import java.util.Map;

record SnapshotProjection(int version,
                          String name,
                          String shotAt,
                          String race,
                          String profession,
                          Map<PrimaryCharacteristicName, Integer> investedPoints,
                          int pointsLeft) {

    static SnapshotProjection from(final Snapshot snapshot) {
        return new SnapshotProjection(
                snapshot.version(),
                snapshot.name(),
                snapshot.shotAt().toString(),
                snapshot.race().name().name(),        // ou snapshot.raceId() si tu as ajouté le raccourci
                snapshot.profession().name().name(),
                snapshot.investedPoints(),
                snapshot.pointsLeft()
        );
    }
}
