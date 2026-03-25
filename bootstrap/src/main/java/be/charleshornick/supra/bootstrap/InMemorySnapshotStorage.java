package be.charleshornick.supra.bootstrap;

import be.charleshornick.supra.ForStoringSnapshot;
import be.charleshornick.supra.define.ForLoadingSnapshot;
import be.charleshornick.supra.retrieve.snapshot.ForGettingSnapshot;
import be.charleshornick.supra.state.snapshot.Snapshot;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;

import java.util.*;

public class InMemorySnapshotStorage implements ForLoadingSnapshot, ForStoringSnapshot, ForGettingSnapshot {

    private final Map<String, TreeSet<Snapshot>> store = new HashMap<>();

    @Override
    public Option<Snapshot> getLastSnapshot(final String characterName) {
        return Option.option(this.store.get(characterName))
                .filter(set -> !set.isEmpty())
                .map(TreeSet::last);
    }

    @Override
    public Result<Snapshot> store(final Snapshot snapshot) {
        this.store.computeIfAbsent(snapshot.name(), _ -> new TreeSet<>()).add(snapshot);
        return Result.ok(snapshot);
    }

    @Override
    public Option<Snapshot> theLastest(final String name) {
        return this.getLastSnapshot(name);
    }

    @Override
    public List<Snapshot> allOrdered(final String name) {
        return Option.option(this.store.get(name))
                .map(List::copyOf)
                .or(List.of());
    }
}
