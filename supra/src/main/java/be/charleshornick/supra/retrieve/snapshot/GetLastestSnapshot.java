package be.charleshornick.supra.retrieve.snapshot;

import be.charleshornick.supra.state.snapshot.Snapshot;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;

public final class GetLastestSnapshot {

    private final ForGettingSnapshot forGettingSnapshot;

    public GetLastestSnapshot(final ForGettingSnapshot forGettingSnapshot) {
        this.forGettingSnapshot = forGettingSnapshot;
    }

    public Result<Option<Snapshot>> forCharacterNamed(final String name) {
        return this.forGettingSnapshot.theLatest(name);
    }
}
