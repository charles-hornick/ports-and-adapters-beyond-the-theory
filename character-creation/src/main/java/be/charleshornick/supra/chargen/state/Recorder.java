package be.charleshornick.supra.chargen.state;

import be.charleshornick.supra.chargen.state.snapshot.Snapshot;

public interface Recorder {
    Snapshot doSnapshot();
}
