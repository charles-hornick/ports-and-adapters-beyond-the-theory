package be.charleshornick.supra.define.profession;

import be.charleshornick.supra.profession.Profession;
import be.charleshornick.supra.profession.ProfessionName;
import org.pragmatica.lang.Option;

public interface ForLoadingProfession {
    Option<Profession> getProfessionDetails(ProfessionName professionName);
}
