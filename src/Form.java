import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Form extends JFrame{
    private JPanel mainPanel;
    private JPanel sourceImagePanel;
    private JPanel finalImagePanel;
    private JPanel infoPanel;
    private JButton fileChooserButton;
    private JTextArea textArea;
    private JTextField resizeTextField;
    private JButton generateButton;

    BufferedImage sourceImage;

    public Form(int width, int height){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, height);
        add(mainPanel);

        mainPanel.setBackground(new Color(200, 200, 200));
        infoPanel.setBackground(new Color(200, 200, 200));
        sourceImagePanel.setBackground(new Color(220, 220, 220));
        finalImagePanel.setBackground(new Color(220, 220, 220));

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                ".jpg", "jpg");
        fileChooser.setFileFilter(filter);
        fileChooser.setAlignmentX(RIGHT_ALIGNMENT);

        fileChooserButton.addActionListener(e -> {
            int returnVal = fileChooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("File " + fileChooser.getSelectedFile().getName() + " opened.");
                ReadAndWrite.IMAGE_FILE_PATH = fileChooser.getSelectedFile().getPath();
                ReadAndWrite.TEXT_FILE_PATH = fileChooser.getSelectedFile().getParent() + "\\Image.txt";
            }
        });

        generateButton.addActionListener(e -> {
            Generate();
        });
    }

    private void Generate(){
        ReadAndWrite rw = new ReadAndWrite();
        sourceImage = rw.ReadImage();
//        ImageIcon icon = new ImageIcon(sourceImage);
//        JLabel picLabel = new JLabel(icon);
//        sourceImagePanel.add(picLabel);
//        sourceImagePanel.repaint();
        Converter converter = new Converter();
        BufferedImage resizedImage;
        try {
            resizedImage = converter.resize(sourceImage, Double.parseDouble(resizeTextField.getText()));
            rw.WriteImage(resizedImage);
            rw.WriteImageToNotepad(converter.toSigns(rw.ImageToPixels(resizedImage)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Lucida Console", Font.PLAIN, 5));
        textArea.setText(converter.textFromFile());
    }
}
