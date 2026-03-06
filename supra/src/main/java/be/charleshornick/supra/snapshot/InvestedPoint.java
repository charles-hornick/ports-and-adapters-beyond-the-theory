package be.charleshornick.supra.snapshot;

import be.charleshornick.supra.characteristic.PrimaryCharacteristic;
import be.charleshornick.supra.characteristic.PrimaryCharacteristicName;
import be.charleshornick.supra.race.Race;
import org.pragmatica.lang.Result;
import org.pragmatica.lang.utils.Causes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class InvestedPoint {

    private final Map<PrimaryCharacteristicName, Integer> mapOfInvestedPoints;
    private final int maxByCategory;
    private final int maxByCharacteristic;
    private final int maxInvestedPoints;
    private final Race race;

    private InvestedPoint(final Map<PrimaryCharacteristicName, Integer> mapOfInvestedPoints, final Race race) {
        this.mapOfInvestedPoints = mapOfInvestedPoints;
        this.race = race;
        this.maxByCategory = (race.highRace()) ? 12 : 9;
        this.maxByCharacteristic = (race.highRace()) ? 7 : 5;
        this.maxInvestedPoints = (race.highRace()) ? 30 : 27;
    }

    public static InvestedPoint with(final Map<PrimaryCharacteristicName, Integer> investedPoints, final Race race) {
        return new InvestedPoint(new HashMap<>(investedPoints), race);
    }

    private Integer getTotalOfInvestedPoints() {
        return Arrays.stream(PrimaryCharacteristicName.values())
                .map(n -> this.mapOfInvestedPoints.getOrDefault(n, 0))
                .reduce(0, Integer::sum);
    }

    private Integer getTotalOfInvestedPointOnTheCategory(final PrimaryCharacteristicName name) {
        return PrimaryCharacteristicName.getCategoryRelatedTo(name)
                .stream()
                .map(n -> this.mapOfInvestedPoints.getOrDefault(n, 0))
                .reduce(0, Integer::sum);
    }

    private boolean canAddPointToCharacteristic(final PrimaryCharacteristicName name) {
        return this.getTotalOfInvestedPointOnTheCategory(name) < this.maxByCategory &&
                this.mapOfInvestedPoints.getOrDefault(name, 0) < this.maxByCharacteristic &&
                this.getTotalOfInvestedPoints() < this.maxInvestedPoints;
    }

    public Result<InvestedPoint> addPointToCharacteristic(final PrimaryCharacteristicName name) {
        if (this.canAddPointToCharacteristic(name)) {
            final int investedPoint = this.mapOfInvestedPoints.getOrDefault(name, 0);
            final var newMap = new HashMap<>(this.mapOfInvestedPoints);
            newMap.put(name, investedPoint + 1);
            return Result.ok(new InvestedPoint(newMap, this.race));
        }
        return Result.failure(Causes.cause("Cannot add any more points to "+ name));
    }

    private boolean canRemovePointToCharacteristic(final PrimaryCharacteristicName name) {
        return this.mapOfInvestedPoints.getOrDefault(name, 0) > 0;
    }

    public Result<InvestedPoint> removePointToCharacteristic(final PrimaryCharacteristicName name) {
        if (this.canRemovePointToCharacteristic(name)) {
            final int investedPoint = this.mapOfInvestedPoints.getOrDefault(name, 0);
            final var newMap = new HashMap<>(this.mapOfInvestedPoints);
            newMap.put(name, investedPoint - 1);
            return Result.success(new InvestedPoint(newMap, this.race));
        }
        return Result.failure(Causes.cause("Cannot remove any more points to "+ name));
    }

    public Map<PrimaryCharacteristicName, Integer> computeWithRace() {
        return this.race
                .characteristics()
                .stream()
                .collect(Collectors.toMap(
                        PrimaryCharacteristic::name,
                        c -> c.base() + this.mapOfInvestedPoints.getOrDefault(c.name(), 0)
                ));
    }

    public Map<PrimaryCharacteristicName, Integer> getInvestedPoints() {
        return new HashMap<>(this.mapOfInvestedPoints);
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final InvestedPoint that = (InvestedPoint) o;
        return Objects.equals(this.mapOfInvestedPoints, that.mapOfInvestedPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mapOfInvestedPoints);
    }
}
