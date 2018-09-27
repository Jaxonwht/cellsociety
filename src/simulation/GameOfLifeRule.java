package simulation;

import java.util.List;

/**
 * @author Haotian Wang
 * A specific Rule class for GameOfLife game, adapting from https://en.wikipedia.org/wiki/Conway's_Game_of_Life#Rules.
 */
public class GameOfLifeRule extends Rule {
    // If number of neighbors < UNDERPOPULATION_THRESHOLD, underpopulation happens.
    private static final int UNDERPOPULATION_THRESHOLD = 2;
    // If number of neighbors > OVERPOPULATION_THRESHOLD, overpopulation happens.
    private static final int OVERPOPULATION_THRESHOLD = 3;

    public GameOfLifeRule(Grid grid, List<Double> extraParameters) {
        super(grid, extraParameters);
    }

    @Override
    public void determineNextStates() {
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                Cell cell = this.getGrid().item(i, j);
                List<Cell> neighbors = this.getNeighbors(i, j);
                checkCell(cell, neighbours);
            }
        }
    }
    
    public void checkCell(Cell cell, List<Cell> neighbors){
        int numAliveNeighbors = 0;
        for (Cell neighbor : neighbors) {
            if (neighbor != null && neighbor.getState() == GameOfLifeCell.ALIVE) {
                numAliveNeighbors++;
            }
        }
        if (cell.getState() == GameOfLifeCell.DEAD && numAliveNeighbors == OVERPOPULATION_THRESHOLD) {
            cell.setNextState(GameOfLifeCell.ALIVE);
        } else if (cell.getState() == GameOfLifeCell.ALIVE &&
                (numAliveNeighbors < UNDERPOPULATION_THRESHOLD || numAliveNeighbors > OVERPOPULATION_THRESHOLD)) {
            cell.setNextState(GameOfLifeCell.DEAD);
        } else {
            cell.setNextState(cell.getState());
        }
    }
}
