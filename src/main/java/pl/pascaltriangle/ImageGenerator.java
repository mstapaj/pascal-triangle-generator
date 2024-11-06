package pl.pascaltriangle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigInteger;

public class ImageGenerator {


    private static Color getColor(int number) {
        int r = (number * 123) % 256;
        int g = (number * 321) % 256;
        int b = (number * 231) % 256;
        return new Color(r, g, b);
    }

    public static BufferedImage generatePascalTriangleImage(PascalTriangle pascalTriangle, int moduloNumber, int width, int height) {
        int rows = pascalTriangle.getHeight();
        int squareSize = height / rows;
        BigInteger modulo = BigInteger.valueOf(moduloNumber);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);
        for (int row = 0; row < rows; row++) {
            int numSquares = row + 1;
            int rowWidth = numSquares * squareSize;
            int startX = (width - rowWidth) / 2;
            for (int col = 0; col < numSquares; col++) {
                BigInteger color = pascalTriangle.getRows().get(row).get(col).mod(modulo);
                g2d.setColor(getColor(color.intValue()));
                int x = startX + col * squareSize;
                int y = row * squareSize;
                g2d.fillRect(x, y, squareSize, squareSize);
            }
        }
        g2d.dispose();
        return bufferedImage;
    }
}
