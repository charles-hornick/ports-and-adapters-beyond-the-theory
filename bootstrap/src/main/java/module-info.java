module be.charleshornick.supra.bootstrap {
    requires be.charleshornick.supra.chargen;
    requires be.charleshornick.supra.lib.cqs.core;
    requires be.charleshornick.supra.lib.cqs.spring;
    requires core;
    requires org.jspecify;

    requires spring.context;
    requires spring.tx;
    requires spring.beans;
    requires spring.core;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;

    opens be.charleshornick.supra.bootstrap.config to spring.core, spring.beans, spring.context;
    opens be.charleshornick.supra.bootstrap to spring.beans, spring.core, spring.context;
}