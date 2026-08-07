module be.charleshornick.supra.compendium.json {
    requires be.charleshornick.supra.chargen;
    requires core;

    requires spring.core;
    requires spring.beans;
    requires jakarta.inject;
    requires tools.jackson.databind;
    requires jakarta.annotation;
    requires static org.jspecify;

    opens be.charleshornick.supra.compendium.json to spring.core, spring.beans, tools.jackson.databind;
}