package rule;


import cell.AntForageAnt;
import cell.AntCell;
import cell.Cell;
import rule.Rule;
import simulation.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Yunhao Qing
 * A specific Rule class for Ant Forage game,
 * adapting rules from https://cs.gmu.edu/~eclab/projects/mason/publications/alife04ant.pdf.
 */
public class AntRule extends Rule {
    private final double ANT_RATIO;
    private final double MAX_PHEROMONE;
    private final double EVAPORATION;
    private final double DIFFUSION;
    private final double K;
    private final double N;
    private Random r;
    private List<AntForageAnt> Ants;

    /**
     * Constructor for SpreadingOfFireRule.
     * @param grid the grid with states number in each cell
     * @param extraParameters MY_BURNING_COUNT, PROB_GROWTH and PROB_CATCH
     */
    public AntRule(Grid grid, List<Double> extraParameters) {
        super(grid, extraParameters);
        ANT_RATIO = extraParameters.get(0);
        MAX_PHEROMONE = extraParameters.get(1);
        EVAPORATION = extraParameters.get(2);
        DIFFUSION = extraParameters.get(3);
        K = extraParameters.get(4);
        N = extraParameters.get(5);
        initialiseAnts();
    }

    public void initialiseAnts(){
        Ants = new ArrayList<>();
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                AntCell cell = (AntCell) this.getGrid().item(i, j);
                if (cell.getState() == AntCell.EMPTY && r.nextDouble() < ANT_RATIO) {
                    Ants.add(new AntForageAnt(i,j));
                }
            }
        }
    }

    @Override
    public void determineNextStates() {
        antsMoveAndDropPheromones();
        pheromonesEvaporateAndDiffuse();
    }

    public void antsMoveAndDropPheromones(){
        for (int i = 0; i < Ants.size();i++){
            AntForageAnt ant = Ants.get(i);
            if (ant.getHaveFood()){
                antReturnToNest(ant);
            }
            else{
                antFindFoodSource(ant);
            }
        }
    }

    public void antReturnToNest(AntForageAnt ant){
        AntCell currentCell = (AntCell)this.getGrid().item(ant.getrowIndex(), ant.getcolIndex());
        List<Cell> neighbors = this.getGrid().getAllNeighbors(ant.getrowIndex(), ant.getcolIndex());
        dropFoodPheromones(currentCell,neighbors);
        if (!forwardLocations(ant).isEmpty()) neighbors = forwardLocations(ant);
        Cell moveToCell = this.getGrid().item(ant.getrowIndex()+1, ant.getcolIndex());
        double maxPhero = 0;
        for (Cell c : neighbors){
            AntCell antcell = (AntCell) c;
            if (antcell.getNestPheromones()>=maxPhero){
                moveToCell = c;
                maxPhero = antcell.getNestPheromones();
            }
        }
        ant.setOrientation(determineNewOrientation(moveToCell,ant));
        ant.setcolIndex(moveToCell.getColIndex());
        ant.setrowIndex(moveToCell.getRowIndex());
        if (moveToCell.getState() == AntCell.NEST){
            ant.changeHaveFood();
        }
    }

    public void dropHomePheromones(AntCell currentCell,List<Cell> neighbors){
        if (currentCell.getState()== AntCell.NEST)
            currentCell.setNestPheromones(MAX_PHEROMONE);
        else{
            double maxPhero = 0;
            for (Cell cell : neighbors){
                AntCell thisCell = (AntCell)cell;
                maxPhero = Math.max(maxPhero,thisCell.getNestPheromones());
            }
            if (maxPhero - 2 - currentCell.getNestPheromones()>0)
                currentCell.setNestPheromones(currentCell.getNestPheromones()+1);
        }
    }

    public void dropFoodPheromones(AntCell currentCell, List<Cell> neighbors){
        if (currentCell.getState()== AntCell.FOOD)
            currentCell.setNestPheromones(MAX_PHEROMONE);
        else{
            double maxPhero = 0;
            for (Cell cell : neighbors){
                AntCell thisCell = (AntCell)cell;
                maxPhero = Math.max(maxPhero,thisCell.getFoodPheromones());
            }
            if (maxPhero - 2 - currentCell.getFoodPheromones()>0)
                currentCell.setFoodPheromones(currentCell.getFoodPheromones()+1);
        }
    }

    public int determineNewOrientation(Cell cell, AntForageAnt ant){
        if (cell.getRowIndex() == ant.getrowIndex()+1){
            if (cell.getColIndex()==ant.getcolIndex()+1){return 3;}
            else if (cell.getColIndex()==ant.getcolIndex()-1){return 7;}
        }
        else if (cell.getRowIndex() == ant.getrowIndex()-1){
            if (cell.getColIndex()==ant.getcolIndex()+1){return 2;}
            else if (cell.getColIndex()==ant.getcolIndex()){return 1;}
            else if (cell.getColIndex()==ant.getcolIndex()-1){return 0;}
        }
        else if (cell.getRowIndex() == ant.getrowIndex()){
            if (cell.getColIndex()==ant.getcolIndex()+1){return 4;}
            else if (cell.getColIndex()==ant.getcolIndex()){return 5;}
            else if (cell.getColIndex()==ant.getcolIndex()-1){return 6;}
        }
        return -1;
    }

    //0 = N,1 = NE,2 = E,3 = SE,4 = S,5 = SW,6 = W,7 = NW
    public List<Cell> forwardLocations(AntForageAnt ant){
        Grid grid = this.getGrid();
        List<Cell> neighbors = new ArrayList<>();
        int row = ant.getrowIndex();
        int col = ant.getcolIndex();
        int[] index = {row-1,col,row-1,col+1,row,col+1,row+1,col+1,row+1,col,
                row+1,col-1,row,col-1,row-1,col-1,row-1,col,row-1,col+1};
        for (int i = ant.getOrientation()*2; i < ant.getOrientation()*2+6;){
            addCell(index[i],index[i+1],grid,neighbors);
            i = i+2;
        }
        return neighbors;
    }

    public void addCell(int i, int j, Grid grid, List<Cell> neighbors){
        if (!grid.isOutOfBounds(i, j)) {
            neighbors.add(grid.item(i, j));
        }
    }

    public void antFindFoodSource(AntForageAnt ant){
        AntCell currentCell = (AntCell)this.getGrid().item(ant.getrowIndex(), ant.getcolIndex());
        List<Cell> neighbors = this.getGrid().getAllNeighbors(ant.getrowIndex(), ant.getcolIndex());
        dropHomePheromones(currentCell,neighbors);
        if (!forwardLocations(ant).isEmpty()) neighbors = forwardLocations(ant);
        Cell moveToCell = this.getGrid().item(ant.getrowIndex()+1, ant.getcolIndex());
        double maxPhero = 0;
        for (Cell c : neighbors){
            AntCell antcell = (AntCell)c;
            if (antcell.getFoodPheromones()>=maxPhero){
                moveToCell = c;
                maxPhero = antcell.getFoodPheromones();
            }
        }
        ant.setOrientation(determineNewOrientation(moveToCell,ant));
        ant.setcolIndex(moveToCell.getColIndex());
        ant.setrowIndex(moveToCell.getRowIndex());
        if (moveToCell.getState() == AntCell.FOOD){
            ant.changeHaveFood();
            moveToCell.setNextState(2);
        }
    }

    public void pheromonesEvaporateAndDiffuse(){
        for (int i = 0; i < this.getGrid().getNumRow(); i++){
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                AntCell cell = (AntCell) this.getGrid().item(i, j);
                cell.setFoodPheromones(cell.getFoodPheromones()*(1-EVAPORATION));
                //EVAPORATION
                if(cell.getNextState()==-1)
                    cell.setNextState(cell.getState());
            }
        }
    }
}
