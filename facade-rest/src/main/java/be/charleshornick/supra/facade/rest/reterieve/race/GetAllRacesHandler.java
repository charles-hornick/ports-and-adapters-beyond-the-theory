package be.charleshornick.supra.facade.rest.reterieve.race;

import be.charleshornick.supra.lib.cqs.core.QueryHandler;
import be.charleshornick.supra.race.Race;
import be.charleshornick.supra.retrieve.race.GetAllRaces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.pragmatica.lang.Result;

import java.util.List;

@Named
class GetAllRacesHandler implements QueryHandler<List<Race>, GetAllRacesQuery> {

    private final GetAllRaces getAllRaces;

    @Inject
    GetAllRacesHandler(final GetAllRaces getAllRaces) {
        this.getAllRaces = getAllRaces;
    }

    @Override
    public Result<List<Race>> handle(final GetAllRacesQuery query) {
        return Result.ok(query)
                .map(_ -> this.getAllRaces.details());
    }

    @Override
    public Class<GetAllRacesQuery> queryType() {
        return GetAllRacesQuery.class;
    }
}
