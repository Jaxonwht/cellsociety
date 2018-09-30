package simulation;

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
     * Allow access for the subclasses of rule to the abstract Rule class' Grid object.
     * @return myGrid: a Grid object.
     */
    protected Grid getGrid () {
        return myGrid;
    }
}
