package be.charleshornick.supra.facade.rest.reterieve.snapshot;

import be.charleshornick.supra.lib.cqs.core.Query;
import org.jspecify.annotations.Nullable;
import org.pragmatica.lang.Option;

record GetLatestSnapshotQuery(@Nullable String characterName) implements Query<Option<SnapshotProjection>> {

    GetLatestSnapshotQuery {
        characterName = (characterName != null) ? characterName.strip() : "";
    }
}
