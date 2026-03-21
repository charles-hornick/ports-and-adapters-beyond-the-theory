package be.charleshornick.supra.state;

import be.charleshornick.supra.state.snapshot.Snapshot;

public interface Recorder {
    Snapshot doSnapshot();
}
