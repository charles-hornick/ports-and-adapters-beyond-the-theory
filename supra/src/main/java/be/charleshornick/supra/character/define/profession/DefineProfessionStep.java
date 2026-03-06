package be.charleshornick.supra.character.define.profession;

import be.charleshornick.supra.character.define.ToCharacter;
import be.charleshornick.supra.character.ForLoadingSnapshot;
import be.charleshornick.supra.character.ForStoringSnapshot;
import be.charleshornick.supra.ErrorCause;
import be.charleshornick.supra.shared.character.snapshot.Snapshot;
import be.charleshornick.supra.shared.profession.Profession;
import be.charleshornick.supra.shared.profession.ProfessionName;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Tuple;

final class DefineProfessionStep implements ToCharacter {

    private final ForLoadingSnapshot forLoadingSnapshot;
    private final ForStoringSnapshot forStoringSnapshot;
    private final ForLoadingProfession forLoadingProfession;
    private final ProfessionName professionName;

    DefineProfessionStep(final ForLoadingSnapshot forLoadingSnapshot,
                                final ForStoringSnapshot forStoringSnapshot,
                                final ForLoadingProfession forLoadingProfession,
                                final ProfessionName professionName) {
        this.forLoadingSnapshot = forLoadingSnapshot;
        this.forStoringSnapshot = forStoringSnapshot;
        this.forLoadingProfession = forLoadingProfession;
        this.professionName = professionName;
    }

    @Override
    public Result<Snapshot> toCharacterNamed(final String name) {
        final var character = this.getSnapshot(name);
        final var profession = this.getProfession(this.professionName);

        return Result.all(character, profession)
                .id()
                .flatMap(this::applyProfession)
                .map(Character::doSnapshot)
                .flatMap(this.forStoringSnapshot::store);
    }

    private Result<Snapshot> getSnapshot(final String characterName) {
        return this.forLoadingSnapshot
                .getLastSnapshot(characterName)
                .toResult(ErrorCause.CHARACTER_DOES_NOT_EXIST);
    }

    private Result<Profession> getProfession(final ProfessionName professionName) {
        return Option.some(professionName)
                .flatMap(this.forLoadingProfession::getProfessionDetails)
                .toResult(ErrorCause.PROFESSION_DOES_NOT_EXIST);
    }

    private Result<Character> applyProfession(final Tuple.Tuple2<Snapshot, Profession> data) {
        final var snapshot = data.first();
        final var profession = data.last();

        return Character.load(snapshot).defineProfession(profession);
    }
}
