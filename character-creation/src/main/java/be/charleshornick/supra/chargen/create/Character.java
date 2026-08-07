package be.charleshornick.supra.chargen.create;

import be.charleshornick.supra.chargen.state.Recorder;
import be.charleshornick.supra.chargen.state.snapshot.Snapshot;
import be.charleshornick.supra.chargen.state.SnapshotBuilder;

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
