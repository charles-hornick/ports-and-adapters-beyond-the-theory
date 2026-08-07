package be.charleshornick.supra.storage.sqlite;

import be.charleshornick.supra.chargen.ForStoringSnapshot;
import be.charleshornick.supra.chargen.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.chargen.create.ForRegisteringName;
import be.charleshornick.supra.chargen.define.ForLoadingSnapshot;
import be.charleshornick.supra.chargen.fault.SupraCause;
import be.charleshornick.supra.chargen.profession.Profession;
import be.charleshornick.supra.chargen.profession.ProfessionName;
import be.charleshornick.supra.chargen.race.Race;
import be.charleshornick.supra.chargen.race.RaceName;
import be.charleshornick.supra.chargen.retrieve.profession.ForGettingProfession;
import be.charleshornick.supra.chargen.retrieve.race.ForGettingRaces;
import be.charleshornick.supra.chargen.retrieve.snapshot.ForGettingSnapshot;
import be.charleshornick.supra.chargen.state.snapshot.Action;
import be.charleshornick.supra.chargen.state.snapshot.Snapshot;
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
        WHERE character_name = :name
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
        return Result.tryOf(
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
                },
                e -> new SupraCause.Technical("snapshot.store.failed", e)
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
        return Result.tryOf(
                () -> this.jdbc.sql(SELECT_LATEST_SNAPSHOT)
                        .param("name", name)
                        .query(this::mapRow)
                        .optional(),
                e -> new SupraCause.Technical("snapshot.store.unexpected.error", e)
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
                e -> (e instanceof MappingException me) ? me.cause() : new SupraCause.Technical("snapshot.store.unexpected.error", e)
        );
    }

    private Snapshot mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return Result.all(
                        this.deserialize(rs.getString("invested_points")),
                        this.getRaceFromName(rs.getString("race_name")),
                        this.getProfessionFromName(rs.getString("profession_name"))
                )
                .flatMap((points, race, profession) -> this.buildSnapshot(rs, points, race, profession))
                .fold(
                        cause -> { throw new MappingException(cause); },
                        result -> result
                );
    }

    private Result<Race> getRaceFromName(final String name) {
        if (RaceName.isUndefined(name)) {
            return Result.success(Race.undefined());
        }

        return this.forGettingRaces.details()
                .stream()
                .filter(race -> race.name().name().equals(name))
                .findFirst()
                .map(Result::success)
                .orElseGet(() -> Result.failure(new SupraCause.Technical("race.unknown.in.compendium." + name, null)));
    }

    private Result<Profession> getProfessionFromName(final String name) {
        if (ProfessionName.isUndefined(name)) {
            return Result.success(Profession.undefined());
        }

        return this.forGettingProfession.details()
                .stream()
                .filter(race -> race.name().name().equals(name))
                .findFirst()
                .map(Result::success)
                .orElseGet(() -> Result.failure(new SupraCause.Technical("profession.unknown.in.compendium." + name, null)));
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
        var current = e;
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
