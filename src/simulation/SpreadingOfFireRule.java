package simulation;

import java.util.*;

/**
 * A specific Rule class for SpreadingOfFire game.
 */
public class SpreadingOfFireRule extends Rule {
    
    public static final int myBurningCount = 3;
    public statif final double probGrowth = 0.1;
    
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

    /**
     * Calculate the next state of all the cells in the grid, according to this article, http://nifty.stanford.edu/2007/shiflet-fire/.
     */
    @Override
    public void determineNextStates() {
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                Cell cell = this.getGrid().item(i, j);
                if (cell.getState() == SpreadingOfFireCell.BURNING){
                    cell.setBurningTime(cell.getBurningTime()+1);
                    if (cell.getBurningTime() == myBurningCount){
                        cell.setBurningTime(0);
                        cell.setNextState(SpreadingOfFireCell.EMPTY);
                    }
				}
                if (cell.getState() == SpreadingOfFireCell.NORMAL){
                    boolean check = false;
                    List<Cell> neighbors = cell.getNeighbors(i, j);
                    for (Cell neighbor : neighbors) {
					if (neighbor != null && neighbor.getState() == SpreadingOfFireCell.BURNING){
						check = true;
						}
					}
					if (check == true){
						Random rand = new Random();
						boolean val = rand.nextInt(1/probCatch)==0;
						if (val == true)
							cell.setNextState(SpreadingOfFireCell.BURNING);
					}
                }
                if (cell.getState() == SpreadingOfFireCell.EMPTY){
						Random rand = new Random();
						boolean val = rand.nextInt(1/probGrowth)==0;
						if (val == true)
							cell.setNextState(SpreadingOfFireCell.NORMAL);
					}
                }
            }
        }
}
