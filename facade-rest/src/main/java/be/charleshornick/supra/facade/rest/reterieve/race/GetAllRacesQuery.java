package be.charleshornick.supra.facade.rest.reterieve.race;

import be.charleshornick.supra.lib.cqs.core.Query;
import be.charleshornick.supra.race.Race;

import java.util.List;

record GetAllRacesQuery() implements Query<List<Race>> {
}
