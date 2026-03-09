package be.charleshornick.supra.retrieve.snapshot;

import be.charleshornick.supra.snapshot.Snapshot;
import org.pragmatica.lang.Option;

import java.util.List;

public interface ForGettingSnapshot {

    Option<Snapshot> theLastest(String name);

    List<Snapshot> allOrdered(String name);
}
