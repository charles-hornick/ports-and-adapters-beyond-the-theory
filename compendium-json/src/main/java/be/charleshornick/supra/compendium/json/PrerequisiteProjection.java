package be.charleshornick.supra.compendium.json;

import be.charleshornick.supra.chargen.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.chargen.profession.Prerequisite;
import be.charleshornick.supra.chargen.race.RaceName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

record PrerequisiteProjection(List<String> forbiddenRaces,
                              Map<String, Integer> characteristicsToMatch) {

    Prerequisite toCore() {
        return new Prerequisite(
                buildCharacteristicsToMatch(characteristicsToMatch),
                forbiddenRaces.stream().map(RaceName::valueOf).toList()
        );
    }

    private static Map<PrimaryCharacteristicName, Integer> buildCharacteristicsToMatch(final Map<String, Integer> characteristicsToMatch) {
        final var map = new HashMap<PrimaryCharacteristicName, Integer>();
        for (final var entry : characteristicsToMatch.entrySet()) {
            map.put( PrimaryCharacteristicName.valueOf(entry.getKey()), entry.getValue());
        }

        return map;
    }
}
