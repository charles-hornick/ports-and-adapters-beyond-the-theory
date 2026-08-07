package be.charleshornick.supra.lib.cqs.spring;

import be.charleshornick.supra.lib.cqs.core.Query;
import be.charleshornick.supra.lib.cqs.core.QueryHandler;
import org.pragmatica.lang.Result;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;

public final class ReadOnlyQueryDecorator<R, Q extends Query<R>> implements QueryHandler<R, Q> {

    private final QueryHandler<R, Q> delegate;
    private final TransactionTemplate tx;

    public ReadOnlyQueryDecorator(final QueryHandler<R, Q> delegate, final TransactionTemplate tx) {
        this.delegate = delegate;
        this.tx = tx;
    }

    @Override
    public Result<R> handle(final Q query) {
        return Objects.requireNonNull(tx.execute(_ -> delegate.handle(query)));
    }

    @Override
    public Class<Q> queryType() {
        return delegate.queryType();
    }
}
