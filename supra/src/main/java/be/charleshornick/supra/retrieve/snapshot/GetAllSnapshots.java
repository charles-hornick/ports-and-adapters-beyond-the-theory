package be.charleshornick.supra.retrieve.snapshot;

import be.charleshornick.supra.snapshot.Snapshot;

import java.util.List;

public class GetAllSnapshots {

    private final ForGettingSnapshot forGettingSnapshot;

    public GetAllSnapshots(final ForGettingSnapshot forGettingSnapshot) {
        this.forGettingSnapshot = forGettingSnapshot;
    }

    List<Snapshot> forCharacterNamed(final String name) {
        return this.forGettingSnapshot.allOrdered(name);
    }
}
