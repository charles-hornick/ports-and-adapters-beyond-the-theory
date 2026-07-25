package be.charleshornick.supra.retrieve.snapshot;

import be.charleshornick.supra.state.snapshot.Snapshot;
import org.jspecify.annotations.NullMarked;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;

import java.util.List;

@NullMarked
public interface ForGettingSnapshot {

    Result<Option<Snapshot>> theLatest(String name);

    Result<List<Snapshot>> allOrdered(String name);
}
