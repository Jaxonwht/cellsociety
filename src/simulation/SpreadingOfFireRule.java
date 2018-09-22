package simulation;

import java.util.*;

/**
 * A specific Rule class for SpreadingOfFire game, adapting rules from http://nifty.stanford.edu/2007/shiflet-fire/.
 */
public class SpreadingOfFireRule extends Rule {
    
    private static final int MY_BURNING_COUNT = 3;
    private static final double PROB_GROWTH = 0.1;
    private static final double PROB_CATCH = 0.2;
    //This two params may be passed into this class and this part need to be changed in the future.
    
    public SpreadingOfFireRule(Grid grid) {
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

    @Override
    public void determineNextStates() {
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                SpreadingOfFireCell cell = (SpreadingOfFireCell) this.getGrid().item(i, j);
                if (cell.getState() == SpreadingOfFireCell.BURNING) {
                    cell.setBurningTime(cell.getBurningTime() + 1);
                    if (cell.getBurningTime() == MY_BURNING_COUNT) {
                        cell.setBurningTime(0);
                        cell.setNextState(SpreadingOfFireCell.EMPTY);
                    }
                }
                if (cell.getState() == SpreadingOfFireCell.NORMAL) {
                    boolean check = false;
                    List<Cell> neighbors = this.getNeighbors(i, j);
                    for (Cell neighbor : neighbors) {
                        if (neighbor != null && neighbor.getState() == SpreadingOfFireCell.BURNING) {
                            check = true;
                        }
                    }
                    if (check && Math.random() < PROB_CATCH) {
                        cell.setNextState(SpreadingOfFireCell.BURNING);
                    }
                }
                if (cell.getState() == SpreadingOfFireCell.EMPTY) {
                    if (Math.random() < PROB_GROWTH)
                        cell.setNextState(SpreadingOfFireCell.NORMAL);

                }
            }
        }
    }
}
