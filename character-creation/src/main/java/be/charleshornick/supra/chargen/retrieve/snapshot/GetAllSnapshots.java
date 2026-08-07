package be.charleshornick.supra.chargen.retrieve.snapshot;

import be.charleshornick.supra.chargen.state.snapshot.Snapshot;
import org.pragmatica.lang.Result;

import java.util.List;

public final class GetAllSnapshots {

    private final ForGettingSnapshot forGettingSnapshot;

    public GetAllSnapshots(final ForGettingSnapshot forGettingSnapshot) {
        this.forGettingSnapshot = forGettingSnapshot;
    }

    public Result<List<Snapshot>> forCharacterNamed(final String name) {
        return this.forGettingSnapshot.allOrdered(name);
    }
}
