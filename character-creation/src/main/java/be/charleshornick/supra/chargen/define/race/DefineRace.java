package be.charleshornick.supra.chargen.define.race;

import be.charleshornick.supra.chargen.define.ToCharacter;
import be.charleshornick.supra.chargen.define.ForLoadingSnapshot;
import be.charleshornick.supra.chargen.ForStoringSnapshot;
import be.charleshornick.supra.chargen.race.RaceName;

public final class DefineRace {

    private final ForLoadingSnapshot forLoadingSnapshot;
    private final ForStoringSnapshot forStoringSnapshot;
    private final ForLoadingRace forLoadingRace;

    public DefineRace(final ForLoadingSnapshot forLoadingSnapshot, final ForStoringSnapshot forStoringSnapshot, final ForLoadingRace forLoadingRace) {
        this.forLoadingSnapshot = forLoadingSnapshot;
        this.forStoringSnapshot = forStoringSnapshot;
        this.forLoadingRace = forLoadingRace;
    }

    public ToCharacter named(final RaceName race) {
        return new DefineRaceStep(this.forLoadingSnapshot, this.forStoringSnapshot, this.forLoadingRace, race);
    }
}
