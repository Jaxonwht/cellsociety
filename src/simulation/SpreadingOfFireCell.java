package simulation;

import javafx.scene.Group;

import java.util.*;

public class SpreadingOfFireCell extends Cell {
    public static final int EEMPTY = 0;
    public static final int NORMAL = 1;
    public static final int BURNING = 2;
    public static final String EMPTY_IMAGE = "empty.gif";
    public static final String NORMAL_IMAGE = "normal.gif";
    public static final String BURNING_IMAGE = "burning.gif";
    public static final Map<Integer, String> STATE_TO_IMAGE_MAP = initMap();

    /**
     * @return An unmodifiable map.
     */
    private static Map<Integer, String> initMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(EMPTY, EMPTY_IMAGE);
        map.put(NORMAL, NORMAL_IMAGE);
        map.put(BURNING, BURNING_IMAGE);
        return Collections.unmodifiableMap(map);
    }

    public GameOfLifeCell(Group root, int row, int col, double width, double height, int state) {
        super(root, row, col, width, height, state);
        setImageView(STATE_TO_IMAGE_MAP.get(state), width, height);
    }

    @Override
    protected void updateImageView() {
        if (this.getNextState() != this.getState()) {
            this.getRoot().getChildren().remove(this.getImageView());
            this.setImageView(STATE_TO_IMAGE_MAP.get(getNextState(), this.getWidth(), this.getHeight()));
        }
    }
}
