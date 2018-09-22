package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A specific Rule class for Wator.
 * @author Yunhao Qing
 */
public class WatorRule extends Rule {
	
	//nextState = currentState, this must be initilised for this class to work.
	
    public final int REPRODUCTION_FISH;
	public final int REPRODUCTION_SHARK;
    public final Random rand = new Random();

    public WatorRule(Grid grid, List<Double> extraParameters) {
        super(grid, extraParameters);
        REPRODUCTION_FISH = (int)Math.floor(extraParameters.get(0));
		REPRODUCTION_SHARK = (int)Math.floor(extraParameters.get(1);
    }

    @Override
    protected List<Cell> getNeighbors(int row, int col) {
        Grid grid = this.getGrid();
        List<Cell> neighbors = new ArrayList<Cell>();
        List<int[]> cells  = new ArrayList<>();
        cells.add(new int[]{row+1,col});
        cells.add(new int[]{row-1,col});
        cells.add(new int[]{row,col+1});
        cells.add(new int[]{row+1,col-1});
        for (int[] cell : cells){
            if (!grid.isOutOfBounds(cell[0], cell[1])) {
                neighbors.add(grid.item(cell[0], cell[1]));
            }
        }
        return neighbors;
    }

	
    @Override
    public void determineNextStates() {
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                WatorCell cell = (WatorCell)this.getGrid().item(i, j);

				if (cell.getState() == WatorCell.SHARK){
					cell.setSurviveTime(cell.getSurviveTime()+1);
					List<Cell> neighbors = this.getNeighbors(i, j);
					boolean eat = false;
					List<Cell> possibleFoods = new ArrayList<Cell>();
					for (Cell neighbor : neighbors) {
                        if (neighbor.getState() == WatorCell.FISH && neighbor.getNextState() != WatorCell.EMPTY) {
                            possibleFoods.add(neighbor);
                        }
                    }
                    if (!possibleFoods.isEmpty()){
                        eat = true;
                        Cell food = possibleFoods.get(rand.nextInt(possibleFoods.size()));
                        food.setNextState(WatorCell.EMPTY);
                    }

                    if (!eat){
					List<Cell> possibleMoves = new ArrayList<Cell>();
					for (Cell neighbor : neighbors) {
                        if (neighbor.getState() == WatorCell.EMPTY && neighbor.getNextState() == WatorCell.EMPTY) {
                            possibleMoves.add(neighbor);
                        }
                    }
					Cell move = possibleMoves.get(rand.nextInt(possibleMoves.size()));
					move.setNextState(WatorCell.EMPTY);
                    }
					
					if (cell.getSurviveTime() >= REPRODUCTION_SHARK){
						List<Cell> possibleReprobs = new ArrayList<Cell>();
						for (Cell neighbor : neighbors) {
							if (neighbor.getState() == WatorCell.EMPTY && neighbor.getNextState() == WatorCell.EMPTY) {
								possibleReprobs.add(neighbor);
							}
						}
						Cell possibleReprob = possibleReprobs.get(rand.nextInt(possibleReprobs.size()));
                        possibleReprob.setNextState(WatorCell.SHARK);
					}
				}
				
				
				if (cell.getState() == WatorCell.FISH && cell.getNextState() == WatorCell.FISH){
					//TODO
				}
            }
        }
    }
}
