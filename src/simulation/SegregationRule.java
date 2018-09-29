package simulation;

import java.util.List;

/**
 * @author Haotian Wang, Julia Saveliff
 * A specific Rule class for Segregation, adapting from http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/.
 */
public class SegregationRule extends Rule {
    private final double SATISFACTION_THRESHOLD;

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
                    // If Type A or Type B...
                    int numSameType = 0;
                    List<Cell> neighbors = getGrid().getAllNeighbors(i, j);
                    for (Cell neighbor : neighbors) {
                        if (cell.getState() == neighbor.getState()) {
                            numSameType++;
                        }
                    }
                    if (numSameType / (float) neighbors.size() < SATISFACTION_THRESHOLD) {
                        // If unsatisfied...
                        int destX = Rule.rand.nextInt(this.getGrid().getNumCol());
                        int destY = Rule.rand.nextInt(this.getGrid().getNumRow());
                        while (this.getGrid().item(destX, destY).getState() != SegregationCell.EMPTY
                                || (this.getGrid().item(destX, destY).getNextState() == SegregationCell.TYPE_A
                                     || this.getGrid().item(destX, destY).getNextState() == SegregationCell.TYPE_B)) {
                            destX = Rule.rand.nextInt(this.getGrid().getNumRow());
                            destY = Rule.rand.nextInt(this.getGrid().getNumCol());
                        }
                        this.getGrid().item(destX, destY).setNextState(cell.getState());
                        cell.setNextState(SegregationCell.EMPTY);
                    } else {
                        // If satisfied...
                        cell.setNextState(cell.getState());
                    }
                } else if (cell.getState() == SegregationCell.EMPTY && cell.getNextState() == Cell.UNINITIALIZED) {
                    // If uninitialized empty...
                    cell.setNextState(SegregationCell.EMPTY);
                }
            }
        }
    }
}
