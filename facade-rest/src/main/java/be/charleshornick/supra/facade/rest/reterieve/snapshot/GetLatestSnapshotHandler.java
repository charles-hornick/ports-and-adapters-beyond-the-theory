package be.charleshornick.supra.facade.rest.reterieve.snapshot;

import be.charleshornick.supra.lib.cqs.core.QueryHandler;
import be.charleshornick.supra.retrieve.snapshot.GetLastestSnapshot;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;

@Named
class GetLatestSnapshotHandler implements QueryHandler<Option<SnapshotProjection>, GetLatestSnapshotQuery> {

    private final GetLastestSnapshot getLastestSnapshot;

    @Inject
    GetLatestSnapshotHandler(final GetLastestSnapshot getLastestSnapshot) {
        this.getLastestSnapshot = getLastestSnapshot;
    }

    @Override
    public Result<Option<SnapshotProjection>> handle(final GetLatestSnapshotQuery query) {
        return Result.ok(query)
                .map(GetLatestSnapshotQuery::characterName)
                .flatMap(this.getLastestSnapshot::forCharacterNamed)
                .map(opt -> opt.map(SnapshotProjection::from));
    }

    @Override
    public Class<GetLatestSnapshotQuery> queryType() {
        return GetLatestSnapshotQuery.class;
    }
}
