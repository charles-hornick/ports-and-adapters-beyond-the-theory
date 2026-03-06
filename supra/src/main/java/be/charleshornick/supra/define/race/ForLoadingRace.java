package be.charleshornick.supra.define.race;

import be.charleshornick.supra.race.Race;
import be.charleshornick.supra.race.RaceName;
import org.pragmatica.lang.Option;

public interface ForLoadingRace {
    Option<Race> getRaceDetails(RaceName raceName);
}
