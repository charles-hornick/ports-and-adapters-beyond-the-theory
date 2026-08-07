package be.charleshornick.supra.facade.rest.retrieve.race;

import be.charleshornick.supra.facade.rest.ResultResponseMapper;
import be.charleshornick.supra.lib.cqs.core.Bus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/races")
final class RaceController {

    private final Bus bus;

    RaceController(final Bus bus) {
        this.bus = bus;
    }

    @GetMapping
    ResponseEntity<?> getAllRaces() {
        return this.bus.executeQuery(new GetAllRacesQuery())
                .fold(ResultResponseMapper::toError, ResponseEntity::ok);
    }
}
