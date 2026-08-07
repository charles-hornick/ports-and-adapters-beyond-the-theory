package be.charleshornick.supra.chargen.retrieve.snapshot;

import be.charleshornick.supra.chargen.state.snapshot.Snapshot;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;

public final class GetLatestSnapshot {

    private final ForGettingSnapshot forGettingSnapshot;

    public GetLatestSnapshot(final ForGettingSnapshot forGettingSnapshot) {
        this.forGettingSnapshot = forGettingSnapshot;
    }

    public Result<Option<Snapshot>> forCharacterNamed(final String name) {
        return this.forGettingSnapshot.theLatest(name);
    }
}
