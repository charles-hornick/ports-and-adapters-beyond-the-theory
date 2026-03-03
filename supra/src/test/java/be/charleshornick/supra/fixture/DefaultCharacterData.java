package be.charleshornick.supra.fixture;

import be.charleshornick.supra.shared.point.InvestedPoint;
import be.charleshornick.supra.shared.profession.Profession;
import be.charleshornick.supra.shared.race.Race;

public interface DefaultCharacterData {
    String NAME = "Borgrim";
    Race RACE_UNDEFINED = Race.undefined();
    Profession PROFESSION_UNDEFINED = Profession.undefined();
    InvestedPoint INVESTED_POINT = InvestedPoint.beginning(RACE_UNDEFINED);
}
