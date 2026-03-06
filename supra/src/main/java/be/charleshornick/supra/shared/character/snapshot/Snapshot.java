package be.charleshornick.supra.shared.character.snapshot;

import be.charleshornick.supra.shared.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.shared.point.CreationPoint;
import be.charleshornick.supra.shared.profession.Profession;
import be.charleshornick.supra.shared.race.Race;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class Snapshot implements Comparable<Snapshot> {

    private final int version;
    private final LocalDateTime shotAt = LocalDateTime.now();
    private final Action action;
    private final String name;
    private final CreationPoint creationPoints;
    private final Race race;
    private final Profession profession;
    private final Map<PrimaryCharacteristicName, Integer> investedPoints;

    Snapshot(final int version, final String name, final Action action, final Race race, final Profession profession, final Map<PrimaryCharacteristicName, Integer> investedPoints) {
        this.version = version;
        this.name = name;
        this.action = action;
        this.race = race;
        this.profession = profession;
        this.investedPoints = Map.copyOf(investedPoints);
        this.creationPoints = CreationPoint.beginning()
                .addNewConsumer(this.race)
                .addNewConsumer(this.profession);
    }

    public int version() {
        return this.version;
    }

    public String name() {
        return this.name;
    }

    public int getPointsLeft() {
        return this.creationPoints.getPointsLeft();
    }

    public Race race() {
        return this.race;
    }

    public Profession profession() {
        return this.profession;
    }

    public Map<PrimaryCharacteristicName, Integer> investedPoints() {
        return this.investedPoints;
    }

    @Override
    public int compareTo(final Snapshot snapshot) {
        return Integer.compare(this.version, snapshot.version);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Snapshot snapshot)) return false;
        return this.version == snapshot.version;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.version);
    }

    @Override
    public String toString() {
        return "Snapshot{" +
                "version=" + version +
                ", shotAt=" + shotAt +
                ", action=" + action +
                ", name='" + name + '\'' +
                ", creationPoints=" + creationPoints +
                ", race=" + race +
                ", profession=" + profession +
                ", investedPoints=" + investedPoints +
                '}';
    }
}
