package be.charleshornick.supra.compendium.json;

import be.charleshornick.supra.define.race.ForLoadingRace;
import be.charleshornick.supra.race.Race;
import be.charleshornick.supra.race.RaceName;
import be.charleshornick.supra.retrieve.race.ForGettingRaces;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import org.pragmatica.lang.Option;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

@Named
class RacesProjectionLoader implements ForLoadingRace, ForGettingRaces {

    private final Map<RaceName, Race> raceProjections = new HashMap<>();

    private final ObjectMapper objectMapper;

    RacesProjectionLoader(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    void loadData() {
        try (var in = getClass().getResourceAsStream("/data/races.json")) {
            if (in == null) {
                throw new IllegalStateException("races.json not found on classpath");
            }

            final RaceProjection[] races = this.objectMapper.readValue(in, RaceProjection[].class);
            Arrays.stream(races)
                    .map(RaceProjection::toCore)
                    .forEach(r -> this.raceProjections.put(r.name(), r));

        } catch (final IOException e) {
            throw new IllegalStateException("Cannot load races.json", e);
        }
    }

    @Override
    public List<Race> details() {
        return List.copyOf(this.raceProjections.values());
    }

    @Override
    @Nonnull
    public Option<Race> getRaceDetails(@Nonnull final RaceName raceName) {
        return Option.option(raceName)
                .map(this.raceProjections::get);
    }
}
