package NoiseGenerator;

import Utils.NoiseParameters;

import static Utils.Interpolation.interpolate;

public class PerlinNoise extends AbstractNoiseGenerator
{
    ////////// SINGLETON //////////
    private static PerlinNoise instance;

    public static PerlinNoise getInstance()
    {
        if (instance == null)
        {
            instance = new PerlinNoise();
        }
        return instance;
    }

    ////////// METHODS //////////
    @Override
    protected double noiseAlgorithm(double x, double y, NoiseParameters noiseParameters)
    {
        // Get integer part of x and y
        int xi = (int) x & 255;
        int yi = (int) y & 255;

        // Get decimal part of x and y
        double xf = x - (int) x;
        double yf = y - (int) y;

        // Calcul des gradients
        double u = noiseParameters.isSmooth() ? fade(xf) : xf;
        double v = noiseParameters.isSmooth() ? fade(yf) : yf;

        // Get the hash of the 4 corners
        int a = noiseParameters.getPermutationTable()[xi] + yi;
        int aa = noiseParameters.getPermutationTable()[a];
        int ab = noiseParameters.getPermutationTable()[a + 1];
        int b = noiseParameters.getPermutationTable()[xi + 1] + yi;
        int ba = noiseParameters.getPermutationTable()[b];
        int bb = noiseParameters.getPermutationTable()[b + 1];

        // Interpolation des gradients
        double grad1 = grad(noiseParameters.getPermutationTable()[aa], xf, yf);
        double grad2 = grad(noiseParameters.getPermutationTable()[ba], xf - 1, yf);
        double x1 = interpolate(grad1, grad2, u, noiseParameters.getInterpolationType());

        grad1 = grad(noiseParameters.getPermutationTable()[ab], xf, yf - 1);
        grad2 = grad(noiseParameters.getPermutationTable()[bb], xf - 1, yf - 1);
        double x2 = interpolate(grad1, grad2, u, noiseParameters.getInterpolationType());

        //System.out.println(interpolate(x1, x2, v, noiseParameters.getInterpolationType()));
        return interpolate(x1, x2, v, noiseParameters.getInterpolationType());
    }

}
