import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Converter {

    public static char space = ' ';
    public static char acute = '`';
    public static char period = '.';
    public static char apostrophe = '\'';
    public static char minus = '-';
    public static char caret = '^';
    public static char asterisk = '*';
    public static char exclamation = '!';
    public static char plus = '+';
    public static char o = 'o';
    public static char zero = '0';
    public static char percent = '%';
    public static char at = '%';
    public static char hash = '#';
    public static char dollar = '$';
    public static char ampersand = '&';
    public static char EOL = (char) 10;
    public static char X = 'X';
    public static String newline = System.lineSeparator();

    public char[][] toSigns(int[][][] pixels){
        int width = pixels.length;
        int height = pixels[0].length;
        System.out.println(width + " " + height);
        char[][] chars = new char[width+1][(int) Math.ceil((double) height/2)];
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height-1; y=y+2){
                chars[x][y/2] = PixelToSignBetter(pixels[x][y]);
            }
        }
        for(int a = 0; a < height/2; a++){
            chars[width][a] = EOL;
        }
        return chars;
    }

    public BufferedImage resize(BufferedImage image, double factor) throws IOException {
        double proportions = (double) image.getWidth()/image.getHeight();
        int targetWidth = (int) (image.getWidth() * factor);
        int targetHeight = (int) (image.getHeight() * factor);
        if(targetWidth > 1024){
            targetWidth = 1024;
            targetHeight = (int) Math.floor(targetWidth/proportions);
        }
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    @Deprecated
    private char PixelToSign(int[] pixel){
        if (grayscale(pixel) >= 0 && grayscale(pixel) < 32){
            return at;
        } else if (grayscale(pixel) >= 32 && grayscale(pixel) < 64){
            return ampersand;
        } else if (grayscale(pixel) >= 64 && grayscale(pixel) < 96){
            return hash;
        } else if (grayscale(pixel) >= 96 && grayscale(pixel) < 128){
            return zero;
        } else if (grayscale(pixel) >= 128 && grayscale(pixel) < 160){
            return o;
        } else if (grayscale(pixel) >= 160 && grayscale(pixel) < 192){
            return asterisk;
        } else if (grayscale(pixel) >= 192 && grayscale(pixel) < 224){
            return period;
        } else if (grayscale(pixel) >= 224 && grayscale(pixel) < 256){
            return space;
        } else return X;
    }

    private char PixelToSignBetter(int[] pixel){
        if (grayscale(pixel) >= 0 && grayscale(pixel) < 16){
            return ampersand;
        } else if (grayscale(pixel) >= 16 && grayscale(pixel) < 32){
            return dollar;
        } else if (grayscale(pixel) >= 32 && grayscale(pixel) < 48){
            return hash;
        } else if (grayscale(pixel) >= 48 && grayscale(pixel) < 64){
            return at;
        } else if (grayscale(pixel) >= 64 && grayscale(pixel) < 80){
            return percent;
        } else if (grayscale(pixel) >= 80 && grayscale(pixel) < 96){
            return zero;
        } else if (grayscale(pixel) >= 96 && grayscale(pixel) < 112){
            return o;
        } else if (grayscale(pixel) >= 112 && grayscale(pixel) < 128){
            return plus;
        } else if (grayscale(pixel) >= 128 && grayscale(pixel) < 144){
            return exclamation;
        } else if (grayscale(pixel) >= 144 && grayscale(pixel) < 160){
            return asterisk;
        } else if (grayscale(pixel) >= 160 && grayscale(pixel) < 176){
            return caret;
        } else if (grayscale(pixel) >= 176 && grayscale(pixel) < 192){
            return minus;
        } else if (grayscale(pixel) >= 192 && grayscale(pixel) < 208){
            return apostrophe;
        } else if (grayscale(pixel) >= 208 && grayscale(pixel) < 224){
            return period;
        } else if (grayscale(pixel) >= 224 && grayscale(pixel) < 240){
            return acute;
        } else if (grayscale(pixel) >= 240 && grayscale(pixel) < 256){
            return space;
        } else return X;
    }

    private int grayscale(int[] pixel){
        return (pixel[1] + pixel[2] + pixel[3])/3;
    }

    @Deprecated
    public static String charsArrayToString(char[][] chars){
        StringBuilder charsString = new StringBuilder();
        for(int x = 0; x < chars.length; x++){
            for(int y = 0; y < chars[0].length; y++){
                if(chars[x][y] != 10){
                    charsString.append(chars[x][y]);
                } else {
                    charsString.append(newline);
                }
            }
        }
        return charsString.toString();
    }

    public static String[] charsArrayToStringsArray(char[][] chars){
        int height = chars.length;
        StringBuilder[] stringBuilders = new StringBuilder[height];
        for(int a = 0; a < height; a++){
            stringBuilders[a] = new StringBuilder();
        }
        for(int x = 0; x < chars.length; x++){
            for(int y = 0; y < chars[0].length; y++){
                if(chars[x][y] != 10){
                    stringBuilders[x].append(chars[x][y]);
                }
            }
            stringBuilders[x].append(System.getProperty("line.separator"));
        }
        String[] strings = new String[height];
        for(int a = 0; a < stringBuilders.length; a++){
            strings[a] = stringBuilders[a].toString();
        }
        return strings;
    }

    public String textFromFile(){
        BufferedReader br;
        String text;
        try {
            br = new BufferedReader(new FileReader(ReadAndWrite.TEXT_FILE_PATH));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            text = sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text;
    }

    public static char[][] invert(char[][] chars){
        char[][] newChars = new char[chars[0].length][chars.length];
        for(int x = 0; x < chars[0].length; x++){
            for(int y = 0; y < chars.length; y++){
                newChars[x][y] = chars[y][x];
            }
        }
        return newChars;
    }

    public static Color intToColor(int rgb){
        int r = (rgb>>16)&0xFF;
        int g = (rgb>>8)&0xFF;
        int b = (rgb)&0xFF;
        return new Color(r, g, b);
    }
}
