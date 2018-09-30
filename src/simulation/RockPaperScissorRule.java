package simulation;

import java.util.List;
import java.util.Random;

/**
 * @author Julia Saveliff
 */
public class RockPaperScissorRule extends Rule {
    private Random rand = new Random();

    public RockPaperScissorRule(Grid grid, List<Double> extraParameters) {
        super(grid, extraParameters);
    }

    /**
     * Update all the Cells in the grid to their next states.
     */
    @Override
    public void updateGrid() {
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                ((RockPaperScissorCell)this.getGrid().item(i, j)).updateToNextGradient();
                //this.getGrid().item(i, j).updateShape(Cell.STATE_TO_PAINT_MAP);
                this.getGrid().item(i, j).updateToNextState();
                this.getGrid().item(i, j).setNextState(Cell.UNINITIALIZED);
            }
        }
    }

    @Override
    public void determineNextStates() {
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                Cell cell = this.getGrid().item(i, j);
                List<Cell> neighbors = this.getGrid().getDirectNeighbors(i, j);
                Cell neighbor = neighbors.get(rand.nextInt(neighbors.size()));
                if (cell.getState() == RockPaperScissorCell.EMPTY) {
                    if (neighbor.getState() != RockPaperScissorCell.EMPTY) {
                        // cell is empty and selected neighbor is color
                        cell.setNextState(neighbor.getState());
                        // set cell next gradient = neighbor gradient + 1
                        ((RockPaperScissorCell) cell).setNextGradient(((RockPaperScissorCell) neighbor).getGradient()+1);
                    } else {
                        // cell is empty and selected neighbor is empty
                        cell.setNextState(cell.getState());
                    }
                } else {
                    if (((RockPaperScissorCell) cell).beats(neighbor)) {
                        // feed and upgrade toward zero
                        cell.setNextState(cell.getState());
                        // set cell next gradient = cell gradient - 1
                        ((RockPaperScissorCell) cell).setNextGradient(((RockPaperScissorCell) cell).getGradient()-1);
                    } else if (!((RockPaperScissorCell) cell).beats(neighbor)) {
                        // killed and downgrade away from zero
                        cell.setNextState(cell.getState());
                        // set cell next gradient = cell gradient + 1
                        ((RockPaperScissorCell) cell).setNextGradient(((RockPaperScissorCell) cell).getGradient()+1);

                    }
                }
            }
        }
    }

}
