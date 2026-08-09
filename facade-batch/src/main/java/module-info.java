module be.charleshornick.supra.facade.batch {
    requires be.charleshornick.supra.chargen;
    requires be.charleshornick.supra.lib.cqs.core;
    requires core;

    requires spring.core;
    requires spring.beans;
    requires jakarta.inject;
    requires spring.context;
    requires org.slf4j;
    requires static org.jspecify;

    opens be.charleshornick.supra.facade.batch to spring.core, spring.beans;
}