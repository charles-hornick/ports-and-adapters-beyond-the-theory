package be.charleshornick.supra;

import be.charleshornick.supra.state.snapshot.Snapshot;
import org.pragmatica.lang.Result;

public interface ForStoringSnapshot {
    Result<Snapshot> store(Snapshot snapshot);
}
