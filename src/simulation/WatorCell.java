package simulation;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.*;

/**
 * @author Yunhao Qing
 * This is a class specific for WatorCell, cell behaviours are set based on
 * http://nifty.stanford.edu/2011/scott-wator-world/.
 * The WatorCell can have 3 states, being empty, a fish or a shark.
 */
public class WatorCell extends Cell {
    public static final int EMPTY = 0;
    public static final int FISH = 1;
    public static final int SHARK = 2;
    private static final Paint EMPTY_COLOR = Color.LIGHTGRAY;
    private static final Paint FISH_COLOR = Color.GREEN;
    private static final Paint SHARK_COLOR = Color.BLUE;
    private static final Map<Integer, Paint> STATE_TO_PAINT_MAP = initMap();

    private int mySurviveTime;

    /**
     * @return An unmodifiable map.
     */
    private static Map<Integer, Paint> initMap() {
        Map<Integer, Paint> map = new HashMap<>();
        map.put(EMPTY, EMPTY_COLOR);
        map.put(FISH, FISH_COLOR);
        map.put(SHARK, SHARK_COLOR);
        return Collections.unmodifiableMap(map);
    }
    
    /**
     * Constructor for WatorCell.
     */
    public WatorCell(double x, double y, double width, double height, int state) {
        super(x, y, width, height, state);
        this.setRectangle(STATE_TO_PAINT_MAP.get(state));
        this.mySurviveTime = 0;
    }

    /**
     * Update the color of the shape of the cell to its corresponding next state.
     */
    @Override
    protected void updateShape(Map<Integer, Paint> map) {
        super.updateShape(WatorCell.STATE_TO_PAINT_MAP);
    }
    
    /**
     * @return mySurviveTime how long the fish/shark has survived from last
     * reproduction.
     */
    public int getSurviveTime() {
        return mySurviveTime;
    }
    
    /**
     * @param time the new time that the fish/shark has survived from last
     * reproduction.
     */
    public void setSurviveTime(int time) {
        mySurviveTime = time;
    }

}
