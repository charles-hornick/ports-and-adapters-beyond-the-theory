package be.charleshornick.supra.chargen.state.snapshot;

import be.charleshornick.supra.chargen.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.chargen.profession.Profession;
import be.charleshornick.supra.chargen.race.Race;
import be.charleshornick.supra.chargen.state.CreationPoint;

import java.time.LocalDateTime;
import java.util.Map;

public record Snapshot(
        int version,
        String name,
        Action action,
        LocalDateTime shotAt,
        Race race,
        Profession profession,
        Map<PrimaryCharacteristicName, Integer> investedPoints,
        CreationPoint creationPoints) implements Comparable<Snapshot> {

    public Snapshot {
        investedPoints = Map.copyOf(investedPoints);
    }

    public static Snapshot create(final int version,
                                  final String name,
                                  final Action action,
                                  final LocalDateTime shotAt,
                                  final Race race,
                                  final Profession profession,
                                  final Map<PrimaryCharacteristicName, Integer> investedPoints) {
        final var points = CreationPoint.beginning()
                .addNewConsumer(race)
                .addNewConsumer(profession);

        return new Snapshot(version, name, action, shotAt, race, profession, investedPoints, points);
    }

    public int pointsLeft() {
        return this.creationPoints.getPointsLeft();
    }

    @Override
    public int compareTo(final Snapshot o) {
        return Integer.compare(this.version, o.version);
    }
}
