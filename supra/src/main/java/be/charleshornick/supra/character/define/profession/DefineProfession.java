package be.charleshornick.supra.character.define.profession;

import be.charleshornick.supra.character.define.ToCharacter;
import be.charleshornick.supra.character.port.ForLoadingProfession;
import be.charleshornick.supra.character.port.ForLoadingSnapshot;
import be.charleshornick.supra.character.port.ForStoringSnapshot;
import be.charleshornick.supra.shared.profession.ProfessionName;

public class DefineProfession {

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
