package be.charleshornick.supra.retrieve.snapshot;

import be.charleshornick.supra.state.snapshot.Snapshot;

import java.util.List;

public final class GetAllSnapshots {

    private final ForGettingSnapshot forGettingSnapshot;

    public GetAllSnapshots(final ForGettingSnapshot forGettingSnapshot) {
        this.forGettingSnapshot = forGettingSnapshot;
    }

    public List<Snapshot> forCharacterNamed(final String name) {
        return this.forGettingSnapshot.allOrdered(name);
    }
}
