package Utils;

import Enumerator.InterpolationType;
import Enumerator.NoiseType;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

import java.util.Random;

public class NoiseParameters
{
    ////////// SIZE //////////
    private int width;
    private int height;

    ////////// NOISE PARAMETERS //////////
    private int seed; // La graine est un nombre qui permet de générer une suite de nombres aléatoires
    private int octaves; // Le nombre d'octaves est le nombre de fonctions de bruit superposées
    private double persistence; // La persistance est le facteur par lequel l'amplitude est multipliée à chaque octave
    private double lacunarity; // La lacunarité est le facteur par lequel la fréquence est multipliée à chaque octave
    private double scale; // La taille de la fonction de bruit
    private double offsetX; // Les offsets permettent de décaler la fonction de bruit
    private double offsetY; // Les offsets permettent de décaler la fonction de bruit
    private double amplitude; // L'amplitude est la hauteur maximale de la fonction
    private double frequency; // La fréquence est le nombre de cycles de la fonction sur une unité de distance
    private boolean smooth; // Le lissage permet de lisser les valeurs de bruit

    ////////// PERMUTATION TABLE //////////
    private int[] permutationTable;

    ////////// INTERPOLATION AND NOISE TYPE //////////
    private InterpolationType interpolationType;
    private NoiseType noiseType;

    ////////// RANDOM GENERATOR //////////
    private Random random;

    ////////// CONSTRUCTOR //////////
    public NoiseParameters()
    {
        this(512, 512, (int) System.currentTimeMillis(), false, 8, 0.5, 2.0, 0.01, 0.0, 0.0, 1.0, 1.0, InterpolationType.LINEAR, NoiseType.PERLIN);
    }

    public NoiseParameters(int width, int height, int seed)
    {
        this(width, height, seed, false, 8, 0.5, 2.0, 0.01, 0.0, 0.0, 1.0, 1.0, InterpolationType.LINEAR, NoiseType.PERLIN);
    }

    public NoiseParameters(int width, int height, int seed, boolean smooth)
    {
        this(width, height, seed, smooth, 8, 0.5, 2.0, 0.01, 0.0, 0.0, 1.0, 1.0, InterpolationType.LINEAR, NoiseType.PERLIN);
    }

    public NoiseParameters(int width, int height, int seed, boolean smooth, InterpolationType interpolationType, NoiseType noiseType)
    {
        this(width, height, seed, smooth, 8, 0.5, 2.0, 0.01, 0.0, 0.0, 1.0, 1.0, interpolationType, noiseType);
    }

    public NoiseParameters(int width, int height, int seed, boolean smooth, int octaves, double persistence, double lacunarity, double scale, double offsetX, double offsetY, double amplitude, double frequency, InterpolationType interpolationType, NoiseType noiseType)
    {
        this.width = width;
        this.height = height;
        this.seed = seed;
        this.octaves = octaves;
        this.persistence = persistence;
        this.lacunarity = lacunarity;
        this.scale = scale;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.smooth = smooth;
        this.interpolationType = interpolationType;
        this.noiseType = noiseType;

        random = new Random(seed);
        generatePermutationTable();
    }

    ////////// PERMUTATION TABLE GENERATION //////////
    private void generatePermutationTable()
    {
        if (permutationTable == null) { permutationTable = new int[512]; }

        // Fill the permutation table with values from 0 to 255
        for (int i = 0; i < 256; i++) { permutationTable[i] = i; }

        // Shuffle the permutation table
        for (int i = 0; i < 256; i++)
        {
            int randomIndex = random.nextInt(256);
            int temp = permutationTable[i];
            permutationTable[i] = permutationTable[randomIndex];
            permutationTable[randomIndex] = temp;
        }

        // Duplicate the permutation table
        for (int i = 0; i < 256; i++) { permutationTable[i + 256] = permutationTable[i]; }
    }

    ////////// GETTERS //////////
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getSeed() { return seed; }
    public int getOctaves() { return octaves; }
    public double getPersistence() { return persistence; }
    public double getLacunarity() { return lacunarity; }
    public double getScale() { return scale; }
    public double getOffsetX() { return offsetX; }
    public double getOffsetY() { return offsetY; }
    public double getAmplitude() { return amplitude; }
    public double getFrequency() { return frequency; }
    public boolean isSmooth() { return smooth; }
    public InterpolationType getInterpolationType() { return interpolationType; }
    public NoiseType getNoiseType() { return noiseType; }
    public int[] getPermutationTable() { return permutationTable; }

