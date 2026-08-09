package be.charleshornick.supra.facade.batch;

import be.charleshornick.supra.facade.batch.create.ImportCharacterCommand;
import be.charleshornick.supra.lib.cqs.core.Bus;
import jakarta.inject.Named;
import org.pragmatica.lang.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Named
final class CharacterImportScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(CharacterImportScheduler.class);

    private static final List<String> CHARACTERS = List.of(
            "Zaela", "Elandra", "Thusk", "Zaela", "Mirelle", "Kazadin"
    );

    private final Bus bus;

    CharacterImportScheduler(final Bus bus) {
        this.bus = bus;
    }

    @Scheduled(initialDelay = 2, fixedDelay = 3600, timeUnit = TimeUnit.SECONDS)
    void importCharacters() {
        final var failures = CHARACTERS.stream()
                .map(name -> this.bus.executeCommand(new ImportCharacterCommand(name))
                        .onSuccess(_ -> LOG.info("imported character '{}'", name))
                        .onFailure(cause -> LOG.warn("skipped character '{}': {}", name, cause.message())))
                .filter(Result::isFailure)
                .count();

        LOG.info("import finished: {}/{} characters created", CHARACTERS.size() - failures, CHARACTERS.size());
    }
}
