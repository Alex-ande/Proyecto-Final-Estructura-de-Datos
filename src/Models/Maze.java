package Models;

import java.util.Arrays;

public class Maze {
    private boolean[][] grid;
    private int size;

    public Maze(int size) {
        this.size = size;
        grid = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            Arrays.fill(grid[i], true);
        }
    }

    public Maze(boolean[][] predefinedGrid) {
        this.size = predefinedGrid.length;
        this.grid = predefinedGrid;
    }

    public boolean[][] getGrid() {
        return grid;
    }

    // Método para obtener el tamaño del laberinto
    public int getSize() {
        return size;
    }

    public void printMaze() {
        System.out.println("\nEstado actual del laberinto:");
        for (boolean[] row : grid) {
            for (boolean cell : row) {
                System.out.print(cell ? " - " : " * ");
            }
            System.out.println();
        }
    }
}









