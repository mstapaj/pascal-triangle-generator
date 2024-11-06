package pl.pascaltriangle;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PascalTriangle {
    private final int height;
    private final ArrayList<ArrayList<BigInteger>> rows;

    public PascalTriangle(int height) {
        this.height = height;
        this.rows = generatePascalTriangle(height);
    }

    private ArrayList<ArrayList<BigInteger>> generatePascalTriangle(int height) {
        ArrayList<ArrayList<BigInteger>> newTriangle = new ArrayList<>();
        ArrayList<BigInteger> firstRow = new ArrayList<>(List.of(BigInteger.ONE));
        newTriangle.add(firstRow);
        for (int i = 1; i < height; i++) {
            ArrayList<BigInteger> row = new ArrayList<>(List.of(BigInteger.ONE));
            for (int j = 1; j < i; j++) {
                ArrayList<BigInteger> previousRow = newTriangle.get(i - 1);
                row.add(previousRow.get(j).add(previousRow.get(j - 1)));
            }
            row.add(BigInteger.ONE);
            newTriangle.add(row);
        }
        return newTriangle;
    }

    public void printTriangle() {
        for (ArrayList<BigInteger> row : this.rows) {
            System.out.println(row);
        }
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<ArrayList<BigInteger>> getRows() {
        return rows;
    }
}
