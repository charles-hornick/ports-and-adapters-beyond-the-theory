package be.charleshornick.supra.chargen.define;

import be.charleshornick.supra.chargen.state.snapshot.Snapshot;
import org.pragmatica.lang.Result;

public interface ToCharacter {
    Result<Snapshot> toCharacterNamed(String name);
}
