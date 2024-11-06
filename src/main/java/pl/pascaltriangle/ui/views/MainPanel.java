package pl.pascaltriangle.ui.views;

import pl.pascaltriangle.ImageGenerator;
import pl.pascaltriangle.PascalTriangle;
import pl.pascaltriangle.ui.ErrorMessageFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

import static pl.pascaltriangle.ui.DefaultValues.timeoutMinutes;

public class MainPanel extends JPanel {

    private final JTextField triangleHeight = new JTextField("500");
    private final JTextField moduloNumber = new JTextField("2");
    private final JTextField imageHeight = new JTextField("1024");
    private final JTextField imageWidth = new JTextField("1024");
    private final JLabel imageLabel;
    private final JTextField filename = new JTextField();
    private final JTextField dir = new JTextField();

    public MainPanel() {
        setLayout(new BorderLayout());

        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftSide = new JPanel();
        leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.Y_AXIS));
        leftSide.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        leftSide.add(new JLabel("Triangle height:"));
        leftSide.add(new JLabel("Modulo value:"));
        leftSide.add(new JLabel("Image height:"));
        leftSide.add(new JLabel("Image width:"));
        center.add(leftSide, BorderLayout.WEST);

        JPanel centerSide = new JPanel();
        centerSide.setLayout(new BoxLayout(centerSide, BoxLayout.Y_AXIS));
        centerSide.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.triangleHeight.setMaximumSize(new Dimension(700, 29));
        this.moduloNumber.setMaximumSize(new Dimension(700, 29));
        this.imageHeight.setMaximumSize(new Dimension(700, 29));
        this.imageWidth.setMaximumSize(new Dimension(700, 29));

        centerSide.add(this.triangleHeight);
        centerSide.add(this.moduloNumber);
        centerSide.add(this.imageHeight);
        centerSide.add(this.imageWidth);
        center.add(centerSide, BorderLayout.CENTER);

        JPanel rightSide = new JPanel();
        rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));
        rightSide.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton printPascalTriangleButton = new JButton("Print Pascal Triangle");
        printPascalTriangleButton.addActionListener(new PrintPascalTriangle());
        rightSide.add(printPascalTriangleButton);
        JButton saveImageAsButton = new JButton("Save image as");
        saveImageAsButton.addActionListener(new SaveImageAs());
        rightSide.add(saveImageAsButton);
        center.add(rightSide, BorderLayout.EAST);

        center.setPreferredSize(new Dimension(400, 400));
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Use FlowLayout to center content
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottom.add(imageLabel);

        add(bottom, BorderLayout.SOUTH);
    }

    private void setImage(BufferedImage image) {
        ImageIcon icon = new ImageIcon(image);
        imageLabel.setIcon(icon);
        imageLabel.revalidate();
    }

    private BufferedImage prepareImage() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<BufferedImage> future = executor.submit(() -> {
            PascalTriangle pascalTriangle = new PascalTriangle(Integer.parseInt(triangleHeight.getText()));
            return ImageGenerator.generatePascalTriangleImage(
                    pascalTriangle,
                    Integer.parseInt(moduloNumber.getText()),
                    Integer.parseInt(imageWidth.getText()),
                    Integer.parseInt(imageHeight.getText()));
        });
        try {
            return future.get(timeoutMinutes, TimeUnit.SECONDS);
        } catch (TimeoutException err) {
            new ErrorMessageFrame("Timeout error: Calculation took too long");
            future.cancel(true);
        } catch (InterruptedException | ExecutionException err) {
            new ErrorMessageFrame("Error: " + err.getMessage());
        } finally {
            executor.shutdown();
        }
        return null;
    }

    private class PrintPascalTriangle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BufferedImage resultImage = prepareImage();
            setImage(resultImage);
        }
    }

    private void saveImageAsFile(BufferedImage image) {
        JFileChooser jFileChooser = new JFileChooser();
        int rVal = jFileChooser.showSaveDialog(MainPanel.this);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            filename.setText(selectedFile.getName());
            dir.setText(selectedFile.getParent());
            try {
                ImageIO.write(image, "jpg", selectedFile);
            } catch (IOException err) {
                new ErrorMessageFrame("Error during saving image: " + err.getMessage());
            }
        }
    }

    private class SaveImageAs implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BufferedImage resultImage = prepareImage();
            saveImageAsFile(resultImage);
        }
    }
}
