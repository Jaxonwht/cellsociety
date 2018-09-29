package simulation;

import java.util.List;
import java.util.Random;

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
                List<Cell> neighbors = this.getNeighbors(i, j);
                Cell neighbor = neighbors.get(rand.nextInt(neighbors.size()));
                if (cell.getState() == RockPaperScissorCell.EMPTY) {
                    if (neighbor.getState() != RockPaperScissorCell.EMPTY) {
                        // cell is empty and selected neighbor is color
                        cell.setNextState(neighbor.getState());
                        // TODO: set cell next gradient = neighbor gradient + 1
                    } else {
                        // cell is empty and selected neighbor is empty
                        cell.setNextState(cell.getState());
                    }
                } else {
                    if (((RockPaperScissorCell) cell).beats(neighbor)) {
                        // feed and upgrade toward zero
                        cell.setNextState(cell.getState());
                        // TODO: cell next gradient = cell gradient - 1
                    } else if (!((RockPaperScissorCell) cell).beats(neighbor)) {
                        // killed and downgrade away from zero
                        cell.setNextState(cell.getState());
                        // TODO: cell next gradient = cell gradient + 1
                    }
                }
            }
        }
    }

}
