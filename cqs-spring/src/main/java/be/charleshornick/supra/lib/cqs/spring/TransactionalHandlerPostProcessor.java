package be.charleshornick.supra.lib.cqs.spring;

import be.charleshornick.supra.lib.cqs.core.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Supplier;

public final class TransactionalHandlerPostProcessor implements BeanPostProcessor {

    private final Supplier<TransactionTemplate> writeTx;
    private final Supplier<TransactionTemplate> readOnlyTx;

    public TransactionalHandlerPostProcessor(final Supplier<TransactionTemplate> writeTx,
                                             final Supplier<TransactionTemplate> readOnlyTx) {
        this.writeTx = writeTx;
        this.readOnlyTx = readOnlyTx;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String name) {
        return switch (bean) {
            case NonTransactional _ -> bean;
            case CommandHandler<?> handler -> new TransactionalCommandDecorator<>(handler, this.writeTx.get());
            case QueryHandler<?, ?> handler -> new ReadOnlyQueryDecorator<>(handler, this.readOnlyTx.get());
            default -> bean;
        };
    }
}
