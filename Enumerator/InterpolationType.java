package Enumerator;

public enum InterpolationType
{
    LINEAR,
    COSINES,
    CUBIC;

    // Returns the string representation of the noise type
    @Override
    public String toString()
    {
        return switch (this) {
            case LINEAR -> "Linear";
            case COSINES -> "Cosines";
            case CUBIC -> "Cubic";
        };
    }

    // Returns the noise type from the string representation
    public static InterpolationType fromString(String str)
    {
        return switch (str) {
            case "Linear" -> LINEAR;
            case "Cosines" -> COSINES;
            case "Cubic" -> CUBIC;
            default -> throw new IllegalArgumentException("Invalid noise type: " + str);
        };
    }

    // Returns the int representation of the noise type
    public int toInt()
    {
        return switch (this) {
            case LINEAR -> 0;
            case COSINES -> 1;
            case CUBIC -> 2;
        };
    }

    // Returns the noise type from the int representation
    public static InterpolationType fromInt(int i)
    {
        return switch (i) {
            case 0 -> LINEAR;
            case 1 -> COSINES;
            case 2 -> CUBIC;
            default -> throw new IllegalArgumentException("Invalid noise type: " + i);
        };
    }
}