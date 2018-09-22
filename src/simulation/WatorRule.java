package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A specific Rule class for Wator.
 *
 * @author Yunhao Qing
 */
public class WatorRule extends Rule {

    //nextState = currentState, this must be initilised for this class to work.

    public final int REPRODUCTION_FISH;
    public final int REPRODUCTION_SHARK;
    public final Random rand = new Random();

    public WatorRule(Grid grid, List<Double> extraParameters) {
        super(grid, extraParameters);
        REPRODUCTION_FISH = (int) Math.floor(extraParameters.get(0));
        REPRODUCTION_SHARK = (int) Math.floor(extraParameters.get(1));
        System.out.println(REPRODUCTION_FISH);
        System.out.println(REPRODUCTION_SHARK);


    }

    @Override
    protected List<Cell> getNeighbors(int row, int col) {
        Grid grid = this.getGrid();
        List<Cell> neighbors = new ArrayList<Cell>();
        List<int[]> cells = new ArrayList<>();
        cells.add(new int[]{row + 1, col});
        cells.add(new int[]{row - 1, col});
        cells.add(new int[]{row, col + 1});
        cells.add(new int[]{row, col - 1});
        for (int[] cell : cells) {
            if (!grid.isOutOfBounds(cell[0], cell[1])) {
                neighbors.add(grid.item(cell[0], cell[1]));
            }
        }
        return neighbors;
    }

    public boolean moveCell(WatorCell cell, List<Cell> neighbors){
        List<Cell> possMoves = new ArrayList<Cell>();
        for (Cell neighbor : neighbors) {
            if (neighbor.getState() == WatorCell.EMPTY &&
                    neighbor.getNextState() == WatorCell.UNINITIALIZED) {
                possMoves.add(neighbor);
            }
        }
        if (!possMoves.isEmpty()) {
            WatorCell move = (WatorCell) possMoves.get(rand.nextInt(possMoves.size()));
            move.setNextState(cell.getState());
            move.setSurviveTime(cell.getSurviveTime());
            cell.setNextState(WatorCell.EMPTY);
            cell.setSurviveTime(0);
            return true;
        }
        return false;
    }

    public boolean reprobCell(WatorCell cell,List<Cell> neighbors){
        if (cell.getSurviveTime() >= REPRODUCTION_SHARK) {
            List<Cell> possReprobs = new ArrayList<Cell>();
            for (Cell neighbor : neighbors) {
                if (neighbor.getState() == WatorCell.EMPTY &&
                        neighbor.getNextState() == WatorCell.UNINITIALIZED) {
                    possReprobs.add(neighbor);
                }
            }
            if (!possReprobs.isEmpty()) {
                cell.setSurviveTime(0);
                Cell reprob = possReprobs.get(rand.nextInt(possReprobs.size()));
                reprob.setNextState(WatorCell.SHARK);
                return true;
            }
        }
        return false;
    }

    public boolean eatCell(List<Cell> neighbors){
        List<Cell> possFoods = new ArrayList<Cell>();
        for (Cell neighbor : neighbors) {
            if (neighbor.getState() == WatorCell.FISH &&
                    neighbor.getNextState() == WatorCell.UNINITIALIZED) {
                possFoods.add(neighbor);
            }
        }
        if (!possFoods.isEmpty()) {
            WatorCell food = (WatorCell) possFoods.get(rand.nextInt(possFoods.size()));
            food.setNextState(WatorCell.EMPTY);
            food.setSurviveTime(0);
            return true;
        }
        return false;
    }

    @Override
    public void determineNextStates() {
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                WatorCell cell = (WatorCell) this.getGrid().item(i, j);
                if (cell.getState() == WatorCell.SHARK) {
                    cell.setSurviveTime(cell.getSurviveTime() + 1);
                    List<Cell> neighbors = this.getNeighbors(i, j);
                    if (!reprobCell(cell,neighbors)) {
                        if (!eatCell(neighbors)) {
                            moveCell(cell,neighbors);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                WatorCell cell = (WatorCell) this.getGrid().item(i, j);
                if (cell.getState() == WatorCell.FISH && cell.getNextState() == WatorCell.UNINITIALIZED) {
                    cell.setSurviveTime(cell.getSurviveTime() + 1);
                    List<Cell> neighbors = this.getNeighbors(i, j);
                    if (!reprobCell(cell,neighbors)) {
                            moveCell(cell,neighbors);
                    }
                }
            }
        }
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                WatorCell cell = (WatorCell) this.getGrid().item(i, j);
                if (cell.getNextState() == WatorCell.UNINITIALIZED)
                    cell.setNextState(cell.getState());
            }
        }

       /* for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                WatorCell cell = (WatorCell) this.getGrid().item(i, j);
                //System.out.printf("Cell at row " + i + "column" + j)
            }
        }*/
    }
}