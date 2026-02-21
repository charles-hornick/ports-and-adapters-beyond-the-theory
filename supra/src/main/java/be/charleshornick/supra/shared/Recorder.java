package be.charleshornick.supra.shared;

import be.charleshornick.supra.shared.character.snapshot.Character;

public interface Recorder {
    Character doSnapshot();
}
