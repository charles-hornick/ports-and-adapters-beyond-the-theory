package be.charleshornick.supra.shared.point;

import be.charleshornick.supra.shared.CreationPointConsumer;

import java.util.Objects;

public class CreationPoint {
    private static final int base = 12;
    private final int consumed;

    private CreationPoint(final int consumed) {
        this.consumed = consumed;
    }

    public static CreationPoint beginning() {
        return new CreationPoint(0);
    }

    public CreationPoint addNewConsumer(final CreationPointConsumer consumer) {
        return (consumer != null)
                ? new CreationPoint(this.consumed + consumer.getCostInCreationPoint())
                : this;
    }

    public boolean hasEnoughCreationToAdd(final CreationPointConsumer consumerToAdd) {
        return (base - this.consumed - consumerToAdd.getCostInCreationPoint()) >= 0;
    }

    public int getPointsLeft() {
        return base - consumed;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreationPoint that = (CreationPoint) o;
        return this.consumed == that.consumed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.consumed);
    }
}
