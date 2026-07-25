package be.charleshornick.supra.define;

import be.charleshornick.supra.state.snapshot.Snapshot;
import org.jspecify.annotations.NullMarked;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;

public interface ForLoadingSnapshot {

    @NullMarked
    Result<Option<Snapshot>> getLastSnapshot(String characterName);
}
