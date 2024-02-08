import Enumerator.InterpolationType;
import Enumerator.NoiseType;
import NoiseGenerator.NoiseGenerator;
import Utils.NoiseParameters;

public class Main
{
    public static void main(String[] args) throws Exception {
        NoiseParameters noiseParameters = new NoiseParameters();
        noiseParameters.setNoiseType(NoiseType.SIMPLEX);

        NoiseGenerator noiseGenerator = new NoiseGenerator(noiseParameters);
        double[][] noise = noiseGenerator.generateNoise();
        noiseGenerator.generateGreyscaleImage("noise.png", noise, noiseParameters.getAmplitude(), 1, true);

        return;
    }
}