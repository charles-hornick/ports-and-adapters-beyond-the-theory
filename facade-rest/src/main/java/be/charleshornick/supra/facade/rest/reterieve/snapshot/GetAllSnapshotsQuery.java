package be.charleshornick.supra.facade.rest.reterieve.snapshot;

import be.charleshornick.supra.lib.cqs.core.Query;

import java.util.List;

record GetAllSnapshotsQuery(String characterName) implements Query<List<SnapshotProjection>> {

    GetAllSnapshotsQuery {
        characterName = (characterName != null) ? characterName.strip() : "";
    }
}
