package be.charleshornick.supra.character.define.race;

import be.charleshornick.supra.character.port.ForLoadingRace;
import be.charleshornick.supra.character.port.ForLoadingSnapshot;
import be.charleshornick.supra.character.port.ForStoringSnapshot;
import be.charleshornick.supra.shared.character.snapshot.Snapshot;
import be.charleshornick.supra.shared.race.RaceName;
import org.pragmatica.lang.Result;

public class DefineRace {

    public interface ToCharacter {
        Result<Snapshot> toCharacterNamed(String name);
    }

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
