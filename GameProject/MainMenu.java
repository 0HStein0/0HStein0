package GameProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainMenu extends JFrame {

    private JLabel backgroundLabel;
    private ImageIcon backgroundIcon;
    private int imageWidth = 600;
    private int imageHeight = 1200;

    public MainMenu() {
        setTitle("Maze Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        backgroundIcon = new ImageIcon(getClass().getResource("Images/StaticGIF.gif"));
        backgroundLabel = new JLabel(backgroundIcon);

        backgroundLabel.setBounds(0, 0, imageWidth, imageHeight);
        add(backgroundLabel);
        
        JButton startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.setBounds(700, 250, 150, 50);
        add(startButton);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
                backgroundLabel.setIcon(new ImageIcon(backgroundIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH)));
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }

}