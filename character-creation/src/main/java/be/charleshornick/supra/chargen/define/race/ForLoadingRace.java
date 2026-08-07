package be.charleshornick.supra.chargen.define.race;

import be.charleshornick.supra.chargen.race.Race;
import be.charleshornick.supra.chargen.race.RaceName;
import org.jspecify.annotations.NullMarked;
import org.pragmatica.lang.Option;

public interface ForLoadingRace {

    @NullMarked
    Option<Race> getRaceDetails(RaceName raceName);
}
