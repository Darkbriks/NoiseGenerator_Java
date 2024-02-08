package NoiseGenerator;

import Utils.NoiseParameters;

public class SimplexNoise extends AbstractNoiseGenerator
{
    ////////// SINGLETON //////////
    private static SimplexNoise instance;
    public static SimplexNoise getInstance()
    {
        if (instance == null)
        {
            instance = new SimplexNoise();
        }
        return instance;
    }

    ////////// GLOBAL VARIABLES //////////
    // Coefficients pour calculer les coins du simplex
    private static final double F2 = 0.5 * (Math.sqrt(3.0) - 1.0);
    private static final double G2 = (3.0 - Math.sqrt(3.0)) / 6.0;

    ////////// METHODS //////////
    @Override
    protected double noiseAlgorithm(double x, double y, NoiseParameters noiseParameters)
    {
        // Somme pour obtenir le bruit
        double s = (x + y) * F2;
        int i = (int) Math.floor(x + s);
        int j = (int) Math.floor(y + s);

        // Coefficients pour calculer les coins du simplex
        double t = (i + j) * G2;
        double X0 = i - t;
        double Y0 = j - t;
        double x0 = x - X0;
        double y0 = y - Y0;

        // Determination des coins du simplex
        int i1, j1;

        if (x0 > y0) { i1 = 1; j1 = 0; }
        else { i1 = 0; j1 = 1; }

        double x1 = x0 - i1 + G2;
        double y1 = y0 - j1 + G2;
        double x2 = x0 - 1.0 + 2.0 * G2;
        double y2 = y0 - 1.0 + 2.0 * G2;

        // Calcul des gradients
        int ii = i & 255;
        int jj = j & 255;
        int gi0 = noiseParameters.getPermutationTable()[ii + noiseParameters.getPermutationTable()[jj]] % 12;
        int gi1 = noiseParameters.getPermutationTable()[ii + i1 + noiseParameters.getPermutationTable()[jj + j1]] % 12;
        int gi2 = noiseParameters.getPermutationTable()[ii + 1 + noiseParameters.getPermutationTable()[jj + 1]] % 12;

        // Calcul des contributions des coins du simplex
        double n0, n1, n2;

        double t0 = 0.5 - x0 * x0 - y0 * y0;
        if (t0 < 0) n0 = 0;
        else
        {
            t0 *= t0;
            n0 = t0 * t0 * grad(noiseParameters.getPermutationTable()[gi0], x0, y0);
        }

        double t1 = 0.5 - x1 * x1 - y1 * y1;
        if (t1 < 0) n1 = 0;
        else
        {
            t1 *= t1;
            n1 = t1 * t1 * grad(noiseParameters.getPermutationTable()[gi1], x1, y1);
        }

        double t2 = 0.5 - x2 * x2 - y2 * y2;
        if (t2 < 0) n2 = 0;
        else
        {
            t2 *= t2;
            n2 = t2 * t2 * grad(noiseParameters.getPermutationTable()[gi2], x2, y2);
        }

        // Somme des contributions des coins du simplex
        return 70.0 * (n0 + n1 + n2);
    }
}
