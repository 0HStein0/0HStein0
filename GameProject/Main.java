package GameProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Main extends JFrame {

    public Main() {
        setTitle("Maze Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.addActionListener(e -> MazeGame.startGame(this));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 250));
        panel.add(startButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}

class MazeGame extends JPanel implements KeyListener { // MazeGame is now non-public
    private final int ROWS = 21;
    private final int COLS = 21;
    private final int CELL_SIZE = 30;
    private int[][] maze;
    private int playerRow = 1, playerCol = 1;

    public MazeGame() {
        
        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE));
        setFocusable(true);
        addKeyListener(this);
        generateMaze();
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
            for (int c = 0; COLS > c; c++) {
                g.setColor(maze[r][c] == 1 ? Color.DARK_GRAY : Color.BLACK);
                g.fillRect(c * CELL_SIZE + xOffset, r * CELL_SIZE + yOffset, CELL_SIZE, CELL_SIZE);
            }
        }

        g.setColor(Color.BLUE);
        g.fillRect(playerCol * CELL_SIZE + xOffset, playerRow * CELL_SIZE + yOffset, CELL_SIZE, CELL_SIZE);

        g.setColor(Color.WHITE);
        g.fillRect((COLS - 2) * CELL_SIZE + xOffset, (ROWS - 2) * CELL_SIZE + yOffset, CELL_SIZE, CELL_SIZE);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), 30);
        g.setColor(Color.BLACK);
        g.drawString("Use Arrow Keys to Move. Reach the White Square to win!", 10, 20);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int newRow = playerRow, newCol = playerCol;
        if (e.getKeyCode() == KeyEvent.VK_UP) newRow--;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) newRow++;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) newCol--;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) newCol++;
        if (maze[newRow][newCol] == 0) {
            playerRow = newRow;
            playerCol = newCol;
        }
        if (playerRow == ROWS - 2 && playerCol == COLS - 2) {
            JOptionPane.showMessageDialog(this, "You win!");
            generateMaze();
            playerRow = 1;
            playerCol = 1;
            repaint();
        }
        repaint();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void startGame(JFrame frame) {
        frame.dispose();
        frame.getContentPane().removeAll();
        MazeGame game = new MazeGame();
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.requestFocusInWindow();
    }
}