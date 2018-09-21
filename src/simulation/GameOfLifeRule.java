package simulation;

import java.util.List;

/**
 * A specific Rule class for GameOfLife game.
 */
public class GameOfLifeRule extends Rule {
    public GameOfLifeRule(Grid grid) {
        super(grid);
    }

    @Override
    public void determineNextStates() {
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                Cell cell = this.getGrid().item(i, j);
                List<Cell> neighbors = this.getNeighbors(cell);
                int numAliveNeighbors = 0;
                for (Cell neighbor : neighbors) {
                    if (neighbor != null && neighbor.getState() == GameOfLifeCell.ALIVE) { numAliveNeighbors++; }
                }
                int numDeadNeighbors = neighbors.size() - numAliveNeighbors;
                if (cell.getState() == GameOfLifeCell.DEAD && numAliveNeighbors == 3) { cell.setNextState(GameOfLifeCell.ALIVE); }
                else if (cell.getState() == GameOfLifeCell.ALIVE && numAliveNeighbors < 2) { cell.setNextState(GameOfLifeCell.DEAD); }
                else if (cell.getState() == GameOfLifeCell.ALIVE && numAliveNeighbors <= 3) {}
                else if (cell.getState() == GameOfLifeCell.ALIVE && numAliveNeighbors > 3) { cell.setNextState(GameOfLifeCell.DEAD); }
            }
        }
    }
}
