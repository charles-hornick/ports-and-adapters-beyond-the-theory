package be.charleshornick.supra.lib.cqs.spring;

import be.charleshornick.supra.lib.cqs.core.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.transaction.support.TransactionTemplate;

public final class TransactionalHandlerPostProcessor implements BeanPostProcessor {

    private final TransactionTemplate writeTx;
    private final TransactionTemplate readOnlyTx;

    public TransactionalHandlerPostProcessor(final TransactionTemplate writeTx, final TransactionTemplate readOnlyTx) {
        this.writeTx = writeTx;
        this.readOnlyTx = readOnlyTx;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String name) {
        return switch (bean) {
            case NonTransactional _ -> bean;
            case CommandHandler<?> handler -> decorateCommand(handler);
            case QueryHandler<?, ?> handler -> decorateQuery(handler);
            default -> bean;
        };
    }

    private <C extends Command> Object decorateCommand(final CommandHandler<C> handler) {
        return new TransactionalCommandDecorator<>(handler, writeTx);
    }

    private <R, Q extends Query<R>> Object decorateQuery(final QueryHandler<R, Q> handler) {
        return new ReadOnlyQueryDecorator<>(handler, readOnlyTx);
    }
}
