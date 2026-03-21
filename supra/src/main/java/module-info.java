module be.charleshornick.supra {
    exports be.charleshornick.supra;                    // ErrorCause, ForStoringSnapshot
    exports be.charleshornick.supra.create;
    exports be.charleshornick.supra.define;
    exports be.charleshornick.supra.define.race;
    exports be.charleshornick.supra.define.profession;
    exports be.charleshornick.supra.define.characteristic;
    exports be.charleshornick.supra.retrieve.race;
    exports be.charleshornick.supra.retrieve.profession;
    exports be.charleshornick.supra.retrieve.snapshot;
    exports be.charleshornick.supra.race;
    exports be.charleshornick.supra.profession;
    exports be.charleshornick.supra.characteristic;
    exports be.charleshornick.supra.state.snapshot;

    requires org.apache.commons.lang3;
    requires core;
}