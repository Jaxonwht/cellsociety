package simulation;

import java.util.List;
import java.util.Random;

/**
 * @author Haotian Wang
 * A specific Rule class for Segregation, adapting from http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/.
 */
public class SegregationRule extends Rule {
    public final double SATISFACTION_THRESHOLD;
    public final Random rand = new Random();

    public SegregationRule(Grid grid, List<Double> extraParameters) {
        super(grid, extraParameters);
        SATISFACTION_THRESHOLD = extraParameters.get(0);
    }

    @Override
    public void determineNextStates() {
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                Cell cell = this.getGrid().item(i, j);
                if (cell.getState() == SegregationCell.TYPE_A || cell.getState() == SegregationCell.TYPE_B) {
                    int numSameType = 0;
                    List<Cell> neighbors = this.getNeighbors(i, j);
                    for (Cell neighbor : neighbors) {
                        if (cell.getState() == neighbor.getState()) {
                            numSameType++;
                        }
                    }
                    if (numSameType / neighbors.size() < SATISFACTION_THRESHOLD) {
                        int destX = rand.nextInt(this.getGrid().getNumCol());
                        int destY = rand.nextInt(this.getGrid().getNumRow());
                        while (this.getGrid().item(destX, destY).getState() != SegregationCell.EMPTY && this.getGrid().item(destX, destY).getNextState() != SegregationCell.EMPTY) {
                            destX = rand.nextInt(this.getGrid().getNumRow());
                            destY = rand.nextInt(this.getGrid().getNumCol());
                        }
                        this.getGrid().item(destX, destY).setNextState(cell.getState());
                        cell.setNextState(SegregationCell.EMPTY);
                    }
                }
            }
        }
    }
}
