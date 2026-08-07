module be.charleshornick.supra.chargen {
    requires core; // Pragmatica-core
    requires static org.jspecify;

    exports be.charleshornick.supra.chargen; // ForStoringSnapshot
    exports be.charleshornick.supra.chargen.create; // Primary port
    exports be.charleshornick.supra.chargen.define; // ForLoadingSnapshot, ToCharacter
    exports be.charleshornick.supra.chargen.define.race; // Primary port
    exports be.charleshornick.supra.chargen.define.profession; // Primary port
    exports be.charleshornick.supra.chargen.define.characteristic; // Primary port
    exports be.charleshornick.supra.chargen.retrieve.race; // Primary port
    exports be.charleshornick.supra.chargen.retrieve.profession; // Primary port
    exports be.charleshornick.supra.chargen.retrieve.snapshot; // Primary port
    exports be.charleshornick.supra.chargen.race; // Vocabulary
    exports be.charleshornick.supra.chargen.profession; // Vocabulary
    exports be.charleshornick.supra.chargen.characteristic; // Vocabulary
    exports be.charleshornick.supra.chargen.state.snapshot; // Exposed state
    exports be.charleshornick.supra.chargen.fault; // Errors and Causes
}