package be.charleshornick.supra.shared.characteristic;

import java.util.Set;

public enum PrimaryCharacteristicName {
    COURAGE,
    WILLPOWER,
    CHARISMA,
    BEAUTY,
    CONSTITUTION,
    INTELLIGENCE,
    PERCEPTION,
    DEXTERITY,
    STRENGTH,
    ;

    PrimaryCharacteristicName() {}

    static Set<PrimaryCharacteristicName> getNameFromFirstCategory() {
        return Set.of(COURAGE, WILLPOWER, CHARISMA);
    }

    static Set<PrimaryCharacteristicName> getNameFromSecondCategory() {
        return Set.of(BEAUTY, CONSTITUTION, INTELLIGENCE);
    }

    static Set<PrimaryCharacteristicName> getNameFromThirdCategory() {
        return Set.of(PERCEPTION, DEXTERITY, STRENGTH);
    }

    public static Set<PrimaryCharacteristicName> getCategoryRelatedTo(final PrimaryCharacteristicName name) {
        if (getNameFromFirstCategory().contains(name)) {
            return getNameFromFirstCategory();
        } else if (getNameFromSecondCategory().contains(name)) {
            return getNameFromSecondCategory();
        }
        return getNameFromThirdCategory();
    }
}
