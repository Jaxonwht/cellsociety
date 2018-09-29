package simulation;

import javafx.print.Paper;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    private int myGradient;
    private int myNextGradient;

    private static Map<Integer, Paint> initMap() {
        Map<Integer, Paint> map = new HashMap<>();
        map.put(EMPTY, EMPTY_COLOR);
        map.put(ROCK, ROCK_COLOR);
        map.put(PAPER, PAPER_COLOR);
        map.put(SCISSOR, SCISSOR_COLOR);
        return Collections.unmodifiableMap(map);
    }

    public RockPaperScissorCell(double x, double y, double width, double height, int state) {
        super(x, y, width, height, state);
        myGradient = 0;
    }

    @Override
    protected void updateShape(Map<Integer, Paint> map) {
        super.updateShape(RockPaperScissorCell.STATE_TO_PAINT_MAP);
        // also set gradient here
    }

    public boolean beats(Cell other) {
        return (this.getState() == ROCK && other.getState() == SCISSOR) ||
                (this.getState() == SCISSOR && other.getState() == PAPER) ||
                (this.getState() == PAPER && other.getState() == ROCK);
    }

    public int getGradient() { return myGradient; }

    public void setGradient(int gradient) { myGradient = gradient; }

    public int getNextGradient() { return myNextGradient; }

    public void setNextGradient(int gradient) { myNextGradient = gradient; }

    public void updateToNextGradient() { myGradient = myNextGradient; }

}
