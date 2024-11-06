package pl.pascaltriangle;

import pl.pascaltriangle.ui.MainFrame;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class Main {
//    public static void main(String[] args) throws IOException {
//        PascalTriangle pascalTriangle = new PascalTriangle(500);
//        pascalTriangle.printTriangle();
//        ImageGenerator.generatePascalTriangleImage(pascalTriangle, BigInteger.valueOf(2));
//    }

    public static void setUIFont(FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

    public static void main(String[] args) {
        int fontSize = 20;
        setUIFont(new FontUIResource(new Font("Arial", Font.PLAIN, fontSize)));
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}