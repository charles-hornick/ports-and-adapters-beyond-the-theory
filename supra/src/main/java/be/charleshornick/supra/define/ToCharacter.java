package be.charleshornick.supra.define;

import be.charleshornick.supra.state.snapshot.Snapshot;
import org.pragmatica.lang.Result;

public interface ToCharacter {
    Result<Snapshot> toCharacterNamed(String name);
}
