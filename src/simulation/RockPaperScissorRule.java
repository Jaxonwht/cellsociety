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

    @Override
    public void determineNextStates() {
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                Cell cell = this.getGrid().item(i, j);
                List<Cell> neighbors = this.getGrid().getAllNeighbors(i, j);
                Cell neighbor = neighbors.get(rand.nextInt(neighbors.size()));

                if (cell.getState() != RockPaperScissorCell.EMPTY) {
                    if (neighbor.getState() != RockPaperScissorCell.EMPTY) {
                        if (((RockPaperScissorCell) cell).beats(neighbor) ) {
                            // eat
                            cell.setNextState(cell.getState());
                            if (cell.getOpacity()<1) {
                                cell.setOpacity(cell.getOpacity()+RockPaperScissorCell.OPACITY_INCREMENT);
                            }
                        } else {
                            // get eaten
                            cell.setNextState(neighbor.getState());
                            if (cell.getOpacity()>0) {
                                cell.setOpacity(cell.getOpacity()-RockPaperScissorCell.OPACITY_INCREMENT);
                            }
                        }
                    } else {
                        cell.setNextState(cell.getState());
                    }
                } else {
                    if (neighbor.getState() != RockPaperScissorCell.EMPTY) {
                        cell.setNextState(neighbor.getState());
                        if (cell.getOpacity()>0) {
                            cell.setOpacity(cell.getOpacity()-RockPaperScissorCell.OPACITY_INCREMENT);
                        }
                    }
                    else {
                        cell.setNextState(RockPaperScissorCell.EMPTY);
                    }
                }
            }
        }
    }

}
