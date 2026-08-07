package be.charleshornick.supra.facade.rest.retrieve.profession;

import be.charleshornick.supra.lib.cqs.core.QueryHandler;
import be.charleshornick.supra.chargen.profession.Profession;
import be.charleshornick.supra.chargen.retrieve.profession.GetAllProfessions;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.pragmatica.lang.Result;

import java.util.List;

@Named
class GetAllProfessionsHandler implements QueryHandler<List<ProfessionProjection>, GetAllProfessionsQuery> {

    private final GetAllProfessions getAllProfessions;

    @Inject
    GetAllProfessionsHandler(final GetAllProfessions getAllProfessions) {
        this.getAllProfessions = getAllProfessions;
    }

    @Override
    public Result<List<ProfessionProjection>> handle(final GetAllProfessionsQuery query) {
        return Result.success(query)
                .map(_ -> this.getAllProfessions.details())
                .map(this::mapToProjection);
    }

    private List<ProfessionProjection> mapToProjection(final List<Profession> professions) {
        return professions.stream()
                .map(ProfessionProjection::from)
                .toList();
    }

    @Override
    public Class<GetAllProfessionsQuery> queryType() {
        return GetAllProfessionsQuery.class;
    }
}
