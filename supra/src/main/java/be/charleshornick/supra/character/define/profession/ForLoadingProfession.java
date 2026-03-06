package be.charleshornick.supra.character.define.profession;

import be.charleshornick.supra.shared.profession.Profession;
import be.charleshornick.supra.shared.profession.ProfessionName;
import org.pragmatica.lang.Option;

public interface ForLoadingProfession {
    Option<Profession> getProfessionDetails(ProfessionName raceName);
}
