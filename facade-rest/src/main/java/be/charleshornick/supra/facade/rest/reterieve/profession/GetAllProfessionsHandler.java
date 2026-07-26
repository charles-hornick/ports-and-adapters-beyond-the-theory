package be.charleshornick.supra.facade.rest.reterieve.profession;

import be.charleshornick.supra.lib.cqs.core.QueryHandler;
import be.charleshornick.supra.profession.Profession;
import be.charleshornick.supra.retrieve.profession.GetAllProfessions;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.pragmatica.lang.Result;

import java.util.List;

@Named
class GetAllProfessionsHandler implements QueryHandler<List<Profession>, GetAllProfessionsQuery> {

    private final GetAllProfessions getAllProfessions;

    @Inject
    GetAllProfessionsHandler(final GetAllProfessions getAllProfessions) {
        this.getAllProfessions = getAllProfessions;
    }

    @Override
    public Result<List<Profession>> handle(final GetAllProfessionsQuery query) {
        return Result.ok(query)
                .map(_ -> this.getAllProfessions.details());
    }

    @Override
    public Class<GetAllProfessionsQuery> queryType() {
        return GetAllProfessionsQuery.class;
    }
}
