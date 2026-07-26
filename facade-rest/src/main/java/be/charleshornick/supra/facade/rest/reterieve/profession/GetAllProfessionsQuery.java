package be.charleshornick.supra.facade.rest.reterieve.profession;

import be.charleshornick.supra.lib.cqs.core.Query;
import be.charleshornick.supra.profession.Profession;

import java.util.List;

record GetAllProfessionsQuery() implements Query<List<Profession>> {
}
