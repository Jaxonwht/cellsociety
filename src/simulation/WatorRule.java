package simulation;

import java.util.*;

/**
 * @author Yunhao Qing
 * A specific Rule class for Wator game, adapting from http://nifty.stanford.edu/2007/shiflet-fire/.
 */
public class WatorRule extends Rule {


    //This two params may be passed into this class and this part need to be changed in the future.

    public WatorRule(Grid grid) {
        super(grid);
    }

    @Override
    protected List<Cell> getNeighbors(int row, int col) {
        Grid grid = this.getGrid();
        List<Cell> neighbors = new ArrayList<Cell>();
        if (!grid.isOutOfBounds(row + 1, col)){
            neighbors.add(grid.item(row + 1, col));
        }
        if (!grid.isOutOfBounds(row - 1, col)){
            neighbors.add(grid.item(row - 1, col));
        }
        if (!grid.isOutOfBounds(row, col + 1)){
            neighbors.add(grid.item(row, col + 1));
        }
        if (!grid.isOutOfBounds(row, col - 1)){
            neighbors.add(grid.item(row, col - 1));
        }
        return neighbors;
    }

    //TODO
    /**
     *
     */
    @Override
    public void determineNextStates() {
    }
}
