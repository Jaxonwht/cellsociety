package simulation;

import java.util.ArrayList;
import java.util.List;

public class GameOfLifeCell extends Cell {

    /**
     *
     * @return list of NESW neighbors
     */
    @Override
    public List<Cell> getNeighbors() {
        Grid grid = this.getGrid();
        int i = this.getIndexX();
        int j = this.getIndexY();
        List<Cell> neighbors = new ArrayList<>();

        if (this.getIndexX() == 0) {

            // left column
            neighbors.add(grid.item(i, j + 1)); // E
            neighbors.add(grid.item(i, grid.getNumCol() - 1)); // W

        } else if (this.getIndexX() == grid.getNumCol()) {

            // right column
            neighbors.add(grid.item(i, 0)); // E
            neighbors.add(grid.item(i, j - 1)); // W

        } else {

            // middle column
            neighbors.add(grid.item(i, j + 1)); // E
            neighbors.add(grid.item(i, j - 1)); // W

        }

        if (this.getIndexY() == 0) {

            // top row
            neighbors.add(grid.item(grid.getNumRow() - 1, j)); // N
            neighbors.add(grid.item(i + 1, j)); // S

        } else if (this.getIndexY() == grid.getNumRow()) {

            // bottom row
            neighbors.add(grid.item(i - 1, j)); // N
            neighbors.add(grid.item(0, j)); // S

        } else {

            // middle row
            neighbors.add(grid.item(i - 1, j)); // N
            neighbors.add(grid.item(i + 1, j)); // S

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
