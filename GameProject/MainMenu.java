package GameProject;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Maze Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton smallButton = new JButton("Start Game");
        smallButton.setPreferredSize(new Dimension(100, 40));

        setLayout(new FlowLayout());
        add(smallButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }

}