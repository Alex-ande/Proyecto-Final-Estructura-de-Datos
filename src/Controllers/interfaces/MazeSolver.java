package Controllers.interfaces;

import java.util.List;
import Models.Cell;

public interface MazeSolver {
    public List<Cell> getPath(boolean[][] grid, Cell start, Cell end);
}


