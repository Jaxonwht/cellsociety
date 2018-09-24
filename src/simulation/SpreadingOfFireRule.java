package simulation;

import java.util.*;

/**
 * @author Yunhao Qing
 * A specific Rule class for SpreadingOfFire game,
 * adapting rules from http://nifty.stanford.edu/2007/shiflet-fire/.
 */
public class SpreadingOfFireRule extends Rule {
    private final int MY_BURNING_COUNT; //how long the tree has been buring
    private final double PROB_GROWTH;
    private final double PROB_CATCH;
    
    /**
     * Constructor for SpreadingOfFireRule.
     * @param grid the grid with states number in each cell
     * @param extraParameters MY_BURNING_COUNT, PROB_GROWTH and PROB_CATCH
     */
    public SpreadingOfFireRule(Grid grid, List<Double> extraParameters) {
        super(grid, extraParameters);
        PROB_CATCH = extraParameters.get(0);
        PROB_GROWTH = extraParameters.get(1);
        MY_BURNING_COUNT = (int) Math.floor(extraParameters.get(2));
    }
    
    /**
     * @return neighbors a list that consists of direct neighbours (up, down,
     * left, right) that are within the grid.
     */
    @Override
    protected List<Cell> getNeighbors(int row, int col) {
        Grid grid = this.getGrid();
        List<Cell> neighbors = new ArrayList<Cell>();
        List<int[]> cells  = new ArrayList<>();
        cells.add(new int[]{row+1,col});
        cells.add(new int[]{row-1,col});
        cells.add(new int[]{row,col+1});
        cells.add(new int[]{row,col-1});
        for (int[] cell : cells){
            if (!grid.isOutOfBounds(cell[0], cell[1])) {
                neighbors.add(grid.item(cell[0], cell[1]));
            }
        }
        return neighbors;
    }
    
    /**
     * Transver the grid and determine the next state for each cell.
     */
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
                    } else {
                        cell.setNextState(cell.getState());
                    }
                }
                
                else if (cell.getState() == SpreadingOfFireCell.NORMAL) {
                    boolean check = false;
                    List<Cell> neighbors = this.getNeighbors(i, j);
                    for (Cell neighbor : neighbors) {
                        if (neighbor != null && neighbor.getState() == SpreadingOfFireCell.BURNING) {
                            check = true;
                        }
                    }
                    if (check && rand.nextDouble() < PROB_CATCH) {
                        cell.setNextState(SpreadingOfFireCell.BURNING);
                    } else {
                        cell.setNextState(cell.getState());
                    }
                }
                
                else if (cell.getState() == SpreadingOfFireCell.EMPTY) {
                    if (rand.nextDouble() < PROB_GROWTH){
                        cell.setNextState(SpreadingOfFireCell.NORMAL);
                    } else {
                        cell.setNextState(cell.getState());
                    }
                }
            }
        }
    }
}
