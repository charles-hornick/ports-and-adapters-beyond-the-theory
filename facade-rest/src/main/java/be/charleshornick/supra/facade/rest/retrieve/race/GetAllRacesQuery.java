package be.charleshornick.supra.facade.rest.retrieve.race;

import be.charleshornick.supra.lib.cqs.core.Query;

import java.util.List;

record GetAllRacesQuery() implements Query<List<RaceProjection>> {
}
