package be.charleshornick.supra.shared;

import be.charleshornick.supra.shared.character.snapshot.Snapshot;

public interface Recorder {
    Snapshot doSnapshot();
}
