package be.charleshornick.supra.profession;

import be.charleshornick.supra.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.snapshot.InvestedPoint;
import be.charleshornick.supra.race.Race;
import be.charleshornick.supra.race.RaceName;

import java.util.*;

public record Prerequisite(Map<PrimaryCharacteristicName, Integer> characteristicsToMatch, List<RaceName> forbiddenRace) {

    public Prerequisite {
        characteristicsToMatch = (characteristicsToMatch != null) ? Map.copyOf(characteristicsToMatch) : Map.of();
        forbiddenRace = (forbiddenRace != null) ? List.copyOf(forbiddenRace) : List.of();
    }

    public static Prerequisite emptyPrerequisite() {
        return new Prerequisite(Map.of(), List.of());
    }

    public static Prerequisite with(final Map<PrimaryCharacteristicName, Integer> characteristics, final List<RaceName> races) {
        return new Prerequisite(
                (characteristics != null) ? characteristics : Map.of(),
                (races != null) ? races : List.of()
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
}
