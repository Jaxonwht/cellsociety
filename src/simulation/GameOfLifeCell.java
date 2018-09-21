package simulation;

import java.util.ArrayList;
import java.util.List;

public class GameOfLifeCell extends Cell {

    /**
     * @return A list of Cells that are "neighbors" of this Cell in GameOfLife.
     */
    @Override
    public List<Cell> getNeighbors() {
        Grid grid = this.getGrid();
        int row = this.getRow();
        int col = this.getCol();
        List<Cell> neighbors = new ArrayList<>();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (!(i == row && j == col && grid.isOutOfBounds(i, j))) {
                    neighbors.add(grid.item(i, j));
                }
            }
        }
        return neighbors;
    }

    @Override
    public void determineNextState() {

    }

    @Override
    protected void changeImageView() {

    }


}
