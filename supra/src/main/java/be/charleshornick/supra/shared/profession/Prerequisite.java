package be.charleshornick.supra.shared.profession;

import be.charleshornick.supra.shared.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.shared.point.InvestedPoint;
import be.charleshornick.supra.shared.race.Race;
import be.charleshornick.supra.shared.race.RaceName;

import java.util.*;

public class Prerequisite {
    private final Map<PrimaryCharacteristicName, Integer> characteristicsToMatch;
    private final List<RaceName> forbiddenRace;

    private Prerequisite(final Map<PrimaryCharacteristicName, Integer> characteristics, final List<RaceName> races) {
        this.characteristicsToMatch = characteristics;
        this.forbiddenRace = races;
    }

    public static Prerequisite emptyPrerequisite() {
        return new Prerequisite(new HashMap<>(0), new ArrayList<>(0));
    }

    public static Prerequisite with(final Map<PrimaryCharacteristicName, Integer> characteristics, final List<RaceName> races) {
        return new Prerequisite(
                (characteristics != null) ? characteristics : new HashMap<>(),
                (races != null) ? races : new ArrayList<>()
        );
    }

    public boolean arePrerequisiteFulfilled(final Race race, final InvestedPoint investedPoint) {
        return this.isRaceAllowed(race.name()) &&
                this.areCharacteristicsMatched(investedPoint.computeWithRace());
    }

    private boolean isRaceAllowed(final RaceName name) {
        return !this.forbiddenRace.contains(name);
    }

    private boolean areCharacteristicsMatched(final Map<PrimaryCharacteristicName, Integer> characteristics) {
        return characteristics.entrySet().stream()
                .filter(e -> this.characteristicsToMatch.containsKey(e.getKey()))
                .allMatch(e -> this.characteristicsToMatch.get(e.getKey()) <= e.getValue());
    }

    public boolean isRaceForbidden(final Race race) {
        return this.forbiddenRace.contains(race.name());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Prerequisite that = (Prerequisite) o;
        return Objects.equals(this.characteristicsToMatch, that.characteristicsToMatch) &&
                Objects.equals(this.forbiddenRace, that.forbiddenRace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.characteristicsToMatch, this.forbiddenRace);
    }
}
