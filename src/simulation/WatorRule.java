package simulation;

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

    public WatorRule(Grid grid, List<int> extraParameters) {
        super(grid, extraParameters);
        REPRODUCTION_FISH = extraParameters.get(0);
		REPRODUCTION_SHARK = extraParameters.get(0);
    }

	@Override
    protected List<Cell> getNeighbors(int row, int col) {
        Grid grid = this.getGrid();
        List<Cell> neighbors = new ArrayList<Cell>();
        if (!grid.isOutOfBounds(row + 1, col)){
            neighbors.add(grid.item(row + 1, col));
        }
        if (!grid.isOutOfBounds(row - 1, col)){
            neighbors.add(grid.item(row - 1, col));
        }
        if (!grid.isOutOfBounds(row, col + 1)){
            neighbors.add(grid.item(row, col + 1));
        }
        if (!grid.isOutOfBounds(row, col - 1)){
            neighbors.add(grid.item(row, col - 1));
        }
        return neighbors;
    }

	
    @Override
    public void determineNextStates() {
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                Cell cell = this.getGrid().item(i, j);
				if (cell.getState() == WatorCell.SHARK){
					cell.addSurviveTime();
					List<Cell> neighbors = this.getNeighbors(i, j);
					
					List<Cell> possibleFoods = new ArrayList<Cell>();
					for (Cell neighbor : neighbors) {
                        if (neighbor.getState() == WatorCell.FISH && neighbor.getNextState != WatorCell.EMPTY) {
                            possibleFoods.add(neighbor);
                        }
                    }
					Cell food = possibleFoods.get(rand.nextInt(possibleFoods.size()));
					food.setNextState = WatorCell.EMPTY;
					
					List<Cell> possibleMove = new ArrayList<Cell>();
					for (Cell neighbor : neighbors) {
                        if (neighbor.getState() == WatorCell.EMPTY && neighbor.getNextState == WatorCell.EMPTY) {
                            possibleMove.add(neighbor);
                        }
                    }
					Cell move = possibleMoves.get(rand.nextInt(possibleMoves.size()));
					move.setNextState = WatorCell.EMPTY;
					
					if (cell.getSurviveTime >= REPRODUCTION_SHARK){
						List<Cell> possibleReprobs = new ArrayList<Cell>();
						for (Cell neighbor : neighbors) {
							if (neighbor.getState() == WatorCell.EMPTY && neighbor.getNextState == WatorCell.EMPTY) {
								possibleReprobs.add(neighbor);
							}
						}
						Cell possibleReprob = possibleReprobs.get(rand.nextInt(possibleReprobs.size()));
						placeToReprob.setNextState = WatorCell.SHARK;
					}
				}
				
				
				if (cell.getState() == WatorCell.FISH && cell.getNextState == WatorCell.FISH){
					//TODO
				}
            }
        }
    }
}
