package be.charleshornick.supra.chargen;

import be.charleshornick.supra.chargen.state.snapshot.Snapshot;
import org.jspecify.annotations.NullMarked;
import org.pragmatica.lang.Result;

public interface ForStoringSnapshot {

    @NullMarked
    Result<Snapshot> store(Snapshot snapshot);
}
