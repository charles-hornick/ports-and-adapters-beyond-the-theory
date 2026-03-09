package be.charleshornick.supra.retrieve.race;

import be.charleshornick.supra.race.Race;
import be.charleshornick.supra.race.RaceName;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public final class GetAllRaces {

    private final ForGettingRaces forGettingRace;

    public GetAllRaces(final ForGettingRaces forGettingRace) {
        this.forGettingRace = forGettingRace;
    }

    public List<String> names() {
        return Arrays.stream(RaceName.values())
                .filter(Predicate.not(RaceName::isTechnical))
                .map(RaceName::name)
                .toList();
    }

    public List<Race> details() {
        return this.forGettingRace.details();
    }
}
