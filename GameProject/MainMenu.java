package GameProject;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Maze Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        
        JButton startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.setBounds(700, 250, 150, 50);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 250));
        panel.setBackground(Color.BLACK);

        getContentPane().setBackground(Color.BLACK);
        add(startButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }

}