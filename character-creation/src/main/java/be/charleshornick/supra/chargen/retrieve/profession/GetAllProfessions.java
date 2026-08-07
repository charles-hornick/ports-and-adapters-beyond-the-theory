package be.charleshornick.supra.chargen.retrieve.profession;

import be.charleshornick.supra.chargen.profession.Profession;

import java.util.List;

public final class GetAllProfessions {

    private final ForGettingProfession forGettingProfession;

    public GetAllProfessions(final ForGettingProfession forGettingProfession) {
        this.forGettingProfession = forGettingProfession;
    }

    public List<Profession> details() {
        return this.forGettingProfession.details();
    }
}
