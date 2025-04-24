package GameProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private Enemy enemy;
    private int roundCount = 0;
    private boolean enemyEnabled = false;

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

        roundCount++;
        enemyEnabled = (roundCount == 1) || (roundCount > 5 && new Random().nextBoolean());
        if (enemyEnabled) {
            spawnEnemy();
        }
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

    private void spawnEnemy() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(ROWS - 2) + 1;
            col = random.nextInt(COLS - 2) + 1;
        } while (maze[row][col] != 0 || (row == ROWS - 2 && col == COLS - 2));

        enemy = new Enemy(row, col, maze);
        enemy.setVisible(false);
    
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                enemy.activate(playerRow, playerCol);
                enemy.setVisible(true);
            }
        }, 5000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        int cellWidth = getWidth() / COLS;
        int cellHeight = getHeight() / ROWS;
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

        if (enemyEnabled) {
            g.setColor(Color.RED);
            g.fillRect(enemy.getCol() * cellWidth, enemy.getRow() * cellHeight, cellWidth, cellHeight);
        }

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), 30);
        g.setColor(Color.BLACK);
        g.drawString("Use Arrow Keys to Move. Reach the White Square to win!", 10, 20);
        g.drawString("Room #" + roundCount, 390, 20);
    }

    private List<int[]> playerPath = new ArrayList<>();

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
            playerPath.add(new int[]{newRow, newCol});
        }

        if (enemyEnabled && enemy.isVisible() && playerRow == enemy.getRow() && playerCol == enemy.getCol()) {
            JOptionPane.showMessageDialog(this, "Game Over.");
            generateMaze();
            playerRow = 1;
            playerCol = 1;
            playerPath.clear();
            enemy = null;
            enemyEnabled = false;
            System.exit(0);
        }
        
        if (playerRow == ROWS - 2 && playerCol == COLS - 2) {
            JOptionPane.showMessageDialog(this, "You win!");
            if (enemy != null) {
                enemy.setVisible(false);
                enemy.deactivate();
            }
            generateMaze();
            playerRow = 1;
            playerCol = 1;
            playerPath.clear();
            repaint();
            return;
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