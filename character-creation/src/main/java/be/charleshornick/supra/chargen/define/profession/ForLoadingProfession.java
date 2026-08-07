package be.charleshornick.supra.chargen.define.profession;

import be.charleshornick.supra.chargen.profession.Profession;
import be.charleshornick.supra.chargen.profession.ProfessionName;
import org.jspecify.annotations.NullMarked;
import org.pragmatica.lang.Option;

public interface ForLoadingProfession {

    @NullMarked
    Option<Profession> getProfessionDetails(ProfessionName professionName);
}
