package simulation;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Haotian Wang
 */
public class GameOfLifeCell extends Cell {
    public static final int DEAD = 0;
    public static final int ALIVE = 1;
    private static final Paint DEAD_COLOR = Color.RED;
    private static final Paint ALIVE_COLOR = Color.LIGHTGRAY;
    private static final Map<Integer, Paint> STATE_TO_PAINT_MAP = initMap();

    /**
     * A static method that creates an unmodifiable map for GameOfLife, relating the state int to the String representing image file name.
     * @return An unmodifiable map.
     */
    private static Map<Integer, Paint> initMap() {
        Map<Integer, Paint> map = new HashMap<>();
        map.put(DEAD, DEAD_COLOR);
        map.put(ALIVE, ALIVE_COLOR);
        return Collections.unmodifiableMap(map);
    }

    public GameOfLifeCell(double x, double y, double width, double height, int state) {
        super(x, y, width, height, state);
        this.setRectangle(STATE_TO_PAINT_MAP.get(state));
    }

    /**
     * Update the color of the shape of the cell to its corresponding next state.
     */
    @Override
    protected void updateShape(Map<Integer, Paint> map) {
        super.updateShape(GameOfLifeCell.STATE_TO_PAINT_MAP);
    }
}
