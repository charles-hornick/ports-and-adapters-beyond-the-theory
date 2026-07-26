module be.charleshornick.supra.bootstrap {
    requires be.charleshornick.supra;
    requires be.charleshornick.supra.lib.cqs;
    requires be.charleshornick.supra.lib.cqs.spring;
    requires core;

    requires spring.context;
    requires spring.tx;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;

    opens be.charleshornick.supra.bootstrap.config;
    opens be.charleshornick.supra.bootstrap to spring.beans, spring.core, spring.context;
}