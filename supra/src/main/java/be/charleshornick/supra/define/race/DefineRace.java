package be.charleshornick.supra.define.race;

import be.charleshornick.supra.define.ToCharacter;
import be.charleshornick.supra.define.ForLoadingSnapshot;
import be.charleshornick.supra.ForStoringSnapshot;
import be.charleshornick.supra.race.RaceName;

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
