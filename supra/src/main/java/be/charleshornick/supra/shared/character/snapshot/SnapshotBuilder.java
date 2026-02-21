package be.charleshornick.supra.shared.character.snapshot;

import be.charleshornick.supra.shared.profession.Profession;
import be.charleshornick.supra.shared.race.Race;

public class SnapshotBuilder {
    private final int version;
    private final String name;
    private Race race;
    private Profession profession;

    public SnapshotBuilder(final Character snapshot) {
        this.version = snapshot.version() + 1;
        this.name = snapshot.name();
        this.race = snapshot.race();
        this.profession = snapshot.profession();
    }

    public static Character asFirstOne(final String name) {
        final var race = Race.undefined();
        return new Character(
                1,
                name,
                Action.CREATE_CHARACTER,
                race,
                Profession.undefined()
        );
    }

    public static SnapshotBuilder basedOnPreviousSnapshot(final Character snapshot) {
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

    public Character getForAction(final Action action) {
        return new Character(
                this.version,
                this.name,
                action,
                this.race,
                this.profession
        );
    }
}
