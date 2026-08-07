package be.charleshornick.supra.facade.rest.retrieve.snapshot;

import be.charleshornick.supra.lib.cqs.core.QueryHandler;
import be.charleshornick.supra.chargen.retrieve.snapshot.GetLatestSnapshot;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;

@Named
class GetLatestSnapshotHandler implements QueryHandler<Option<SnapshotProjection>, GetLatestSnapshotQuery> {

    private final GetLatestSnapshot getLatestSnapshot;

    @Inject
    GetLatestSnapshotHandler(final GetLatestSnapshot getLatestSnapshot) {
        this.getLatestSnapshot = getLatestSnapshot;
    }

    @Override
    public Result<Option<SnapshotProjection>> handle(final GetLatestSnapshotQuery query) {
        return Result.success(query)
                .map(GetLatestSnapshotQuery::characterName)
                .flatMap(this.getLatestSnapshot::forCharacterNamed)
                .map(opt -> opt.map(SnapshotProjection::from));
    }

    @Override
    public Class<GetLatestSnapshotQuery> queryType() {
        return GetLatestSnapshotQuery.class;
    }
}
