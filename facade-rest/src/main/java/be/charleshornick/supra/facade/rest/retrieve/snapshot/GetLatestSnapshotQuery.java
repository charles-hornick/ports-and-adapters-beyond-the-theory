package be.charleshornick.supra.facade.rest.retrieve.snapshot;

import be.charleshornick.supra.lib.cqs.core.Query;
import org.jspecify.annotations.Nullable;
import org.pragmatica.lang.Option;

import static be.charleshornick.supra.facade.rest.Wire.normalize;

record GetLatestSnapshotQuery(@Nullable String characterName) implements Query<Option<SnapshotProjection>> {

    GetLatestSnapshotQuery {
        characterName = normalize(characterName);
    }
}
