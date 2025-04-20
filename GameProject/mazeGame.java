package GameProject;

import java.util.Scanner;

public class mazeGame {
    private char[][] maze;
    private int playerX;
    private int playerY;

    public mazeGame() {
        maze = new char[5][3];
        playerX = 1;
        playerY = 1;
        initializeMaze();
    }

    private void initializeMaze() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (i == 0 || i == maze.length - 1 || j == 0 || j == maze[i].length - 1) {
                    maze[i][j] = '#';
                } else {
                    maze[i][j] = ' ';
                }
            }
        }
        maze[playerX][playerY] = 'P';
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        printMaze();

        while (true) {
            System.out.println("Use WASD to move (Q to quit): ");
            String input = scanner.nextLine().toUpperCase();

            if (input.equals("Q")) {
                System.out.println("Game Over!");
                break;
            }

            movePlayer(input);
            printMaze();
        }

        scanner.close();
    }

    private void movePlayer(String direction) {
        maze[playerX][playerY] = ' ';

        switch (direction) {
            case "W":
                if (playerX > 1) playerX--;
                break;
            case "A":
                if (playerY > 1) playerY--;
                break;
            case "S":
                if (playerX < maze.length - 2) playerX++;
                break;
            case "D":
                if (playerY < maze[0].length - 2) playerY++;
                break;
            default:
                System.out.println("Invalid input! Use W, A, S, or D.");
        }

        maze[playerX][playerY] = 'P';
    }

    private void printMaze() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        mazeGame game = new mazeGame();
        game.startGame();
    }
}