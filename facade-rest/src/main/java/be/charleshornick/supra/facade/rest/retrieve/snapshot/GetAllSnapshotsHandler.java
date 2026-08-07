package be.charleshornick.supra.facade.rest.retrieve.snapshot;

import be.charleshornick.supra.lib.cqs.core.QueryHandler;
import be.charleshornick.supra.chargen.retrieve.snapshot.GetAllSnapshots;
import be.charleshornick.supra.chargen.state.snapshot.Snapshot;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.pragmatica.lang.Result;

import java.util.List;

@Named
class GetAllSnapshotsHandler implements QueryHandler<List<SnapshotProjection>, GetAllSnapshotsQuery> {

    private final GetAllSnapshots getAllSnapshots;

    @Inject
    GetAllSnapshotsHandler(final GetAllSnapshots getAllSnapshots) {
        this.getAllSnapshots = getAllSnapshots;
    }

    @Override
    public Result<List<SnapshotProjection>> handle(final GetAllSnapshotsQuery query) {
        return Result.success(query)
                .map(GetAllSnapshotsQuery::characterName)
                .flatMap(this.getAllSnapshots::forCharacterNamed)
                .map(this::toProjection);
    }

    private List<SnapshotProjection> toProjection(final List<Snapshot> snapshots) {
        return snapshots.stream()
                .map(SnapshotProjection::from)
                .toList();
    }

    @Override
    public Class<GetAllSnapshotsQuery> queryType() {
        return GetAllSnapshotsQuery.class;
    }
}
