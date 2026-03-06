package be.charleshornick.supra.profession;

public enum ProfessionType {
    SPECIAL, MINOR, MINOR_SPECIAL, MAJOR, MAJOR_EVOLUTION;

    public boolean isEvolutionType() {
        return this == MAJOR_EVOLUTION;
    }
}
