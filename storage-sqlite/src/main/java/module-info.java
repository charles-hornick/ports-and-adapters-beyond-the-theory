module be.charleshornick.supra.storage.sqlite {
    requires be.charleshornick.supra.chargen;
    requires core;

    requires org.xerial.sqlitejdbc;
    requires jakarta.inject;
    requires spring.jdbc;
    requires spring.core;
    requires spring.beans;
    requires tools.jackson.databind;
    requires static org.jspecify;

    opens be.charleshornick.supra.storage.sqlite to spring.core, spring.beans;
}