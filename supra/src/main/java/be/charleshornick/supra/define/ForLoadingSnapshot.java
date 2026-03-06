package be.charleshornick.supra.define;

import be.charleshornick.supra.snapshot.Snapshot;
import org.pragmatica.lang.Option;

public interface ForLoadingSnapshot {
    Option<Snapshot> getLastSnapshot(String characterName);
}
