module be.charleshornick.supra.facade.rest {
    requires be.charleshornick.supra.chargen;
    requires be.charleshornick.supra.lib.cqs.core;
    requires core;

    requires spring.web;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires spring.webmvc;
    requires jakarta.inject;
    requires org.slf4j;
    requires tools.jackson.databind;
    requires static org.jspecify;

    opens be.charleshornick.supra.facade.rest.create to spring.core, spring.beans, tools.jackson.databind;
    opens be.charleshornick.supra.facade.rest.define to spring.core, spring.beans;
    opens be.charleshornick.supra.facade.rest.define.race to spring.core, spring.beans, tools.jackson.databind;
    opens be.charleshornick.supra.facade.rest.define.profession to spring.core, spring.beans, tools.jackson.databind;
    opens be.charleshornick.supra.facade.rest.define.characteristic to spring.core, spring.beans, tools.jackson.databind;
    opens be.charleshornick.supra.facade.rest.retrieve.race to spring.core, spring.beans, tools.jackson.databind;
    opens be.charleshornick.supra.facade.rest.retrieve.profession to spring.core, spring.beans, tools.jackson.databind;
    opens be.charleshornick.supra.facade.rest.retrieve.snapshot to spring.core, spring.beans, tools.jackson.databind;
}