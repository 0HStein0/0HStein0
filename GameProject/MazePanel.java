package GameProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MazePanel extends JPanel {
    private Maze maze;
    private final int squareSize = 80;
    private final int offsetX = 50;
    private final int offsetY = 80;

    public MazePanel(Maze maze) {
        this.maze = maze;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        char[][] grid = maze.getGrid();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '#') {
                    g.setColor(Color.BLACK);
                } else if (grid[i][j] == 'P') {
                    g.setColor(Color.BLUE);
                } else if (grid[i][j] == 'G') {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(offsetX + j * squareSize, offsetY + i * squareSize, squareSize, squareSize);

                g.setColor(Color.GRAY);
                g.drawRect(offsetX + j * squareSize, offsetY + i * squareSize, squareSize, squareSize);
            }
        }
    }

    public KeyAdapter getKeyHandler() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        maze.movePlayer(-1, 0);
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        maze.movePlayer(0, -1);
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        maze.movePlayer(1, 0);
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        maze.movePlayer(0, 1);
                        break;
                }
                repaint();
            }
        };
    }
}
