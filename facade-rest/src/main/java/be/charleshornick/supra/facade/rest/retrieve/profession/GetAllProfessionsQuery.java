package be.charleshornick.supra.facade.rest.retrieve.profession;

import be.charleshornick.supra.lib.cqs.core.Query;

import java.util.List;

record GetAllProfessionsQuery() implements Query<List<ProfessionProjection>> {
}
