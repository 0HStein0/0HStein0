package GameProject;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Enemy {
    private int row, col;
    private int targetRow, targetCol;
    private int[][] maze;
    private boolean active = false;

    public Enemy(int startRow, int startCol, int[][] maze) {
        this.row = startRow;
        this.col = startCol;
        this.maze = maze;
    }

    public void activate(int targetRow, int targetCol) {
        this.targetRow = targetRow;
        this.targetCol = targetCol;
        this.active = true;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startChasing();
            }
        }, 5000);
    }

    private void startChasing() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (active) {
                    moveTowardPlayer();
                }
            }
        }, 0, 500);
    }

    private void moveTowardPlayer() {
        if (row < targetRow && maze[row + 1][col] == 0) {
            row++;
        } else if (row > targetRow && maze[row - 1][col] == 0) {
            row--;
        } else if (col < targetCol && maze[row][col + 1] == 0) {
            col++;
        } else if (col > targetCol && maze[row][col - 1] == 0) {
            col--;
        }

        if (row == targetRow && col == targetCol) {
            JOptionPane.showMessageDialog(null, "The enemy caught you! Game Over.");
            System.exit(0);
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void deactivate() {
        this.active = false;
    }
}