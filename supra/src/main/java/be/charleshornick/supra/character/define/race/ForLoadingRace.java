package be.charleshornick.supra.character.define.race;

import be.charleshornick.supra.shared.race.Race;
import be.charleshornick.supra.shared.race.RaceName;
import org.pragmatica.lang.Option;

public interface ForLoadingRace {
    Option<Race> getRaceDetails(RaceName raceName);
}
