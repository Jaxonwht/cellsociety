package simulation;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Yunhao Qing
 * A specific Rule class for Ant Forage game,
 * adapting rules from https://cs.gmu.edu/~eclab/projects/mason/publications/alife04ant.pdf.
 */
public class AntForageRule extends Rule {
    private final double ANT_RATIO;
    private final int LIFE_TIME;
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
    public AntForageRule(Grid grid, List<Double> extraParameters) {
        super(grid, extraParameters);
        ANT_RATIO = extraParameters.get(0);
        LIFE_TIME = (int)(Math.floor(extraParameters.get(1)));
        MAX_PHEROMONE = extraParameters.get(2);;
        EVAPORATION = extraParameters.get(3);
        DIFFUSION = extraParameters.get(4);
        K = extraParameters.get(5);
        N = extraParameters.get(6);
        initialiseAnts();
    }

    public void initialiseAnts(){
        Ants = new ArrayList<>();
        for (int i = 0; i < this.getGrid().getNumRow(); i++) {
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                AntForageCell cell = (AntForageCell) this.getGrid().item(i, j);
                if (cell.getState() == AntForageCell.EMPTY && r.nextDouble() < ANT_RATIO) {
                    Ants.add(new AntForageAnt(i,j,LIFE_TIME));
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
        AntForageCell cell = (AntForageCell) this.getGrid().item(ant.getxIndex(), ant.getxIndex());
        List<Cell> neighbors = this.getNeighbors(ant.getxIndex(), ant.getyIndex());
        //search through neighbours to get i, j of the neighbour that has the most home pheromones
        int i = 0;//TO BE REPLACED
        int j = 0;//TO BE REPLACED
        ant.setxIndex(i);
        ant.setyIndex(j);




    }

    //0 = N,1 = NE,2 = E,3 = SE,4 = S,5 = SW,6 = W,7 = NW
    /*public List<Cell> forwardLocations(AntForageAnt ant){
        Grid grid = this.getGrid();
        List<Cell> neighbors = new ArrayList<>();
        int x = ant.getxIndex();
        int y = ant.getyIndex();
        int[] index = {x,y-1,x-1,y-1,x-1,y,x-1,y+1,x,y+1,x+1,y+1,x+1,y,x+1,y-1,x,y-1,x-1,y-1};
        for (int i = ant.getOrientation()*2; i < ant.getOrientation()*2+6;){
            addCell(i,i+1,grid,neighbors);
            i = i+2;
        }
        return neighbors;
    }


    public void addCell(int i, int j, Grid grid, List<Cell> neighbors){
        if (!grid.isOutOfBounds(i, j)) {
            neighbors.add(grid.item(i, j));
        }
    }
    */

    public void antFindFoodSource(AntForageAnt ant){}



    public void pheromonesEvaporateAndDiffuse(){
        for (int i = 0; i < this.getGrid().getNumRow(); i++){
            for (int j = 0; j < this.getGrid().getNumCol(); j++) {
                AntForageCell cell = (AntForageCell) this.getGrid().item(i, j);
                cell.setFoodPheromones(cell.getFoodPheromones()*(1-EVAPORATION));
                //EVAPORATION
            }
        }
    }
}
