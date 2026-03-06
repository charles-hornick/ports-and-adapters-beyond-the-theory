package be.charleshornick.supra.character.define.race;

import be.charleshornick.supra.character.define.ToCharacter;
import be.charleshornick.supra.character.ForLoadingSnapshot;
import be.charleshornick.supra.character.ForStoringSnapshot;
import be.charleshornick.supra.shared.race.RaceName;

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
