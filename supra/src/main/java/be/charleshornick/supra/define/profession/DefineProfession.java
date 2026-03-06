package be.charleshornick.supra.define.profession;

import be.charleshornick.supra.define.ToCharacter;
import be.charleshornick.supra.define.ForLoadingSnapshot;
import be.charleshornick.supra.ForStoringSnapshot;
import be.charleshornick.supra.profession.ProfessionName;

public final class DefineProfession {

    private final ForLoadingSnapshot forLoadingSnapshot;
    private final ForStoringSnapshot forStoringSnapshot;
    private final ForLoadingProfession forLoadingProfession;

    public DefineProfession(final ForLoadingSnapshot forLoadingSnapshot, final ForStoringSnapshot forStoringSnapshot, final ForLoadingProfession forLoadingProfession) {
        this.forLoadingSnapshot = forLoadingSnapshot;
        this.forStoringSnapshot = forStoringSnapshot;
        this.forLoadingProfession = forLoadingProfession;
    }

    public ToCharacter named(final ProfessionName professionName) {
        return new DefineProfessionStep(
                this.forLoadingSnapshot,
                this.forStoringSnapshot,
                this.forLoadingProfession,
                professionName
        );
    }
}
