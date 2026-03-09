package be.charleshornick.supra.retrieve.snapshot;

import be.charleshornick.supra.snapshot.Snapshot;
import org.pragmatica.lang.Option;

public class GetLastestSnapshot {

    private final ForGettingSnapshot forGettingSnapshot;

    public GetLastestSnapshot(final ForGettingSnapshot forGettingSnapshot) {
        this.forGettingSnapshot = forGettingSnapshot;
    }

    public Option<Snapshot> forCharacterNamed(final String name) {
        return this.forGettingSnapshot.theLastest(name);
    }
}
