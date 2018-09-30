package simulation;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yunhao Qing
 * This is a class specific for Ant Forage, cell behaviours are set
 * based on https://cs.gmu.edu/~eclab/projects/mason/publications/alife04ant.pdf.
 * The AntForageCell can have 3 states, being empty, nest or
 * food.

 */
public class AntForageCell extends Cell {
    public static final int EMPTY = 0;
    public static final int NEST = 1;
    public static final int FOOD = 2;
    private static final Paint EMPTY_COLOR = Color.LIGHTGRAY;
    private static final Paint NEST_COLOR = Color.GREEN;
    private static final Paint FOOD_COLOR = Color.RED;
    private static final Map<Integer, Paint> STATE_TO_PAINT_MAP = initMap();
    private double foodPheromones;
    private double nestPheromones;



    /**
     * @return An unmodifiable map.
     */
    private static Map<Integer, Paint> initMap() {
        Map<Integer, Paint> map = new HashMap<>();
        map.put(EMPTY, EMPTY_COLOR);
        map.put(NEST, NEST_COLOR);
        map.put(FOOD, FOOD_COLOR);
        return Collections.unmodifiableMap(map);
    }

    /**
     * Constructor for AntForageCell.
     */
    public AntForageCell(double x, double y, double width, double height, int state) {
        super(x, y, width, height, state);
        this.setRectangle(STATE_TO_PAINT_MAP.get(state));
        foodPheromones = 0.0;
        nestPheromones = 0.0;
    }

    /**
     * Update the color of the shape of the cell to its corresponding next state.
     */
    @Override
    protected void updateShape(Map<Integer, Paint> map) {
        super.updateShape(AntForageCell.STATE_TO_PAINT_MAP);
    }

    public double getFoodPheromones(){
        return foodPheromones;
    }

    public void setFoodPheromones(double d){
        foodPheromones = d;
    }

    public double getNestPheromones(){
        return nestPheromones;
    }

    public void setNestPheromones(double d){
        nestPheromones = d;
    }

}
