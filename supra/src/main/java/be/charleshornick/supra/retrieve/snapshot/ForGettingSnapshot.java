package be.charleshornick.supra.retrieve.snapshot;

import be.charleshornick.supra.state.snapshot.Snapshot;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;

import java.util.List;

public interface ForGettingSnapshot {

    Result<Option<Snapshot>> theLastest(String name);

    Result<List<Snapshot>> allOrdered(String name);
}
