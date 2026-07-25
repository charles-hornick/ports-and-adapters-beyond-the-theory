package be.charleshornick.supra;

import be.charleshornick.supra.state.snapshot.Snapshot;
import org.jspecify.annotations.NullMarked;
import org.pragmatica.lang.Result;

public interface ForStoringSnapshot {

    @NullMarked
    Result<Snapshot> store(Snapshot snapshot);
}
