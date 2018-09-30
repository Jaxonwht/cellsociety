package simulation;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Julia Saveliff
 */
public class LoopCell extends Cell {
    private static final int EMPTY = 0;
    private static final int DATA = 1;
    private static final int WALL = 2;
    private static final int LEADER = 3;
    private static final int TURNER = 4;
    private static final int DISCONNECT = 5;
    private static final Paint COLOR_0 = Color.BLACK;
    private static final Paint COLOR_1 = Color.BLUE;
    private static final Paint COLOR_2 = Color.RED;
    private static final Paint COLOR_3 = Color.GREEN;
    private static final Paint COLOR_4 = Color.HOTPINK;
    private static final Paint COLOR_5 = Color.YELLOW;
    private static final Map<Integer, Paint> STATE_TO_PAINT_MAP = initMap();

    public static final int UP = -1;
    public static final int DOWN = 1;
    public static final int RIGHT= 2;
    public static final int LEFT = -2;

    private int myDirection;

    /**
     * A static method that creates an unmodifiable map for GameOfLife, relating the state int to the String representing image file name.
     * @return An unmodifiable map.
     */
    private static Map<Integer, Paint> initMap() {
        Map<Integer, Paint> map = new HashMap<>();
        map.put(EMPTY, COLOR_0);
        map.put(DATA, COLOR_1);
        map.put(WALL, COLOR_2);
        map.put(LEADER, COLOR_3);
        map.put(TURNER, COLOR_4);
        map.put(DISCONNECT, COLOR_5);
        return Collections.unmodifiableMap(map);
    }

    public LoopCell(double x, double y, double width, double height, int state) {
        super(x, y, width, height, state);
        this.setRectangle(STATE_TO_PAINT_MAP.get(state));
        myDirection = UP;

    }

    /**
     * Update the color of the shape of the cell to its corresponding next state.
     */
    @Override
    protected void updateShape(Map<Integer, Paint> map) {
        super.updateShape(LoopCell.STATE_TO_PAINT_MAP);
    }

    public void setDirection(int dir) { myDirection = dir; }

    public int getDirection() { return myDirection; }


}