    ////////// SETTERS //////////
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setSeed(int seed) { this.seed = seed; }
    public void setOctaves(int octaves) { this.octaves = octaves; }
    public void setPersistence(double persistence) { this.persistence = persistence; }
    public void setLacunarity(double lacunarity) { this.lacunarity = lacunarity; }
    public void setScale(double scale) { this.scale = scale; }
    public void setOffsetX(double offsetX) { this.offsetX = offsetX; }
    public void setOffsetY(double offsetY) { this.offsetY = offsetY; }
    public void setAmplitude(double amplitude) { this.amplitude = amplitude; }
    public void setFrequency(double frequency) { this.frequency = frequency; }
    public void setSmooth(boolean smooth) { this.smooth = smooth; }
    public void setInterpolationType(InterpolationType interpolationType) { this.interpolationType = interpolationType; }
    public void setNoiseType(NoiseType noiseType) { this.noiseType = noiseType; }
    public void setPermutationTable(int[] permutationTable) { this.permutationTable = permutationTable; }

    ////////// OVERRIDDEN METHODS //////////
    @Override
    public String toString()
    {
        return "NoiseParameters{" +
                "objectName=" + super.toString() +
                ", width=" + width +
                ", height=" + height +
                ", seed=" + seed +
                ", octaves=" + octaves +
                ", persistence=" + persistence +
                ", lacunarity=" + lacunarity +
                ", scale=" + scale +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                ", amplitude=" + amplitude +
                ", frequency=" + frequency +
                ", smooth=" + smooth +
                ", interpolationType=" + interpolationType +
                ", noiseType=" + noiseType +
                '}';
    }

    ////////// INITIALIZATION METHODS //////////
    public void initialize(int width, int height, int seed, boolean smooth, int octaves, double persistence, double lacunarity, double scale, double offsetX, double offsetY, double amplitude, double frequency, InterpolationType interpolationType, NoiseType noiseType)
    {
        this.width = width;
        this.height = height;
        this.seed = seed;
        this.octaves = octaves;
        this.persistence = persistence;
        this.lacunarity = lacunarity;
        this.scale = scale;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.smooth = smooth;
        this.interpolationType = interpolationType;
        this.noiseType = noiseType;

        random = new Random(seed);
        generatePermutationTable();
    }

    public void initialize(NoiseParameters noiseParameters)
    {
        this.width = noiseParameters.getWidth();
        this.height = noiseParameters.getHeight();
        this.seed = noiseParameters.getSeed();
        this.octaves = noiseParameters.getOctaves();
        this.persistence = noiseParameters.getPersistence();
        this.lacunarity = noiseParameters.getLacunarity();
        this.scale = noiseParameters.getScale();
        this.offsetX = noiseParameters.getOffsetX();
        this.offsetY = noiseParameters.getOffsetY();
        this.amplitude = noiseParameters.getAmplitude();
        this.frequency = noiseParameters.getFrequency();
        this.smooth = noiseParameters.isSmooth();
        this.interpolationType = noiseParameters.getInterpolationType();
        this.noiseType = noiseParameters.getNoiseType();

        random = new Random(seed);
        generatePermutationTable();
    }

