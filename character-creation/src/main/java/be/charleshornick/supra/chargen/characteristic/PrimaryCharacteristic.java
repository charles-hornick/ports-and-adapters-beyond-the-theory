package be.charleshornick.supra.chargen.characteristic;

public record PrimaryCharacteristic(PrimaryCharacteristicName name, int base) {

    public PrimaryCharacteristic {
        if (base < 6) {
            throw new IllegalArgumentException("characteristic base below minimum: " + name + "=" + base);
        }
    }

    public static PrimaryCharacteristic with(final PrimaryCharacteristicName name, final int base) {
        return new PrimaryCharacteristic(name, base);
    }
}
