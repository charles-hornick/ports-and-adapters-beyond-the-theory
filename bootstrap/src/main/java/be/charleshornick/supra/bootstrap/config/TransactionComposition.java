package be.charleshornick.supra.bootstrap.config;

import be.charleshornick.supra.lib.cqs.spring.ReadOnlyTransaction;
import be.charleshornick.supra.lib.cqs.spring.TransactionalHandlerPostProcessor;
import be.charleshornick.supra.lib.cqs.spring.WriteTransaction;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration(proxyBeanMethods = false)
@Profile("sqlite")
class TransactionComposition {

    @Bean
    WriteTransaction writeTransactionTemplate(final PlatformTransactionManager tm) {
        return new WriteTransaction(new TransactionTemplate(tm));
    }

    @Bean
    ReadOnlyTransaction readOnlyTransactionTemplate(final PlatformTransactionManager tm) {
        final var template = new TransactionTemplate(tm);
        template.setReadOnly(true);

        return new ReadOnlyTransaction(template);
    }

    @Bean
    static BeanPostProcessor transactionalHandlerPostProcessor(final ObjectProvider<WriteTransaction> write,
                                                               final ObjectProvider<ReadOnlyTransaction> readOnly) {
        return new TransactionalHandlerPostProcessor(() -> write.getObject().template(), () -> readOnly.getObject().template());
    }
}
