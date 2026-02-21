package be.charleshornick.supra.shared.character.snapshot;

import be.charleshornick.supra.shared.profession.Profession;
import be.charleshornick.supra.shared.race.Race;

public record Character(
        int version,
        String name,
        Action action,
        Race race,
        Profession profession) implements Comparable<Character> {

    @Override
    public int compareTo(final Character snapshot) {
        return Integer.compare(this.version, snapshot.version);
    }
}
