package NoiseGenerator;

import Utils.NoiseParameters;

import java.util.Random;

public class RandomNoise extends AbstractNoiseGenerator
{
    ////////// SINGLETON //////////
    private static RandomNoise instance;
    public static RandomNoise getInstance()
    {
        if (instance == null)
        {
            instance = new RandomNoise();
            random = new Random();
        }
        return instance;
    }

    ////////// GLOBAL VARIABLES //////////
    private static Random random;
    private static int seed;

    ////////// METHODS //////////
    @Override
    protected double noiseAlgorithm(double x, double y, NoiseParameters noiseParameters)
    {
        if (seed != noiseParameters.getSeed())
        {
            seed = noiseParameters.getSeed();
            random.setSeed(seed);
        }
        // retourne une valeur aléatoire entre -amplitude et amplitude
        return random.nextDouble() * 2 * noiseParameters.getAmplitude() - noiseParameters.getAmplitude();
    }
}
