package be.charleshornick.supra.character;

import be.charleshornick.supra.shared.character.snapshot.Snapshot;
import org.pragmatica.lang.Result;

public interface ForStoringSnapshot {
    Result<Snapshot> store(Snapshot snapshot);
}
