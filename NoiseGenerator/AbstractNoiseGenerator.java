package NoiseGenerator;

import Utils.NoiseParameters;

import java.util.HashMap;

public abstract class AbstractNoiseGenerator
{
    ////////// UTILITIES METHODS //////////
    protected static double fade(double t) // Le "fade" est utilisé pour lisser les valeurs de la fonction de bruit
    {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    protected static double grad(int hash, double x, double y) // On utilise le hash pour obtenir un vecteur aléatoire
    {
        int h = hash & 15;
        double u = h < 8 ? x : y;
        double v = h < 4 ? y : h == 12 || h == 14 ? x : 0;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    ////////// METHODS //////////
    public double[][] generateNoise(NoiseParameters noiseParameters)
    {
        double[][] noise = new double[noiseParameters.getWidth()][noiseParameters.getHeight()];

        for (int x = 0; x < noiseParameters.getWidth(); x++)
        {
            for (int y = 0; y < noiseParameters.getHeight(); y++)
            {
                noise[x][y] = getNoise(x, y, noiseParameters);
            }
        }
        return noise;
    }

    protected double getNoise(int x, int y, NoiseParameters noiseParameters)
    {
        double total = 0;
        double frequency = noiseParameters.getScale();
        double amplitude = noiseParameters.getAmplitude();

        for (int i = 0; i < noiseParameters.getOctaves(); i++)
        {
            total += noiseAlgorithm(x * frequency + noiseParameters.getOffsetX(), y * frequency + noiseParameters.getOffsetY(), noiseParameters) * amplitude;
            frequency *= noiseParameters.getLacunarity();
            amplitude *= noiseParameters.getPersistence();
        }

        //System.out.println("Total: " + total);
        return total;
    }

    ////////// ABSTRACT METHODS //////////
    protected abstract double noiseAlgorithm(double x, double y, NoiseParameters noiseParameters);
}
