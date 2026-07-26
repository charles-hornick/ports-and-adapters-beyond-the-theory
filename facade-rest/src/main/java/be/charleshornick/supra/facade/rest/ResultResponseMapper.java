package be.charleshornick.supra.facade.rest;

import be.charleshornick.supra.fault.SupraCause;
import org.pragmatica.lang.Cause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public final class ResultResponseMapper {
    private static final Logger log = LoggerFactory.getLogger(ResultResponseMapper.class);

    private ResultResponseMapper() {
    }

    public static ResponseEntity<String> toError(final Cause cause) {
        return switch (cause) {
            case SupraCause.RuleViolation c -> ResponseEntity.unprocessableContent().body(c.message());
            case SupraCause.InvalidInput c  -> ResponseEntity.badRequest().body(c.message());
            case SupraCause.NotFound c      -> ResponseEntity.status(NOT_FOUND).body(c.message());
            case SupraCause.Conflict c      -> ResponseEntity.status(CONFLICT).body(c.message());
            case SupraCause.Technical c     -> {
                log.error("Technical failure: {}", c.message(), c.origin());
                yield ResponseEntity.internalServerError().body("internal.error");
            }
            default                         -> ResponseEntity.internalServerError().body("internal.error");
        };
    }
}
