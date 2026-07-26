package be.charleshornick.supra.storage.sqlite;

import be.charleshornick.supra.ForStoringSnapshot;
import be.charleshornick.supra.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.create.ForRegisteringName;
import be.charleshornick.supra.define.ForLoadingSnapshot;
import be.charleshornick.supra.fault.SupraCause;
import be.charleshornick.supra.profession.Profession;
import be.charleshornick.supra.race.Race;
import be.charleshornick.supra.retrieve.profession.ForGettingProfession;
import be.charleshornick.supra.retrieve.race.ForGettingRaces;
import be.charleshornick.supra.retrieve.snapshot.ForGettingSnapshot;
import be.charleshornick.supra.state.snapshot.Action;
import be.charleshornick.supra.state.snapshot.Snapshot;
import jakarta.inject.Inject;
import org.jspecify.annotations.NonNull;
import org.pragmatica.lang.Option;
import org.pragmatica.lang.Result;
import jakarta.inject.Named;
import org.pragmatica.lang.Unit;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Named
class SnapshotStorage implements ForStoringSnapshot, ForLoadingSnapshot, ForGettingSnapshot, ForRegisteringName {

    private static final String INSERT_SNAPSHOT_QUERY = """
        INSERT INTO snapshots (character_name, version, shot_at, action, race_name, profession_name, invested_points)
        VALUES (:name, :version, :shotAt, :action, :raceName, :professionName, :investedPoints)
        """;
    private static final String SELECT_ORDERED_SNAPSHOTS = """
        SELECT *
        FROM snapshots
        WHERE LOWER(character_name) = LOWER(:name)
        ORDER BY version DESC
        """;
    private static final String SELECT_LATEST_SNAPSHOT = SELECT_ORDERED_SNAPSHOTS + " LIMIT 1";

    private final ForGettingRaces forGettingRaces;
    private final ForGettingProfession forGettingProfession;

    private final JdbcClient jdbc;
    private final ObjectMapper objectMapper;

    @Inject
    SnapshotStorage(final ForGettingRaces forGettingRaces,
                    final ForGettingProfession forGettingProfession,
                    final JdbcClient jdbc,
                    final ObjectMapper objectMapper) {
        this.forGettingRaces = forGettingRaces;
        this.forGettingProfession = forGettingProfession;
        this.jdbc = jdbc;
        this.objectMapper = objectMapper;
    }

    @Override
    @NonNull
    public Result<Snapshot> store(final @NonNull Snapshot snapshot) {
        return this.serialize(snapshot.investedPoints())
                .flatMap(investedPoints -> this.storeSnapshot(snapshot, investedPoints));
    }

    private Result<Snapshot> storeSnapshot(final Snapshot snapshot, final String investedPoints) {
        return Result.lift(
                e -> new SupraCause.Technical("snapshot.store.failed", e),
                () -> {
                    this.jdbc.sql(INSERT_SNAPSHOT_QUERY)
                            .param("name", snapshot.name())
                            .param("version", snapshot.version())
                            .param("shotAt", snapshot.shotAt().toString())
                            .param("action", snapshot.action().name())
                            .param("raceName", snapshot.race().name().name())
                            .param("professionName", snapshot.profession().name().name())
                            .param("investedPoints", investedPoints)
                            .update();

                    return snapshot;
                }
        );
    }

    @Override
    @NonNull
    public Result<Option<Snapshot>> getLastSnapshot(final @NonNull String characterName) {
        return theLatest(characterName);
    }


    @Override
    @NonNull
    public Result<Option<Snapshot>> theLatest(final @NonNull String name) {
        return Result.lift(
                e -> new SupraCause.Technical("snapshot.store.unexpected.error", e),
                () -> this.jdbc.sql(SELECT_LATEST_SNAPSHOT)
                        .param("name", name)
                        .query(this::mapRow)
                        .optional()
        ).map(Option::from);
    }

    @Override
    @NonNull
    public Result<List<Snapshot>> allOrdered(final @NonNull String name) {
        return Result.tryOf(
                () -> this.jdbc.sql(SELECT_ORDERED_SNAPSHOTS)
                .param("name", name)
                .query(this::mapRow)
                .list(),
                e -> new SupraCause.Technical("snapshot.store.unexpected.error", e)
        );
    }

    private Snapshot mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final var raceName = rs.getString("race_name");
        final var professionName = rs.getString("profession_name");

        return this.deserialize(rs.getString("invested_points"))
                .flatMap(points -> this.buildSnapshot(
                        rs,
                        points,
                        this.getRaceFromName(raceName),
                        this.getProfessionFromName(professionName)
                ))
                .fold(
                        cause -> { throw new IllegalArgumentException(cause.message()); },
                        result -> result
                );
    }

    private Race getRaceFromName(final String name) {
        return this.forGettingRaces.details()
                .stream()
                .filter(race -> race.name().name().equals(name))
                .findFirst()
                .orElseGet(Race::undefined);
    }

    private Profession getProfessionFromName(final String name) {
        return this.forGettingProfession.details()
                .stream()
                .filter(race -> race.name().name().equals(name))
                .findFirst()
                .orElseGet(Profession::undefined);
    }

    private Result<Snapshot> buildSnapshot(final ResultSet rs,
                                           final Map<PrimaryCharacteristicName, Integer> investedPoints,
                                           final Race race,
                                           final Profession profession) {
        return Result.tryOf(
                () -> Snapshot.create(
                        rs.getInt("version"),
                        rs.getString("character_name"),
                        Action.valueOf(rs.getString("action")),
                        LocalDateTime.parse(rs.getString("shot_at")),
                        race,
                        profession,
                        investedPoints
                ),
                e -> new SupraCause.Technical("cannot.create.snapshot", e)
        );
    }

    private Result<String> serialize(final Map<PrimaryCharacteristicName, Integer> points) {
        return Result.tryOf(
                () -> this.objectMapper.writeValueAsString(points),
                e -> new SupraCause.Technical("snapshot.serialize.failed", e)
        );
    }

    private Result<Map<PrimaryCharacteristicName, Integer>> deserialize(final String json) {
        return Result.tryOf(
                () -> this.objectMapper.readValue(json, new TypeReference<>() {}),
                e -> new SupraCause.Technical("snapshot.deserialize.failed", e)
        );
    }

    @Override
    public Result<Unit> register(final String name) {
        return Result.tryOf(
                () -> {
                    this.jdbc.sql("INSERT INTO characters (name) VALUES (:name)")
                            .param("name", name)
                            .update();
                    return Unit.toUnit(null);
                },
                SnapshotStorage::toCause
        );
    }

    private static SupraCause toCause(final Throwable e) {
        return isConstraintViolation(e)
                ? new SupraCause.Conflict("character", "name.already.exists")
                : new SupraCause.Technical("character.store.failed", e);
    }

    private static boolean isConstraintViolation(final Throwable e) {
        Throwable current = e;
        while (current != null) {
            if (current instanceof SQLiteException sqlite) {
                return sqlite.getResultCode() == SQLiteErrorCode.SQLITE_CONSTRAINT_PRIMARYKEY
                        || sqlite.getResultCode() == SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE;
            }
            current = current.getCause();
        }
        return false;
    }
}
