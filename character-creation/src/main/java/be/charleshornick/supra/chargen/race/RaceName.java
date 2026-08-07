package be.charleshornick.supra.chargen.race;

public enum RaceName {
    UNDEFINED(true),

    ELF(false),
    HALF_ELF(false),
    HIGH_ELF(false),
    WOODEN_ELF(false),
    DWARF(false),
    HIGH_DWARF(false),
    HUMAN(false),
    HIGH_HUMAN(false),
    GNOME(false),
    HOBBIT(false),
    HALF_ORK(false),
    ;

    private final boolean technical;

    RaceName(boolean technical) { this.technical = technical; }

    public static boolean isUndefined(final String name) {
        return name != null && name.equalsIgnoreCase(UNDEFINED.name());
    }

    public boolean isTechnical() { return this.technical; }
}
