package be.charleshornick.supra.facade.rest.retrieve.race;

import be.charleshornick.supra.chargen.race.Race;

public record RaceProjection(String name, String description, int costInCreationPoint, boolean highRace) {

    static RaceProjection from(final Race race) {
        return new RaceProjection(
                race.name().name(),
                race.description(),
                race.costInCreationPoint(),
                race.highRace()
        );
    }
}
