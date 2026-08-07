package be.charleshornick.supra.chargen.define.race;

import be.charleshornick.supra.chargen.define.ToCharacter;
import be.charleshornick.supra.chargen.define.ForLoadingSnapshot;
import be.charleshornick.supra.chargen.ForStoringSnapshot;
import be.charleshornick.supra.chargen.fault.ErrorCause;
import be.charleshornick.supra.chargen.fault.SupraCause;
import be.charleshornick.supra.chargen.state.snapshot.Snapshot;
import be.charleshornick.supra.chargen.race.Race;
import be.charleshornick.supra.chargen.race.RaceName;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Tuple;

final class DefineRaceStep implements ToCharacter {

    private final ForLoadingSnapshot forLoadingSnapshot;
    private final ForStoringSnapshot forStoringSnapshot;
    private final ForLoadingRace forLoadingRace;
    private final RaceName race;

    DefineRaceStep(final ForLoadingSnapshot forLoadingSnapshot, final ForStoringSnapshot forStoringSnapshot, final ForLoadingRace forLoadingRace, final RaceName race) {
        this.forLoadingSnapshot = forLoadingSnapshot;
        this.forStoringSnapshot = forStoringSnapshot;
        this.forLoadingRace = forLoadingRace;
        this.race = race;
    }

    @Override
    public Result<Snapshot> toCharacterNamed(final String name) {
        final var character = this.getSnapshot(name);
        final var race = this.getRace(this.race);

        return Result.all(character, race)
                .id()
                .flatMap(this::applyRace)
                .map(be.charleshornick.supra.chargen.define.race.Character::doSnapshot)
                .flatMap(this.forStoringSnapshot::store);
    }

    private Result<Snapshot> getSnapshot(final String characterName) {
        return this.forLoadingSnapshot
                .getLastSnapshot(characterName)
                .flatMap(opt -> opt.toResult(new SupraCause.NotFound("character", characterName)));
    }

    private Result<Race> getRace(final RaceName raceName) {
        return Option.some(raceName)
                .flatMap(this.forLoadingRace::getRaceDetails)
                .toResult(ErrorCause.RACE_DOES_NOT_EXIST);
    }

    private Result<be.charleshornick.supra.chargen.define.race.Character> applyRace(final Tuple.Tuple2<Snapshot, Race> data) {
        final var snapshot = data.first();
        final var race = data.last();

        return Character.load(snapshot).defineRace(race);
    }
}
