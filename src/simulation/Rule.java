package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Haotian Wang
 * An abstract class that defines the rules for updating the states of cells in a grid. Determine what the next states
 */
public abstract class Rule {

    public final static Random rand = new Random();
    private Grid myGrid;

    /**
     * Construct a Rule object using grid as the only parameter.
     * @param grid: the Grid object containing the information of the grid of cells.
     */
    public Rule (Grid grid, List<Double> extraParameters) {
        this.myGrid = grid;
    }

    /**
     * Update all the Cells in the grid to their next states.
     */
    public void updateGrid() {
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                this.getGrid().item(i, j).updateShape(Cell.STATE_TO_PAINT_MAP);
                this.getGrid().item(i, j).updateToNextState();
                this.getGrid().item(i, j).setNextState(Cell.UNINITIALIZED);
            }
        }
    }

    /**
     * @author Julia Saveliff
     * Reset the next state of each cell to uninitialized value
     */
    public void clearNextStates() {
        for (int i = 0; i < myGrid.getNumRow(); i++) {
            for (int j = 0; j < myGrid.getNumCol(); j++) {
                Cell cell = myGrid.item(i, j);
                cell.setNextState(Cell.UNINITIALIZED);
            }
        }
    }

    /**
     * Calculate and set the next states of all the cells in the grid.
     */
    public abstract void determineNextStates();

    /**
     * A method used to find the neighbors of a cell in a grid.
     * @param row: the row number of the cell whose neighbors are to be found.
     * @param col: the column number of the cell whose neighbors are to be found.
     * @return A List of Cell that contains the neighbors of the cell passed as parameter.+     */
    protected List<Cell> getNeighbors(int row, int col) {
        Grid grid = this.getGrid();
        List<Cell> neighbors = new ArrayList<>();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (!(i == row && j == col || grid.isOutOfBounds(i, j))) {
                    neighbors.add(grid.item(i, j));
                }
            }
        }
        return neighbors;
    }

    /**
     * @return neighbors a list that consists of direct neighbours (up, down,
     * left, right) that are within the grid.
     */
    protected  List<Cell> getNeighborsFour(int row, int col) {
        List<Cell> neighbors = new ArrayList<>();
        List<int[]> cells  = new ArrayList<>();
        cells.add(new int[]{row+1,col});
        cells.add(new int[]{row-1,col});
        cells.add(new int[]{row,col+1});
        cells.add(new int[]{row,col-1});
        for (int[] cell : cells){
            if (!myGrid.isOutOfBounds(cell[0], cell[1])) {
                neighbors.add(myGrid.item(cell[0], cell[1]));
            }
        }
        return neighbors;
    }

    /**
     * Allow access for the subclasses of rule to the abstract Rule class' Grid object.
     * @return myGrid: a Grid object.
     */
    protected Grid getGrid () {
        return myGrid;
    }
}
