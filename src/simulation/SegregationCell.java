package simulation;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Haotian Wang
 */
public class SegregationCell extends Cell{
    public static final double SATISFACTION_THRESHOLD = 0.3;
    public static final int TYPE_A = 0;
    public static final int TYPE_B = 1;
    public static final Paint TYPE_A_COLOR = Color.WHITE;
    public static final Paint TYPE_B_COLOR = Color.RED;
    public static final Map<Integer, Paint> STATE_TO_PAINT_MAP = initMap();

    /**
     * A static method that creates an unmodifiable map for GameOfLife, relating the state int to the String representing image file name.
     * @return An unmodifiable map.
     */
    private static Map<Integer, Paint> initMap() {
        Map<Integer, Paint> map = new HashMap<>();
        map.put(TYPE_A, TYPE_A_COLOR);
        map.put(TYPE_B, TYPE_B_COLOR);
        return Collections.unmodifiableMap(map);
    }

    public SegregationCell(Group root, double x, double y, double width, double height, int state) {
        super(root, x, y, width, height, state);
        this.setRectangle(STATE_TO_PAINT_MAP.get(state));
    }

    /**
     * Update the color of the shape of the cell to its corresponding next state.
     */
    @Override
    protected void updateShape(Map<Integer, Paint> map) {
        super.updateShape(SegregationCell.STATE_TO_PAINT_MAP);
    }
}
