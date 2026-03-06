package be.charleshornick.supra.shared.characteristic;

import java.util.Objects;

public class PrimaryCharacteristic {
    private final PrimaryCharacteristicName name;
    private final int base;

    private PrimaryCharacteristic(final PrimaryCharacteristicName name, final int base) {
        this.name = name;
        this.base = base;
    }

    public static PrimaryCharacteristic with(final PrimaryCharacteristicName name, final int base) {
        return new PrimaryCharacteristic(name, Math.max(base, 6));
    }

    public PrimaryCharacteristicName getName() {
        return this.name;
    }

    public int getBase() {
        return this.base;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof PrimaryCharacteristic that)) return false;
        return this.base == that.base &&
                this.name == that.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.base);
    }
}
