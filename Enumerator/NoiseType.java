package Enumerator;

public enum NoiseType
{
    RANDOM,
    PERLIN,
    SIMPLEX;

    // Returns the string representation of the noise type
    @Override
    public String toString()
    {
        return switch (this) {
            case RANDOM -> "Random";
            case PERLIN -> "Perlin";
            case SIMPLEX -> "Simplex";
        };
    }

    // Returns the noise type from the string representation
    public static NoiseType fromString(String str)
    {
        return switch (str) {
            case "Random" -> RANDOM;
            case "Perlin" -> PERLIN;
            case "Simplex" -> SIMPLEX;
            default -> throw new IllegalArgumentException("Invalid noise type: " + str);
        };
    }

    // Returns the int representation of the noise type
    public int toInt()
    {
        return switch (this) {
            case RANDOM -> 0;
            case PERLIN -> 1;
            case SIMPLEX -> 2;
        };
    }

    // Returns the noise type from the int representation
    public static NoiseType fromInt(int i)
    {
        return switch (i) {
            case 0 -> RANDOM;
            case 1 -> PERLIN;
            case 2 -> SIMPLEX;
            default -> throw new IllegalArgumentException("Invalid noise type: " + i);
        };
    }
}
