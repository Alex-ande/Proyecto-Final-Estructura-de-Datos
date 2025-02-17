package Controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import Models.Cell;
import Controllers.interfaces.MazeSolver;

public class MazeSolverRecursivo implements MazeSolver {

    @Override
    public List<Cell> getPath(boolean[][] grid, Cell start, Cell end) {
        List<Cell> path = new ArrayList<>();
        Set<Cell> visitadas = new HashSet<>();
        if (grid == null || grid.length == 0) {
            return path;
        }
        if (findPath(grid, start.row, start.col, end, path, visitadas)) {
            return path;
        }
        return new ArrayList<>();
    }

    private boolean findPath(boolean[][] grid, int row, int col, Cell end, List<Cell> path, Set<Cell> visitadas) {
        Cell cell = new Cell(row, col);
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length || !grid[row][col]) {
            return false;
        }

        if (visitadas.contains(cell)) {
            return false;
        }
        visitadas.add(cell);

        if (row == end.row && col == end.col) {
            path.add(cell);
            return true;
        }

        if (findPath(grid, row + 1, col, end, path, visitadas) || findPath(grid, row, col + 1, end, path, visitadas)) {
            path.add(cell);
            return true;
        }
        if (findPath(grid, row, col - 1, end, path, visitadas)) {
            path.add(cell);
            return true;
        }
        if (findPath(grid, row - 1, col, end, path, visitadas)) {
            path.add(cell);
            return true;
        }
        visitadas.remove(cell);
        path.remove(cell);
        return false;
    }
}
