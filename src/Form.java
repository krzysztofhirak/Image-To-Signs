import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Form extends JFrame{
    private JPanel mainPanel;
    private JPanel sourceImagePanel;
    private JPanel finalImagePanel;
    private JPanel infoPanel;

    BufferedImage sourceImage;

    public Form(int width, int height){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(false);
        setSize(width, height);
        add(mainPanel);

        mainPanel.setBackground(new Color(150, 150, 150));
        sourceImagePanel.setBackground(new Color(220, 220, 220));
        finalImagePanel.setBackground(new Color(220, 220, 220));

        ReadAndWrite rw = new ReadAndWrite();
        sourceImage = rw.ReadImage();
//        ImageIcon icon = new ImageIcon(sourceImage);
//        JLabel picLabel = new JLabel(icon);
//        sourceImagePanel.add(picLabel);
//        sourceImagePanel.repaint();
        Converter converter = new Converter();
        BufferedImage resizedImage;
        try {
            resizedImage = converter.resize(sourceImage, 0.25);
            rw.WriteImage(resizedImage);
            rw.WriteImageToNotepad(converter.toSigns(rw.ImageToPixels(resizedImage)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
