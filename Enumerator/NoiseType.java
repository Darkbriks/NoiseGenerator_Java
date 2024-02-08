package Enumerator;

public enum NoiseType
{
    PERLIN,
    SIMPLEX;

    // Returns the string representation of the noise type
    @Override
    public String toString()
    {
        return switch (this) {
            case PERLIN -> "Perlin";
            case SIMPLEX -> "Simplex";
        };
    }

    // Returns the noise type from the string representation
    public static NoiseType fromString(String str)
    {
        return switch (str) {
            case "Perlin" -> PERLIN;
            case "Simplex" -> SIMPLEX;
            default -> throw new IllegalArgumentException("Invalid noise type: " + str);
        };
    }

    // Returns the int representation of the noise type
    public int toInt()
    {
        return switch (this) {
            case PERLIN -> 0;
            case SIMPLEX -> 1;
        };
    }

    // Returns the noise type from the int representation
    public static NoiseType fromInt(int i)
    {
        return switch (i) {
            case 0 -> PERLIN;
            case 1 -> SIMPLEX;
            default -> throw new IllegalArgumentException("Invalid noise type: " + i);
        };
    }
}
