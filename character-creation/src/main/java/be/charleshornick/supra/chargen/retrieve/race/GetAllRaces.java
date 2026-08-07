package be.charleshornick.supra.chargen.retrieve.race;

import be.charleshornick.supra.chargen.race.Race;

import java.util.List;

public final class GetAllRaces {

    private final ForGettingRaces forGettingRace;

    public GetAllRaces(final ForGettingRaces forGettingRace) {
        this.forGettingRace = forGettingRace;
    }

    public List<Race> details() {
        return this.forGettingRace.details();
    }
}
