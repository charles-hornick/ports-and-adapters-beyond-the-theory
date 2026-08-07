package be.charleshornick.supra.compendium.json;

import be.charleshornick.supra.chargen.define.profession.ForLoadingProfession;
import be.charleshornick.supra.chargen.profession.Profession;
import be.charleshornick.supra.chargen.profession.ProfessionName;
import be.charleshornick.supra.chargen.retrieve.profession.ForGettingProfession;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import org.jspecify.annotations.NonNull;
import org.pragmatica.lang.Option;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
class ProfessionProjectionLoader implements ForLoadingProfession, ForGettingProfession {

    private final Map<ProfessionName, Profession> professionMap = new HashMap<>();

    private final ObjectMapper objectMapper;

    ProfessionProjectionLoader(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    void loadData() {
        try (var in = getClass().getResourceAsStream("/data/professions.json")) {
            if (in == null) {
                throw new IllegalStateException("professions.json not found on classpath");
            }

            final ProfessionProjection[] professions = this.objectMapper.readValue(in, ProfessionProjection[].class);
            Arrays.stream(professions)
                    .map(ProfessionProjection::toCore)
                    .forEach(r -> this.professionMap.put(r.name(), r));
            
        } catch (final IOException e) {
            throw new IllegalStateException("Cannot load professions.json", e);
        }
    }

    @Override
    public List<Profession> details() {
        return List.copyOf(this.professionMap.values());
    }

    @Override
    @NonNull
    public Option<Profession> getProfessionDetails(@NonNull final ProfessionName professionName) {
        return Option.option(professionName)
                .map(this.professionMap::get);
    }
}
