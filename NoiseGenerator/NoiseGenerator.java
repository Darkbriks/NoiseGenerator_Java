package NoiseGenerator;

import Enumerator.NoiseType;
import Utils.NoiseParameters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class NoiseGenerator
{
    ////////// PARAMETERS //////////
    private static NoiseParameters noiseParameters;

    ////////// CONSTRUCTORS //////////
    public NoiseGenerator()
    {
        this(new NoiseParameters());
    }
    public NoiseGenerator(NoiseParameters noiseParameters)
    {
        NoiseGenerator.noiseParameters = noiseParameters;
    }

    ////////// GETTERS //////////
    public NoiseParameters getNoiseParameters()
    {
        return noiseParameters;
    }

    ////////// SETTERS //////////
    public void setNoiseParameters(NoiseParameters noiseParameters)
    {
        NoiseGenerator.noiseParameters = noiseParameters;
    }

    ////////// METHODS //////////
    public double[][] generateNoise()
    {
        return switch (noiseParameters.getNoiseType()) {
            case RANDOM -> RandomNoise.getInstance().generateNoise(noiseParameters);
            case PERLIN -> PerlinNoise.getInstance().generateNoise(noiseParameters);
            case SIMPLEX -> SimplexNoise.getInstance().generateNoise(noiseParameters);
        };
    }

    ////////// I/O METHODS //////////
    public void generateGreyscaleImage(String path, double[][] noise, double amplitude, int depth, boolean overwrite) throws Exception
    {
        int width = noise.length, height = noise[0].length;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                double value = noise[x][y];

                // Normalize the value between 0 and 255
                value = ((value + amplitude) * 255 / (2 * amplitude));

                // Apply the depth (depth is the number of different shades of grey)
                // 255 -> no change
                // 1 -> only black and white
                value = Math.round(value / depth) * depth;

                // Clamp the value between 0 and 255
                value = Math.min(255, Math.max(0, value));
                img.setRGB(x, y, new Color((int) value, (int) value, (int) value).getRGB());
            }
        }

        try
        {
            if (!overwrite && new java.io.File(path).exists()) { throw new Exception("The file already exists and you don't want to overwrite it."); }

            File output = new File(path);
            ImageIO.write(img, "png", output);
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
