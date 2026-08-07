package be.charleshornick.supra.lib.cqs.spring;

import be.charleshornick.supra.lib.cqs.core.Command;
import be.charleshornick.supra.lib.cqs.core.CommandHandler;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.Unit;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;

public final class TransactionalCommandDecorator<C extends Command>
        implements CommandHandler<C> {

    private final CommandHandler<C> delegate;
    private final TransactionTemplate tx;

    public TransactionalCommandDecorator(final CommandHandler<C> delegate, final TransactionTemplate tx) {
        this.delegate = delegate;
        this.tx = tx;
    }

    @Override
    public Result<Unit> handle(final C command) {
        return Objects.requireNonNull(tx.execute(status -> {
            final Result<Unit> result = delegate.handle(command);
            result.onFailure(_ -> status.setRollbackOnly());
            return result;
        }));
    }

    @Override
    public Class<C> commandType() {
        return delegate.commandType();   // délègue pour survivre à la décoration
    }
}
