package be.charleshornick.supra.bootstrap.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration(proxyBeanMethods = false)
@EnableScheduling
@Profile("batch")
@ComponentScan("be.charleshornick.supra.facade.batch")
class BatchComposition {

}
