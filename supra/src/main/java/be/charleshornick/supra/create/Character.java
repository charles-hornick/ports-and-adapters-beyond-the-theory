package be.charleshornick.supra.create;

import be.charleshornick.supra.state.Recorder;
import be.charleshornick.supra.state.snapshot.Snapshot;
import be.charleshornick.supra.state.SnapshotBuilder;

class Character implements Recorder {

    private final String name;

    private Character(final String name) {
        this.name = name;
    }

    static Character withName(final String name) {
        return new Character(name);
    }

    @Override
    public Snapshot doSnapshot() {
        return SnapshotBuilder.asFirstOne(this.name);
    }
}
