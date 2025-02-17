package Controllers;

import java.util.List;
import java.util.ArrayList;
import Models.Cell;
import Controllers.interfaces.MazeSolver;

public class MazeSolverDFS implements MazeSolver {

    @Override
    public List<Cell> getPath(boolean[][] grid, Cell start, Cell end) {
        List<Cell> path = new ArrayList<>();
        if (grid == null || grid.length == 0) return path;

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        dfs(grid, start, end, visited, path);
        return path;
    }

    private boolean dfs(boolean[][] grid, Cell current, Cell end, boolean[][] visited, List<Cell> path) {
        if (current.row < 0 || current.col < 0 || current.row >= grid.length || current.col >= grid[0].length || !grid[current.row][current.col] || visited[current.row][current.col]) {
            return false;
        }

        visited[current.row][current.col] = true;
        path.add(current);

        if (current.row == end.row && current.col == end.col) {
            return true;
        }

        if (dfs(grid, new Cell(current.row + 1, current.col), end, visited, path) ||
            dfs(grid, new Cell(current.row - 1, current.col), end, visited, path) ||
            dfs(grid, new Cell(current.row, current.col + 1), end, visited, path) ||
            dfs(grid, new Cell(current.row, current.col - 1), end, visited, path)) {
            return true;
        }

        path.remove(path.size() - 1);
        return false;
    }
}








