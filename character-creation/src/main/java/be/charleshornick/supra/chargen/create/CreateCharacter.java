package be.charleshornick.supra.chargen.create;

import be.charleshornick.supra.chargen.ForStoringSnapshot;
import be.charleshornick.supra.chargen.state.snapshot.Snapshot;
import org.pragmatica.lang.Result;

public final class CreateCharacter {

    private final ForRegisteringName forRegisteringName;
    private final ForStoringSnapshot forStoringSnapshot;

    public CreateCharacter(final ForRegisteringName forRegisteringName, final ForStoringSnapshot forStoringSnapshot) {
        this.forRegisteringName = forRegisteringName;
        this.forStoringSnapshot = forStoringSnapshot;
    }

    public Result<Snapshot> named(final String characterName) {
        return new CharacterNameRegister(this.forRegisteringName)
                .registering(characterName)
                .map(be.charleshornick.supra.chargen.create.Character::withName)
                .map(Character::doSnapshot)
                .flatMap(this.forStoringSnapshot::store);
    }
}
