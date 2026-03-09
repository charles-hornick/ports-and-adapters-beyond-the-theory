package be.charleshornick.supra.retrieve.profession;

import be.charleshornick.supra.profession.Profession;
import be.charleshornick.supra.profession.ProfessionName;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public final class GetAllProfessions {

    private final ForGettingProfession forGettingProfession;

    public GetAllProfessions(final ForGettingProfession forGettingProfession) {
        this.forGettingProfession = forGettingProfession;
    }

    public List<String> names() {
        return Arrays.stream(ProfessionName.values())
                .filter(Predicate.not(ProfessionName::isTechnical))
                .map(ProfessionName::name)
                .toList();
    }

    public List<Profession> details() {
        return this.forGettingProfession.details();
    }
}
