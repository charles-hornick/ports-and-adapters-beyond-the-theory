package be.charleshornick.supra.shared.character.snapshot;

import be.charleshornick.supra.shared.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.shared.point.InvestedPoint;
import be.charleshornick.supra.shared.profession.Profession;
import be.charleshornick.supra.shared.race.Race;

import java.util.HashMap;
import java.util.Map;

public class SnapshotBuilder {

    private final int version;
    private final String name;
    private Race race;
    private Profession profession;
    private Map<PrimaryCharacteristicName, Integer> investedPoints;

    private SnapshotBuilder(final Snapshot snapshot) {
        this.version = snapshot.version() + 1;
        this.name = snapshot.name();
        this.race = snapshot.race();
        this.profession = snapshot.profession();
        this.investedPoints = snapshot.investedPoints();
    }

    public static Snapshot asFirstOne(final String name) {
        final var race = Race.undefined();
        return new Snapshot(
                1,
                name,
                Action.CREATE_CHARACTER,
                race,
                Profession.undefined(),
                new HashMap<>()
        );
    }

    public static SnapshotBuilder basedOnPreviousSnapshot(final Snapshot snapshot) {
        return new SnapshotBuilder(snapshot);
    }

    public SnapshotBuilder updateRaceWith(final Race race) {
        this.race = race;
        return this;
    }

    public SnapshotBuilder updateProfessionWith(final Profession profession) {
        this.profession = profession;
        return this;
    }

    public SnapshotBuilder updateInvestedPointsWith(final InvestedPoint investedPoints) {
        this.investedPoints = investedPoints.getInvestedPoints();
        return this;
    }

    public Snapshot getForAction(final Action action) {
        return new Snapshot(
                this.version,
                this.name,
                action,
                this.race,
                this.profession,
                this.investedPoints
        );
    }
}
