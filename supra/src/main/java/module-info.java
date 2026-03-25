module be.charleshornick.supra {
    requires core; // Pragmatica-core

    exports be.charleshornick.supra; // ErrorCause, ForStoringSnapshot
    exports be.charleshornick.supra.create; // Primary port
    exports be.charleshornick.supra.define; // ForLoadingSnapshot, ToCharacter
    exports be.charleshornick.supra.define.race; // Primary port
    exports be.charleshornick.supra.define.profession; // Primary port
    exports be.charleshornick.supra.define.characteristic; // Primary port
    exports be.charleshornick.supra.retrieve.race; // Primary port
    exports be.charleshornick.supra.retrieve.profession; // Primary port
    exports be.charleshornick.supra.retrieve.snapshot; // Primary port
    exports be.charleshornick.supra.race; // Vocabulary
    exports be.charleshornick.supra.profession; // Vocabulary
    exports be.charleshornick.supra.characteristic; // Vocabulary
    exports be.charleshornick.supra.state.snapshot; // Exposed state
}