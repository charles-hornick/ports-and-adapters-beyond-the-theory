package be.charleshornick.supra.facade.rest.retrieve.snapshot;

import be.charleshornick.supra.lib.cqs.core.Query;
import org.jspecify.annotations.Nullable;

import java.util.List;

import static be.charleshornick.supra.facade.rest.Wire.normalize;

record GetAllSnapshotsQuery(@Nullable String characterName) implements Query<List<SnapshotProjection>> {

    GetAllSnapshotsQuery {
        characterName = normalize(characterName);
    }
}
