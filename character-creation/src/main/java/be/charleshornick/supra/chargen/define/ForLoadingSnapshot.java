package be.charleshornick.supra.chargen.define;

import be.charleshornick.supra.chargen.state.snapshot.Snapshot;
import org.jspecify.annotations.NullMarked;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;

public interface ForLoadingSnapshot {

    @NullMarked
    Result<Option<Snapshot>> getLastSnapshot(String characterName);
}
