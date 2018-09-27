package simulation;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yunhao Qing
 * This is a class specific for SpreadingOfFireCell, cell behaviours are set 
 * based on http://nifty.stanford.edu/2011/scott-wator-world/.
 * The SpreadingOfFireCell can have 3 states, being empty, a normal tree or 
 * a burning tree.

 */
public class SpreadingOfFireCell extends Cell {
    public static final int EMPTY = 0;
    public static final int NORMAL = 1;
    public static final int BURNING = 2;
    private static final Paint EMPTY_COLOR = Color.LIGHTGRAY;
    private static final Paint NORMAL_COLOR = Color.GREEN;
    private static final Paint BURNING_COLOR = Color.RED;
    private static final Map<Integer, Paint> STATE_TO_PAINT_MAP = initMap();

    private int myBurningTime;

    /**
     * @return An unmodifiable map.
     */
    private static Map<Integer, Paint> initMap() {
        Map<Integer, Paint> map = new HashMap<>();
        map.put(EMPTY, EMPTY_COLOR);
        map.put(NORMAL, NORMAL_COLOR);
        map.put(BURNING, BURNING_COLOR);
        return Collections.unmodifiableMap(map);
    }

    /**
     * Constructor for SpreadingOfFireCell.
     */
    public SpreadingOfFireCell(double x, double y, double width, double height, int state) {
        super(x, y, width, height, state);
        this.setRectangle(STATE_TO_PAINT_MAP.get(state));
        this.myBurningTime = 0;
    }

    /**
     * Update the color of the shape of the cell to its corresponding next state.
     */
    @Override
    protected void updateShape(Map<Integer, Paint> map) {
        super.updateShape(SpreadingOfFireCell.STATE_TO_PAINT_MAP);
    }
    
    /**
     * @return mySurviveTime how long a burning tree has been burning.
     */
    public int getBurningTime() {
        return myBurningTime;
    }

    /**
     * @param time the new time that the burning tree has been burning.
     */
    public void setBurningTime(int time) {
        myBurningTime = time;
    }

}
