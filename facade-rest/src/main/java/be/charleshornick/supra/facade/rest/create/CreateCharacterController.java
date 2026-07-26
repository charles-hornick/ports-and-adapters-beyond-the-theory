package be.charleshornick.supra.facade.rest.create;

import be.charleshornick.supra.facade.rest.ResultResponseMapper;
import be.charleshornick.supra.fault.SupraCause;
import be.charleshornick.supra.lib.cqs.core.Bus;
import org.pragmatica.lang.Option;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/characters")
final class CreateCharacterController {

    private final Bus bus;

    CreateCharacterController(final Bus bus) {
        this.bus = bus;
    }

    @PostMapping
    ResponseEntity<String> createCharacter(@RequestBody(required = false) final CreateCharacterRequest request) {
        return Option.option(request)
                .toResult(new SupraCause.InvalidInput("body", "empty"))
                .map(CreateCharacterRequest::toCommand)
                .flatMap(CreateCharacterCommand::validate)
                .flatMap(this.bus::executeCommand)
                .fold(
                    ResultResponseMapper::toError,
                    _ -> ResponseEntity.created(buildUri("/{name}", request.characterName())).build()
                );
    }

    private static URI buildUri(final String path, final String characterName) {
        return MvcUriComponentsBuilder.fromController(CreateCharacterController.class)
                .path(path)
                .buildAndExpand(characterName)
                .toUri();
    }
}
