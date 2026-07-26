package be.charleshornick.supra.facade.rest.reterieve.snapshot;

import be.charleshornick.supra.facade.rest.ResultResponseMapper;
import be.charleshornick.supra.lib.cqs.core.Bus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters/{characterName}")
public class SnapshotController {

    private final Bus bus;

    public SnapshotController(final Bus bus) {
        this.bus = bus;
    }

    @GetMapping("/snapshots")
    ResponseEntity<?> getAllSnapshots(@PathVariable final String characterName) {
        return this.bus.executeQuery(new GetAllSnapshotsQuery(characterName))
                .fold(ResultResponseMapper::toError, ResponseEntity::ok);
    }

    @GetMapping("/snapshots/latest")
    ResponseEntity<?> getLatestSnapshot(@PathVariable final String characterName) {
        return this.bus.executeQuery(new GetLatestSnapshotQuery(characterName))
                .fold(
                        ResultResponseMapper::toError,
                        snapshot -> ResponseEntity.of(snapshot.toOptional())
                );
    }
}
