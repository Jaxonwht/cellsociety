package simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haotian Wang
 * An abstract class that defines the rules for updating the states of cells in a grid. Determine what the next states
 */
public abstract class Rule {
    private Grid myGrid;

    /**
     * Construct a Rule object using grid as the only parameter.
     * @param grid: the Grid object containing the information of the grid of cells.
     */
    public Rule (Grid grid) {
        this.myGrid = grid;
    }

    public void updateGrid() {
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                this.getGrid().item(i, j).updateToNextState();
            }
        }
    }

    /**
     * Change the state of all Cells in Grid to the next state.
     */
    public abstract void determineNextStates();

    protected List<Cell> getNeighbors(Cell cell) {
        Grid grid = this.getGrid();
        int row = cell.getRow();
        int col = cell.getCol();
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
     * Allow access for the subclasses of rule to the abstract Rule class' Grid object.
     * @return myGrid: a Grid object.
     */
    protected Grid getGrid () {
        return myGrid;
    }
}
