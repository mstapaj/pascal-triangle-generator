package pl.pascaltriangle.ui;

import pl.pascaltriangle.ui.views.MainPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Pascal Triangle Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1200, 1400);
        setLocationRelativeTo(null);

        JPanel currentPanel = new MainPanel();
        setContentPane(currentPanel);
    }
}
