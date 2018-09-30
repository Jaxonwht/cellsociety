package simulation;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Julia Saveliff
 */
public class RockPaperScissorCell extends Cell {
    public static final int EMPTY = 0;
    public static final int ROCK = 1;
    public static final int PAPER = 2;
    public static final int SCISSOR = 3;
    private static final Paint EMPTY_COLOR = Color.WHITE;
    private static final Paint ROCK_COLOR = Color.RED;
    private static final Paint PAPER_COLOR = Color.BLUE;
    private static final Paint SCISSOR_COLOR = Color.GREEN;
    private static final Map<Integer, Paint> STATE_TO_PAINT_MAP = initMap();

    public static final double OPACITY_INCREMENT = 0.5; // TODO: Read in from XML file

    private static Map<Integer, Paint> initMap() {
        Map<Integer, Paint> map = new HashMap<>();
        map.put(EMPTY, EMPTY_COLOR);
        map.put(ROCK, ROCK_COLOR);
        map.put(PAPER, PAPER_COLOR);
        map.put(SCISSOR, SCISSOR_COLOR);
        return Collections.unmodifiableMap(map);
    }

    public RockPaperScissorCell(int state, int i, int j) {
        super(state,i,j);
    }

    public boolean beats(Cell other) {
        return (this.getState() == ROCK && other.getState() == SCISSOR) ||
                (this.getState() == SCISSOR && other.getState() == PAPER) ||
                (this.getState() == PAPER && other.getState() == ROCK);
    }

}
