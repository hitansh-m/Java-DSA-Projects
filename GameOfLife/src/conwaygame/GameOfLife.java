package conwaygame;

import java.util.ArrayList;

/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many
 * iterations/generations.
 *
 * Rules
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.
 * 
 * @author Seth Kelley
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean DEAD = false;

    private boolean[][] grid; // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
     * Default Constructor which creates a small 5x5 grid with five alive cells.
     * This variation does not exceed bounds and dies off after four iterations.
     */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
     * Constructor used that will take in values to create a grid with a given
     * number
     * of alive cells
     * 
     * @param file is the input file with the initial game pattern formatted as
     *             follows:
     *             An integer representing the number of grid rows, say r
     *             An integer representing the number of grid columns, say c
     *             Number of r lines, each containing c true or false values (true
     *             denotes an ALIVE cell)
     */
    public GameOfLife(String file) {
        StdIn.setFile(file);
        int r = StdIn.readInt();
        int c = StdIn.readInt();
        grid = new boolean[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                grid[i][j] = StdIn.readBoolean();
            }
            StdIn.readLine();
        }
    }

    /**
     * Returns grid
     * 
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid() {
        return grid;

    }

    /**
     * Returns totalAliveCells
     * 
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells() {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * 
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState(int row, int col) {
        return grid[row][col]; // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * 
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive() {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == true) {
                    return true;
                }
            }
        }
        return false; // update this line, provided so that code compiles
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors(int row, int col) {
        int count = 0;
        int[][] offsets = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, -1 }, { 1, 1 }, { 1, -1 }, { -1, 1 } };
        for (int i = 0; i < offsets.length; i++) {
            int neighborRow = (row + offsets[i][0] + grid.length) % grid.length;
            int neighborCol = (col + offsets[i][1] + grid[0].length) % grid[0].length;
            if (grid[neighborRow][neighborCol] == true) {
                count++;
            }
        }
        return count; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid() {
        boolean[][] board = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == true) {
                    if (numOfAliveNeighbors(i, j) <= 1) {
                        board[i][j] = false;
                    } else if (numOfAliveNeighbors(i, j) == 2 || numOfAliveNeighbors(i, j) == 3) {
                        board[i][j] = true;
                    } else if (numOfAliveNeighbors(i, j) >= 4) {
                        board[i][j] = false;
                    }
                } else if (grid[i][j] == false) {
                    if (grid[i][j] == false && numOfAliveNeighbors(i, j) == 3) {
                        board[i][j] = true;
                    }
                }
            }
        }
        return board;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration() {
        int count = 0;
        boolean[][] a = computeNewGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = a[i][j];
                if (a[i][j]) {
                    count++;
                }
            }
        }
        totalAliveCells = count;
        // WRITE YOUR CODE HERE
    }

    /**
     * Updates the current grid with the grid computed after multiple (n)
     * generations.
     * 
     * @param n number of iterations that the grid will go through to compute a new
     *          grid
     */
    public void nextGeneration(int n) {
        for (int i = 0; i < n; i++) {
            nextGeneration();
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Determines the number of separate cell communities in the grid
     * 
     * @return the number of communities in the grid, communities can be formed from
     *         edges
     */
    public int numOfCommunities() {
        int count = 0;
        ArrayList<Integer> parentNodes = new ArrayList<>();
        WeightedQuickUnionUF s = new WeightedQuickUnionUF(grid.length, grid[0].length);
        int[][] offsets = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, -1 }, { 1, 1 }, { 1, -1 }, { -1, 1 } };
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                for (int p = 0; p < offsets.length; p++) {
                    int neighborRow = (i + offsets[p][0] + grid.length) % grid.length;
                    int neighborCol = (j + offsets[p][1] + grid[0].length) % grid[0].length;
                    if ((grid[i][j] == true) && grid[neighborRow][neighborCol]) {
                        s.union(i, j, neighborRow,neighborCol);

                    }
                }
            }
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == true) {
                    int root = s.find(i, j);
                    if (!parentNodes.contains(root)) {
                        parentNodes.add(root);
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
