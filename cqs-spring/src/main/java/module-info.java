module be.charleshornick.supra.lib.cqs.spring {
    requires transitive be.charleshornick.supra.lib.cqs.core;

    requires core;
    requires spring.beans;
    requires spring.tx;
    requires static org.jspecify;

    exports be.charleshornick.supra.lib.cqs.spring;
}