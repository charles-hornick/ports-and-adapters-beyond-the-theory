package be.charleshornick.supra.bootstrap.config;

import be.charleshornick.supra.lib.cqs.core.Bus;
import be.charleshornick.supra.lib.cqs.core.CommandHandler;
import be.charleshornick.supra.lib.cqs.core.QueryHandler;
import be.charleshornick.supra.lib.cqs.spring.SpringBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration(proxyBeanMethods = false)
class CqsComposition {

    @Bean
    Bus bus(final List<CommandHandler<?>> commandHandlers, final List<QueryHandler<?, ?>> queryHandlers) {
        return new SpringBus(commandHandlers, queryHandlers);
    }
}
