package GameProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MazeGame extends JPanel implements KeyListener {
    private final int ROWS = 21;
    private final int COLS = 21;
    private final int CELL_SIZE = 30;
    private int[][] maze;
    private int playerX, playerY;


    public MazeGame() {
        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE));
        setFocusable(true);
        addKeyListener(this);
        generateMaze();
        playerX = 1 * CELL_SIZE + CELL_SIZE / 2;
        playerY = 1 * CELL_SIZE + CELL_SIZE / 2;
    }

    private void generateMaze() {
        maze = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            Arrays.fill(maze[i], 1);
        }
        dfs(1, 1);
    }

    private void dfs(int row, int col) {
        maze[row][col] = 0;
        int[][] directions = {{0, 2}, {2, 0}, {0, -2}, {-2, 0}};
        Collections.shuffle(Arrays.asList(directions));
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow > 0 && newRow < ROWS - 1 && newCol > 0 && newCol < COLS - 1 && maze[newRow][newCol] == 1) {
                maze[row + dir[0] / 2][col + dir[1] / 2] = 0;
                dfs(newRow, newCol);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        int xOffset = (getWidth() - COLS * CELL_SIZE) / 2;
        int yOffset = (getHeight() - ROWS * CELL_SIZE) / 2;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                g.setColor(maze[r][c] == 1 ? Color.DARK_GRAY : Color.BLACK);
                g.fillRect(c * CELL_SIZE + xOffset, r * CELL_SIZE + yOffset, CELL_SIZE, CELL_SIZE);
            }
        }

        g.setColor(Color.BLUE);
        int playerSize = CELL_SIZE / 3;
        g.fillRect(playerX - playerSize/2, playerY - playerSize/2, playerSize, playerSize);

        
        g.setColor(Color.RED);
        g.fillRect((COLS - 2) * CELL_SIZE + xOffset, (ROWS - 2) * CELL_SIZE + yOffset, CELL_SIZE, CELL_SIZE);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), 30);
        g.setColor(Color.BLACK);
        g.drawString("Use Arrow Keys to Move. Reach the Red Square to win!", 10, 20);
    }

    @Override
        public void keyPressed(KeyEvent e) {
            int moveAmount = CELL_SIZE / 3;
            int newX = playerX;
            int newY = playerY;
            if (e.getKeyCode() == KeyEvent.VK_UP) newY -= moveAmount;
            if (e.getKeyCode() == KeyEvent.VK_DOWN) newY += moveAmount;
            if (e.getKeyCode() == KeyEvent.VK_LEFT) newX -= moveAmount;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) newX += moveAmount;

            int playerSize = CELL_SIZE / 3;
            int xOffset = (getWidth() - COLS * CELL_SIZE) / 2;
            int yOffset = (getHeight() - ROWS * CELL_SIZE) / 2;

            int leftCol = (newX - playerSize/2 - xOffset) / CELL_SIZE;
            int rightCol = (newX + playerSize/2 - xOffset) / CELL_SIZE;
            int topRow = (newY - playerSize/2 - yOffset) / CELL_SIZE;
            int bottomRow = (newY + playerSize/2 - yOffset) / CELL_SIZE;

            if (newX >= xOffset && newX + playerSize <= COLS * CELL_SIZE + xOffset && newY >= yOffset && newY + playerSize <= ROWS * CELL_SIZE + yOffset) {
                if (isWalkable(topRow, leftCol) && isWalkable(topRow, rightCol) &&
                    isWalkable(bottomRow, leftCol) && isWalkable(bottomRow, rightCol)) {
                    playerX = newX;
                    playerY = newY;
                }
            }

            if (bottomRow == ROWS - 2 && rightCol == COLS - 2) {
                JOptionPane.showMessageDialog(this, "You win!");
                generateMaze();
                playerX = 1 * CELL_SIZE + CELL_SIZE / 2;
                playerY = 1 * CELL_SIZE + CELL_SIZE / 2;
            }

            repaint();
        }

        private boolean isWalkable(int row, int col) {
            return row >= 0 && row < ROWS && col >= 0 && col < COLS && maze[row][col] == 0;
        }
        

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Maze Game");
        MazeGame game = new MazeGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
