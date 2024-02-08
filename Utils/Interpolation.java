package Utils;

public class Interpolation
{
    public static <T extends Number> T interpolate(T a, T b, T t, Enumerator.InterpolationType interpolationType)
    {
        return switch (interpolationType) {
            case LINEAR -> linear(a, b, t);
            case COSINES -> cosines(a, b, t);
            case CUBIC -> cubic(a, b, t);
        };
    }

    public static <T extends Number> T linear(T a, T b, T t)
    {
        double result = a.doubleValue() * (1 - t.doubleValue()) + b.doubleValue() * t.doubleValue();
        return getT(a, b, result);
    }

    public static <T extends Number> T cosines(T a, T b, T t)
    {
        double ft = t.doubleValue() * Math.PI;
        double f = (1 - Math.cos(ft)) * 0.5;
        double result = a.doubleValue() * (1 - f) + b.doubleValue() * f;
        return getT(a, b, result);
    }

    public static <T extends Number> T cubic(T a, T b, T t)
    {
        double ft = t.doubleValue();
        double f = ft * ft * (3 - 2 * ft);
        double result = a.doubleValue() * (1 - f) + b.doubleValue() * f;
        return getT(a, b, result);
    }

    private static <T extends Number> T getT(T a, T b, double result) {
        if (a instanceof Double || b instanceof Double)
            return (T) Double.valueOf(result);
        else if (a instanceof Float || b instanceof Float)
            return (T) Float.valueOf((float) result);
        else if (a instanceof Long || b instanceof Long)
            return (T) Long.valueOf((long) result);
        else if (a instanceof Integer || b instanceof Integer)
            return (T) Integer.valueOf((int) result);
        else if (a instanceof Short || b instanceof Short)
            return (T) Short.valueOf((short) result);
        else if (a instanceof Byte || b instanceof Byte)
            return (T) Byte.valueOf((byte) result);
        else
            throw new IllegalArgumentException("Invalid number type: " + a.getClass().getName());
    }
}
