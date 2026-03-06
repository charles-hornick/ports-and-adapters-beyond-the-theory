package be.charleshornick.supra.creation;

import be.charleshornick.supra.snapshot.Recorder;
import be.charleshornick.supra.snapshot.Snapshot;
import be.charleshornick.supra.snapshot.SnapshotBuilder;

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
