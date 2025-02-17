package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import Models.Cell;
import Models.Maze;
import Controllers.interfaces.MazeSolver;
import Controllers.MazeSolverBFS;
import Controllers.MazeSolverDFS;
import Controllers.MazeSolverDP;
import Controllers.MazeSolverRecursivo;

public class MazeFrame extends JFrame {
    private Maze maze;
    private MazePanel mazePanel;
    private JComboBox<String> algorithmComboBox;
    private JSpinner sizeSpinner;
    private Cell startCell = new Cell(0, 0);
    private Cell endCell;
    private boolean isSettingStart = false;
    private boolean isSettingEnd = false;
    private boolean isDrawingWalls = false;

    public MazeFrame() {
        setTitle("Resolvedor de Laberintos");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel configPanel = new JPanel();
        SpinnerNumberModel sizeModel = new SpinnerNumberModel(4, 2, 20, 1);
        sizeSpinner = new JSpinner(sizeModel);
        JButton createButton = new JButton("Crear Nuevo Laberinto");
        createButton.addActionListener(e -> createNewMaze());

        configPanel.add(new JLabel("Tamaño:"));
        configPanel.add(sizeSpinner);
        configPanel.add(createButton);

        JPanel toolPanel = new JPanel();
        JToggleButton setStartButton = new JToggleButton("Establecer Inicio");
        JToggleButton setEndButton = new JToggleButton("Establecer Final");
        JToggleButton drawWallsButton = new JToggleButton("Dibujar Paredes");

        ButtonGroup toolGroup = new ButtonGroup();
        toolGroup.add(setStartButton);
        toolGroup.add(setEndButton);
        toolGroup.add(drawWallsButton);

        setStartButton.addActionListener(e -> {
            isSettingStart = setStartButton.isSelected();
            isSettingEnd = false;
            isDrawingWalls = false;
        });

        setEndButton.addActionListener(e -> {
            isSettingEnd = setEndButton.isSelected();
            isSettingStart = false;
            isDrawingWalls = false;
        });

        drawWallsButton.addActionListener(e -> {
            isDrawingWalls = drawWallsButton.isSelected();
            isSettingStart = false;
            isSettingEnd = false;
        });

        toolPanel.add(setStartButton);
        toolPanel.add(setEndButton);
        toolPanel.add(drawWallsButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(configPanel, BorderLayout.NORTH);
        topPanel.add(toolPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        createNewMaze();

        JPanel controlPanel = new JPanel();
        algorithmComboBox = new JComboBox<>(new String[]{"Recursivo", "Programación Dinámica", "BFS", "DFS"});
        JButton solveButton = new JButton("Resolver");

        controlPanel.add(new JLabel("Algoritmo:"));
        controlPanel.add(algorithmComboBox);
        controlPanel.add(solveButton);

        add(controlPanel, BorderLayout.SOUTH);
        solveButton.addActionListener(e -> resolverLaberinto());
        algorithmComboBox.addActionListener(e -> mazePanel.reset());
    }

    private void createNewMaze() {
        int size = (Integer) sizeSpinner.getValue();
        maze = new Maze(size);
        endCell = new Cell(size-1, size-1);

        if (mazePanel != null) {
            remove(mazePanel);
        }

        mazePanel = new MazePanel(maze);
        mazePanel.setStartCell(startCell);
        mazePanel.setEndCell(endCell);

        mazePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cellSize = mazePanel.getCellSize();
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;

                if (row >= 0 && row < maze.getSize() && col >= 0 && col < maze.getSize()) {
                    if (isSettingStart) {
                        startCell = new Cell(row, col);
                        mazePanel.setStartCell(startCell);
                    } else if (isSettingEnd) {
                        endCell = new Cell(row, col);
                        mazePanel.setEndCell(endCell);
                    } else if (isDrawingWalls) {
                        boolean[][] grid = maze.getGrid();
                        grid[row][col] = !grid[row][col];
                        mazePanel.repaint();
                    }
                }
            }
        });

        add(mazePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void resolverLaberinto() {
        // Validar que inicio y fin sean válidos
        if (!maze.getGrid()[startCell.row][startCell.col]) {
            JOptionPane.showMessageDialog(this, 
                "El punto de inicio está en una pared.\nPor favor, seleccione una celda válida.", 
                "Error de Configuración", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!maze.getGrid()[endCell.row][endCell.col]) {
            JOptionPane.showMessageDialog(this, 
                "El punto final está en una pared.\nPor favor, seleccione una celda válida.", 
                "Error de Configuración", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int selectedAlgorithm = algorithmComboBox.getSelectedIndex();
        List<MazeSolver> solvers = new ArrayList<>();
        solvers.add(new MazeSolverRecursivo());
        solvers.add(new MazeSolverDP());
        solvers.add(new MazeSolverBFS());
        solvers.add(new MazeSolverDFS());

        MazeSolver solver = solvers.get(selectedAlgorithm);
        
        // Medir el tiempo de ejecución
        long startTime = System.currentTimeMillis();
        List<Cell> path = solver.getPath(maze.getGrid(), startCell, endCell);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        if (path == null || path.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No se encontró un camino posible entre el inicio y el final.\n" +
                "Asegúrese de que existe un camino válido.", 
                "Camino no encontrado", 
                JOptionPane.ERROR_MESSAGE);
        } else {
            mazePanel.setPath(path);
            JOptionPane.showMessageDialog(this, 
                String.format("¡Camino encontrado!\n" +
                             "Longitud del camino: %d celdas\n" +
                             "Tiempo de ejecución: %d ms", 
                             path.size(), duration),
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MazeFrame().setVisible(true);
        });
    }
}


