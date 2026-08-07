package be.charleshornick.supra.facade.rest.retrieve.profession;

import be.charleshornick.supra.facade.rest.ResultResponseMapper;
import be.charleshornick.supra.lib.cqs.core.Bus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/professions")
final class ProfessionController {

    private final Bus bus;

    ProfessionController(final Bus bus) {
        this.bus = bus;
    }

    @GetMapping
    ResponseEntity<?> getAllProfessions() {
        return this.bus.executeQuery(new GetAllProfessionsQuery())
                .fold(ResultResponseMapper::toError, ResponseEntity::ok);
    }
}
