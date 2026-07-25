package be.charleshornick.supra.define.profession;

import be.charleshornick.supra.profession.Profession;
import be.charleshornick.supra.profession.ProfessionName;
import org.jspecify.annotations.NullMarked;
import org.pragmatica.lang.Option;

public interface ForLoadingProfession {

    @NullMarked
    Option<Profession> getProfessionDetails(ProfessionName professionName);
}
