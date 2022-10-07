import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReadAndWrite {

    public static String IMAGE_FILE_PATH = "C:\\Users\\khirak\\OneDrive - Capgemini\\Desktop\\Private\\Projects\\Java\\Image-To-Signs\\Image.jpg";
    public static String TEXT_FILE_PATH = "C:\\Users\\khirak\\OneDrive - Capgemini\\Desktop\\Private\\Projects\\Java\\Image-To-Signs\\Image.txt";

    public BufferedImage ReadImage(){
        BufferedImage image = null;
        try {
            File file = new File(IMAGE_FILE_PATH);
            BufferedImage bimg = ImageIO.read(file);
            int width = bimg.getWidth();
            int height = bimg.getHeight();
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(file);
            System.out.println("Image loaded.");
        }
        catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return image;
    }

    public void WriteImage(BufferedImage image){
        try {
            String path = IMAGE_FILE_PATH = IMAGE_FILE_PATH.substring(0, IMAGE_FILE_PATH.length() - 4) + "_Resized.jpg";
            File output_file = new File(path);
            ImageIO.write(image, "png", output_file);
            System.out.println("Writing complete.");
        }
        catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public void WriteImageToNotepad(char[][] chars){
        try {
            FileWriter myWriter = new FileWriter(TEXT_FILE_PATH);
            System.out.println(TEXT_FILE_PATH);
//            myWriter.write(Converter.charsArrayToString(chars));
            String[] strings = Converter.charsArrayToStringsArray(Converter.invert(chars));
//            String[] strings = Converter.charsArrayToStringsArray(chars);
            for(int a = 0; a < strings.length; a++){
                myWriter.write(strings[a]);
            }
            myWriter.close();
            System.out.println("Saved to text file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][][] ImageToPixels(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] pixels = new int[width][height][5];
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                Color color = Converter.intToColor(image.getRGB(x, y));
                pixels[x][y][1] = color.getRed();
                pixels[x][y][2] = color.getGreen();
                pixels[x][y][3] = color.getBlue();
                pixels[x][y][4] = image.getRGB(x, y);
            }
        }
        return pixels;
    }
}