    ////////// I/O METHODS //////////
    public void writeInFile(String filePath, boolean overwrite)
    {
        // Si le fichier existe déjà
        if (new File(filePath).exists()) { if (!overwrite) { throw new IllegalArgumentException("The file already exists and you don't want to overwrite it."); } }

        // Transformer les paramètres en xml
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        xml += "<NoiseParameters>\n";
        xml += "\t<width>" + width + "</width>\n";
        xml += "\t<height>" + height + "</height>\n";
        xml += "\t<seed>" + seed + "</seed>\n";
        xml += "\t<octaves>" + octaves + "</octaves>\n";
        xml += "\t<persistence>" + persistence + "</persistence>\n";
        xml += "\t<lacunarity>" + lacunarity + "</lacunarity>\n";
        xml += "\t<scale>" + scale + "</scale>\n";
        xml += "\t<offsetX>" + offsetX + "</offsetX>\n";
        xml += "\t<offsetY>" + offsetY + "</offsetY>\n";
        xml += "\t<amplitude>" + amplitude + "</amplitude>\n";
        xml += "\t<frequency>" + frequency + "</frequency>\n";
        xml += "\t<smooth>" + smooth + "</smooth>\n";
        xml += "\t<interpolationType>" + interpolationType.toInt() + "</interpolationType>\n";
        xml += "\t<noiseType>" + noiseType.toInt() + "</noiseType>\n";
        xml += "</NoiseParameters>";

        // Écrire le xml dans le fichier
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(xml);
            writer.close();
        }
        catch (Exception e) { System.err.println("Error while writing in the file: " + e.getMessage()); }
    }

    public void readFromFile(String filePath)
    {
        // Lire le fichier
        try
        {
            File inputFile = new File(filePath);
            if (!inputFile.exists()) { throw new IllegalArgumentException("The file doesn't exist."); }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Récupérer les paramètres
            width = Integer.parseInt(doc.getElementsByTagName("width").item(0).getTextContent());
            height = Integer.parseInt(doc.getElementsByTagName("height").item(0).getTextContent());
            seed = Integer.parseInt(doc.getElementsByTagName("seed").item(0).getTextContent());
            octaves = Integer.parseInt(doc.getElementsByTagName("octaves").item(0).getTextContent());
            persistence = Double.parseDouble(doc.getElementsByTagName("persistence").item(0).getTextContent());
            lacunarity = Double.parseDouble(doc.getElementsByTagName("lacunarity").item(0).getTextContent());
            scale = Double.parseDouble(doc.getElementsByTagName("scale").item(0).getTextContent());
            offsetX = Double.parseDouble(doc.getElementsByTagName("offsetX").item(0).getTextContent());
            offsetY = Double.parseDouble(doc.getElementsByTagName("offsetY").item(0).getTextContent());
            amplitude = Double.parseDouble(doc.getElementsByTagName("amplitude").item(0).getTextContent());
            frequency = Double.parseDouble(doc.getElementsByTagName("frequency").item(0).getTextContent());
            smooth = Boolean.parseBoolean(doc.getElementsByTagName("smooth").item(0).getTextContent());
            interpolationType = InterpolationType.fromInt(Integer.parseInt(doc.getElementsByTagName("interpolationType").item(0).getTextContent()));
            noiseType = NoiseType.fromInt(Integer.parseInt(doc.getElementsByTagName("noiseType").item(0).getTextContent()));

            random = new Random(seed);
            generatePermutationTable();
        }
        catch (Exception e) { System.err.println("Error while reading from the file: " + e.getMessage()); }
    }

    public void write(ObjectOutputStream oos) throws IOException // ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("noiseParameters.dat"));
    {
        oos.writeInt(width);
        oos.writeInt(height);
        oos.writeInt(seed);
        oos.writeInt(octaves);
        oos.writeDouble(persistence);
        oos.writeDouble(lacunarity);
        oos.writeDouble(scale);
        oos.writeDouble(offsetX);
        oos.writeDouble(offsetY);
        oos.writeDouble(amplitude);
        oos.writeDouble(frequency);
        oos.writeBoolean(smooth);
        oos.writeInt(interpolationType.toInt());
        oos.writeInt(noiseType.toInt());
        oos.writeObject(permutationTable);
    }

    public void read(ObjectInputStream ois) throws IOException, ClassNotFoundException // ObjectInputStream ois = new ObjectInputStream(new FileInputStream("noiseParameters.dat"));
    {
        width = ois.readInt();
        height = ois.readInt();
        seed = ois.readInt();
        octaves = ois.readInt();
        persistence = ois.readDouble();
        lacunarity = ois.readDouble();
        scale = ois.readDouble();
        offsetX = ois.readDouble();
        offsetY = ois.readDouble();
        amplitude = ois.readDouble();
        frequency = ois.readDouble();
        smooth = ois.readBoolean();
        interpolationType = InterpolationType.fromInt(ois.readInt());
        noiseType = NoiseType.fromInt(ois.readInt());
        permutationTable = (int[]) ois.readObject();

        random = new Random(seed);
    }
}
