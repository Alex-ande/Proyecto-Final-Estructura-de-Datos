package Controllers;

import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import Models.Cell;
import Controllers.interfaces.MazeSolver;

public class MazeSolverBFS implements MazeSolver {

    @Override
    public List<Cell> getPath(boolean[][] grid, Cell start, Cell end) {
        List<Cell> path = new ArrayList<>();
        if (grid == null || grid.length == 0) return path;

        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Cell[][] parent = new Cell[rows][cols];

        Queue<Cell> queue = new LinkedList<>();
        queue.add(start);
        visited[start.row][start.col] = true;

        int[] rowDirections = {-1, 1, 0, 0};
        int[] colDirections = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            Cell current = queue.poll();

            if (current.row == end.row && current.col == end.col) {
                // Reconstruir el camino desde el final hasta el inicio
                Cell cell = current;
                while (cell != null) {
                    path.add(0, cell);
                    cell = parent[cell.row][cell.col];
                }
                return path;
            }

            for (int i = 0; i < 4; i++) {
                int newRow = current.row + rowDirections[i];
                int newCol = current.col + colDirections[i];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] && !visited[newRow][newCol]) {
                    queue.add(new Cell(newRow, newCol));
                    visited[newRow][newCol] = true;
                    parent[newRow][newCol] = current; // Guardar el padre para reconstruir el camino
                }
            }
        }

        return path; // Si no se encuentra un camino, devuelve una lista vacía
    }
}





