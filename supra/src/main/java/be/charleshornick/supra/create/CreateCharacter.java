package be.charleshornick.supra.create;

import be.charleshornick.supra.ForStoringSnapshot;
import be.charleshornick.supra.state.snapshot.Snapshot;
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
                .map(Character::withName)
                .map(Character::doSnapshot)
                .flatMap(this.forStoringSnapshot::store);
    }
}
