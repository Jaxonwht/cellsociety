package simulation;

import java.util.List;
import java.util.Random;

/**
 * @author Julia Saveliff
 */
public class RockPaperScissorRule extends Rule {
    private Random rand = new Random();

    private final double OPACITY_INCREMENT;

    public RockPaperScissorRule(Grid grid, List<Double> extraParameters) {
        super(grid, extraParameters);
        OPACITY_INCREMENT = extraParameters.get(0);

    }

    @Override
    public void determineNextStates() {
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                Cell cell = this.getGrid().item(i, j);
                List<Cell> neighbors = this.getGrid().getAllNeighbors(i, j);
                Cell neighbor = neighbors.get(rand.nextInt(neighbors.size()));

                if (cell.getState() != RockPaperScissorCell.EMPTY) {
                    // cell is rock, paper, or scissor
                    if (neighbor.getState() != RockPaperScissorCell.EMPTY) {
                        // selected neighbor is rock, paper, or scissor
                        if (cell.getState() == neighbor.getState()){
                            cell.setNextState(cell.getState());
                        } else if (((RockPaperScissorCell) cell).beats(neighbor) ) {
                            // eat
                            cell.setNextState(cell.getState());
                            if (cell.getOpacity()<1) {
                                cell.setOpacity(cell.getOpacity()+OPACITY_INCREMENT);
                            }
                        } else {
                            // get eaten
                            cell.setNextState(neighbor.getState());
                            if (cell.getOpacity()>0) {
                                cell.setOpacity(cell.getOpacity()-OPACITY_INCREMENT);
                            }
                        }
                    } else {
                        // selected neighbor is empty
                        cell.setNextState(cell.getState());
                    }
                } else {
                    // cell is empty
                    if (neighbor.getState() != RockPaperScissorCell.EMPTY) {
                        // selected neighbor is rock, paper, or scissor
                        if (neighbor.getOpacity()>0) {
                            // selected neighbor can still reproduce
                            cell.setNextState(neighbor.getState());
                            cell.setOpacity(cell.getOpacity()-OPACITY_INCREMENT);
                        } else {
                            // selected neighbor can no longer reproduce
                            cell.setNextState(cell.getState());
                        }
                    }
                    else {
                        // selected neighbor is empty
                        cell.setNextState(cell.getState());
                    }
                }
            }
        }
    }

}
