package be.charleshornick.supra.characteristic;

public record PrimaryCharacteristic(PrimaryCharacteristicName name, int base) {

    public PrimaryCharacteristic {
        base = Math.max(base, 6);
    }

    public static PrimaryCharacteristic with(final PrimaryCharacteristicName name, final int base) {
        return new PrimaryCharacteristic(name, base);
    }
}
