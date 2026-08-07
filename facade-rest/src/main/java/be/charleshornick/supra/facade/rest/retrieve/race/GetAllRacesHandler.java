package be.charleshornick.supra.facade.rest.retrieve.race;

import be.charleshornick.supra.lib.cqs.core.QueryHandler;
import be.charleshornick.supra.chargen.race.Race;
import be.charleshornick.supra.chargen.retrieve.race.GetAllRaces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.pragmatica.lang.Result;

import java.util.List;

@Named
class GetAllRacesHandler implements QueryHandler<List<RaceProjection>, GetAllRacesQuery> {

    private final GetAllRaces getAllRaces;

    @Inject
    GetAllRacesHandler(final GetAllRaces getAllRaces) {
        this.getAllRaces = getAllRaces;
    }

    @Override
    public Result<List<RaceProjection>> handle(final GetAllRacesQuery query) {
        return Result.success(query)
                .map(_ -> this.getAllRaces.details())
                .map(this::mapToProjection);
    }

    private List<RaceProjection> mapToProjection(final List<Race> races) {
        return races.stream()
                .map(RaceProjection::from)
                .toList();
    }

    @Override
    public Class<GetAllRacesQuery> queryType() {
        return GetAllRacesQuery.class;
    }
}
