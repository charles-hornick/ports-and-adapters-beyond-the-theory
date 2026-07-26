module be.charleshornick.supra.storage.sqlite {
    requires be.charleshornick.supra;
    requires core;

    requires org.xerial.sqlitejdbc;
    requires jakarta.inject;
    requires spring.jdbc;
    requires spring.tx;
    requires tools.jackson.databind;
    requires org.jspecify;
}