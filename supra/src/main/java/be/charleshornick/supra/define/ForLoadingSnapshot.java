package be.charleshornick.supra.define;

import be.charleshornick.supra.state.snapshot.Snapshot;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;

public interface ForLoadingSnapshot {
    Result<Option<Snapshot>> getLastSnapshot(String characterName);
}
