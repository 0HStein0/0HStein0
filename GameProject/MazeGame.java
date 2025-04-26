package GameProject;


import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class MazeGame extends JFrame implements KeyListener {
    private final int ROWS = 21;
    private final int COLS = 21;
    private final int CELL_SIZE = 30;
    private int[][] maze;
    private int playerRow = 1, playerCol = 1;
    private int roundCount = 0;
    private int timeLeft = 5;
    private Timer gameTimer;

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MazePanel mazePanel;
    private JPanel menuPanel;

    public MazeGame() {
        setTitle("Maze Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mazePanel = new MazePanel();
        menuPanel = createMenuPanel();

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(mazePanel, "Game");

        add(mainPanel);
        addKeyListener(this);
        setFocusable(true);
        setVisible(true);

        showMenu();
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);

        JLabel title = new JLabel("Maze Game");
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(_ -> startGame());

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 24));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(_ -> System.exit(0));

        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(startButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(exitButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private void showMenu() {
        cardLayout.show(mainPanel, "Menu");
    }

    private void startGame() {
        generateMaze();
        startTimer();
        cardLayout.show(mainPanel, "Game");
    }

    private void generateMaze() {
        maze = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) Arrays.fill(maze[i], 1);
        dfs(1, 1);
        roundCount++;
    }

    private void dfs(int row, int col) {
        maze[row][col] = 0;
        List<int[]> directions = Arrays.asList(
            new int[] {0, 2}, new int[] {2, 0}, new int[] {0, -2}, new int[] {-2, 0}
        );
        Collections.shuffle(directions);
        for (int[] dir : directions) {
            int newRow = row + dir[0], newCol = col + dir[1];
            if (newRow > 0 && newRow < ROWS - 1 && newCol > 0 && newCol < COLS - 1 && maze[newRow][newCol] == 1) {
                maze[row + dir[0] / 2][col + dir[1] / 2] = 0;
                dfs(newRow, newCol);
            }
        }
    }

    private void startTimer() {
        gameTimer = new Timer(1000, _ -> {
            timeLeft--;
            repaint();
            if (timeLeft <= 0) {
                gameTimer.stop();
                JOptionPane.showMessageDialog(this, "Game Over.");
                System.exit(0);
            }
        });
        gameTimer.start();
    }

    private class MazePanel extends JPanel {
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
            g.fillRect(playerCol * CELL_SIZE + xOffset, playerRow * CELL_SIZE + yOffset, CELL_SIZE, CELL_SIZE);

            g.setColor(Color.WHITE);
            g.fillRect((COLS - 2) * CELL_SIZE + xOffset, (ROWS - 2) * CELL_SIZE + yOffset, CELL_SIZE, CELL_SIZE);

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), 30);
            g.setColor(Color.BLACK);
            g.drawString("Time Remaining: " + timeLeft + " seconds", 10, 20);
            g.drawString("Use Arrow Keys to Move. Reach the White Square to win!", 200, 20);
            g.drawString("Room #" + roundCount, 500, 20);
        }
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
            gameTimer.stop();
            JOptionPane.showMessageDialog(this, "You win!");
            generateMaze();
            playerRow = 1;
            playerCol = 1;
            timeLeft = 60;
            repaint();
        } else repaint();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MazeGame::new);
    }
}