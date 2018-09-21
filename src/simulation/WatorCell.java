package simulation;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.*;

/**
 * @author Yunhao Qing, Haotian Wang
 */
public class WatorCell extends Cell {
    public static final int EMPTY = 0;
    public static final int FISH = 1;
    public static final int SHARK = 2;
    public static final Paint EMPTY_COLOR = Color.WHITE;
    public static final Paint FISH_COLOR = Color.AZURE;
    public static final Paint SHARK_COLOR = Color.BLACK;
    public static final Map<Integer, Paint> STATE_TO_PAINT_MAP = initMap();

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

    public WatorCell(Group root, int row, int col, double width, double height, int state) {
        super(root, row, col, width, height, state);
        this.setRectangle(STATE_TO_PAINT_MAP.get(state));
        mySurviveTime = 0;
    }

    /**
     * Update the color of the shape of the cell to its corresponding next state.
     */
    @Override
    protected void updateShape() {
        if (this.getState() != this.getNextState()) {
            this.getShape().setFill(STATE_TO_PAINT_MAP.get(this.getNextState()));
        }
    }

    public int getSurviveTime() {
        return mySurviveTime;
    }

    public void setSurviveTime(int time) {
        mySurviveTime = time;
    }

}
