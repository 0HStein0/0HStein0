package GameProject;

public class Maze {
    private char[][] grid;
    private int playerX;
    private int playerY;
    private int goalX;
    private int goalY;

    public Maze(int rows, int cols) {
        grid = new char[rows][cols];
        playerX = 1;
        playerY = 1;
        goalX = rows - 2;
        goalY = 1;
        initializeMaze();
    }

    private void initializeMaze() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i == 0 || i == grid.length - 1 || j == 0 || j == grid[i].length - 1) {
                    grid[i][j] = '#';
                } else {
                    grid[i][j] = ' ';
                }
            }
        }
        grid[goalX][goalY] = 'G';
        grid[playerX][playerY] = 'P';
    }

    public char[][] getGrid() {
        return grid;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;

        if (grid[newX][newY] != '#') {
            grid[playerX][playerY] = ' ';
            playerX = newX;
            playerY = newY;
            if (playerX == goalX && playerY == goalY) {
                JOptionPane.showMessageDialog(null, "You reached the goal! You win!");
                System.exit(0);
            }
            grid[playerX][playerY] = 'P';
        }
    }
}
