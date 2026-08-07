package be.charleshornick.supra.compendium.json;

import be.charleshornick.supra.chargen.define.race.ForLoadingRace;
import be.charleshornick.supra.chargen.race.Race;
import be.charleshornick.supra.chargen.race.RaceName;
import be.charleshornick.supra.chargen.retrieve.race.ForGettingRaces;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import org.jspecify.annotations.NonNull;
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
    @NonNull
    public Option<Race> getRaceDetails(@NonNull final RaceName raceName) {
        return Option.option(raceName)
                .map(this.raceProjections::get);
    }
}
