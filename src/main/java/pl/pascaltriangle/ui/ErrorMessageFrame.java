package pl.pascaltriangle.ui;

import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class ErrorMessageFrame {
    public ErrorMessageFrame(String errorMessage) {
        showMessageDialog(new JFrame(), errorMessage);
    }
}
