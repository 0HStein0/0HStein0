package GameProject;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Maze Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 500);
            frame.setLocationRelativeTo(null);

            Maze maze = new Maze(5, 3);
            MazePanel mazePanel = new MazePanel(maze);
            frame.add(mazePanel);

            frame.addKeyListener(mazePanel.getKeyHandler());
            frame.setFocusable(true);
            frame.setVisible(true);
        });
    }
}