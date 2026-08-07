package be.charleshornick.supra.chargen.profession;

public enum ProfessionType {
    SPECIAL,
    MINOR,
    MINOR_SPECIAL,
    MAJOR,
    MAJOR_EVOLUTION,
    UNDEFINED,
    ;

    public boolean isEvolutionType() {
        return this == MAJOR_EVOLUTION;
    }
}
