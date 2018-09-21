package simulation;

import java.util.List;

/**
 * A specific Rule class for GameOfLife game.
 */
public class SpreadingOfFireRule extends Rule {

    public GameOfLifeRule(Grid grid) {
        super(grid);
    }

    /**
     * Calculate the next state of all the cells in the grid, according to this article, http://nifty.stanford.edu/2007/shiflet-fire/.
     */
    @Override
    public void determineNextStates() {
        List<Cell> FireCell = new ArrayList<Cell>();
        List<Cell> EmptyCellToFire = new ArrayList<Cell>();
    
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                Cell cell = this.getGrid().item(i, j);
                if (cell.getState() ==SpreadingOfFireCell.BURNING){
                    FireCell.add(cell);
                }
                if (cell.getState() == SpreadingOfFireCell.NORMAL){
                    boolean check = false;
                    List<Cell> neighbors = cell.getNeighbors(i, j);
                    for (Cell neighbor : neighbors) {
                        if (neighbor != null && neighbor.getState() == SpreadingOfFireCell.BURNING) { check = true; }
                }
                 if (check == true){
                 Random rand = new Random();
                 boolean val = rand.nextInt(1/probCatch)==0;
                 if (val == true)
                 EmptyCellToFire.add(cell);
                 }   
                }
            }
        }
        for (Cell cell : FireCell)
        cell.setNextState(SpreadingOfFireCellCell.EMPTY);
        for (Cell cell : EmptyCellToFire)
        cell.setNextState(SpreadingOfFireCellCell.BURNING);
        
    }
}
