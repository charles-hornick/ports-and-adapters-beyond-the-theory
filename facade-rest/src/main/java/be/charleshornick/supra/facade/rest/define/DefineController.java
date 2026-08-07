package be.charleshornick.supra.facade.rest.define;

import be.charleshornick.supra.facade.rest.ResultResponseMapper;
import be.charleshornick.supra.facade.rest.define.characteristic.AllocationRequest;
import be.charleshornick.supra.facade.rest.define.profession.DefineProfessionRequest;
import be.charleshornick.supra.facade.rest.define.race.DefineRaceRequest;
import be.charleshornick.supra.chargen.fault.SupraCause;
import be.charleshornick.supra.lib.cqs.core.Bus;
import org.pragmatica.lang.Option;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/characters/{characterName}")
final class DefineController {

    private final Bus bus;

    DefineController(final Bus bus) {
        this.bus = bus;
    }

    @PutMapping("/race")
    ResponseEntity<?> defineRace(@PathVariable final String characterName,
                                 @RequestBody(required = false) final DefineRaceRequest request) {
        return Option.option(request)
                .toResult(new SupraCause.InvalidInput("body", "empty"))
                .flatMap(r -> r.toCommand(characterName))
                .flatMap(this.bus::executeCommand)
                .fold(
                        ResultResponseMapper::toError,
                        _ -> ResponseEntity.noContent().build()
                );
    }

    @PutMapping("/profession")
    ResponseEntity<?> defineProfession(@PathVariable final String characterName,
                                       @RequestBody(required = false) final DefineProfessionRequest request) {
        return Option.option(request)
                .toResult(new SupraCause.InvalidInput("body", "empty"))
                .flatMap(r -> r.toCommand(characterName))
                .flatMap(this.bus::executeCommand)
                .fold(
                        ResultResponseMapper::toError,
                        _ -> ResponseEntity.noContent().build()
                );
    }

    @PostMapping("/characteristics/{characteristic}/points")
    ResponseEntity<?> allocatePoint(@PathVariable final String characterName,
                                    @PathVariable final String characteristic,
                                    @RequestBody(required = false) final AllocationRequest body) {
        return Option.option(body)
                .toResult(new SupraCause.InvalidInput("body", "empty"))
                .flatMap(r -> r.toCommand(characterName, characteristic))
                .flatMap(this.bus::executeCommand)
                .fold(
                        ResultResponseMapper::toError,
                        _ -> ResponseEntity.noContent().build()
                );
    }
}
