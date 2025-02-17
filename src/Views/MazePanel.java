package Views;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import Models.Cell;
import Models.Maze;

public class MazePanel extends JPanel {
    private Maze maze;
    private List<Cell> path;
    private int cellSize = 50;
    private Cell startCell;
    private Cell endCell;

    public MazePanel(Maze maze) {
        this.maze = maze;
        this.path = null;
        this.startCell = new Cell(0, 0);
        this.endCell = new Cell(maze.getSize()-1, maze.getSize()-1);
        setPreferredSize(new Dimension(maze.getSize() * cellSize, maze.getSize() * cellSize));
    }

    // Método para establecer el camino encontrado
    public void setPath(List<Cell> path) {
        this.path = path;
        repaint(); // Redibuja el panel cuando se actualiza el camino
    }

    // Método para reiniciar el panel
    public void reset() {
        this.path = null; // Borrar el camino
        repaint(); // Redibuja el panel
    }

    public void setStartCell(Cell startCell) {
        this.startCell = startCell;
        repaint();
    }

    public void setEndCell(Cell endCell) {
        this.endCell = endCell;
        repaint();
    }

    public int getCellSize() {
        return cellSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        boolean[][] grid = maze.getGrid();
        int size = maze.getSize();

        // Dibujar el laberinto
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col]) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                g.setColor(Color.GRAY);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }

        // Dibujar el camino
        if (path != null) {
            g.setColor(Color.GREEN);
            for (Cell cell : path) {
                g.fillRect(cell.col * cellSize, cell.row * cellSize, cellSize, cellSize);
            }
        }

        // Dibujar inicio y fin
        g.setColor(Color.RED);
        g.fillRect(startCell.col * cellSize, startCell.row * cellSize, cellSize, cellSize);
        g.setColor(Color.BLUE);
        g.fillRect(endCell.col * cellSize, endCell.row * cellSize, cellSize, cellSize);
    }
}

