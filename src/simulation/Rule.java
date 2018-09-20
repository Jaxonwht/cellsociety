package simulation;

import java.util.List;

/**
 * @author Haotian Wang
 * An abstract class that defines the rules for updating the states of cells in a grid.
 */
public abstract class Rule {
    private Grid myGrid;

    /**
     * Construct a Rule object using grid as the only parameter.
     * @param grid: the Grid object containing the information of the grid of cells.
     */
    public Rule (Grid grid) {
        myGrid = grid;
    }

    /**
     * Change the state of all Cells in Grid to the next state.
     */
    public abstract void updateGrid() {
        for
    }

    /**
     * Allow access for the subclasses of rule to the abstract Rule class' Grid object.
     * @return myGrid: a Grid object.
     */
    protected Grid getGrid () {
        return myGrid;
    }
}