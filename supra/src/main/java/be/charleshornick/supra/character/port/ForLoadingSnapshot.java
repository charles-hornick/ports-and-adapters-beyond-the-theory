package be.charleshornick.supra.character.port;

import be.charleshornick.supra.shared.character.snapshot.Snapshot;
import org.pragmatica.lang.Option;

public interface ForLoadingSnapshot {
    Option<Snapshot> getLastSnapshot(String characterName);
}
