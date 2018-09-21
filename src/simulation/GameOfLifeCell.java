package simulation;

import javafx.scene.Group;

import java.util.*;

/**
 * @author Haotian Wang
 */
public class GameOfLifeCell extends Cell {
    public static final int DEAD = 0;
    public static final int ALIVE = 1;
    public static final String DEAD_IMAGE = "dead.gif";
    public static final String ALIVE_IMAGE = "alive.gif";
    public static final Map<Integer, String> STATE_TO_IMAGE_MAP = initMap();

    /**
     * A static method that creates an unmodifiable map for GameOfLife, relating the state int to the String representing image file name.
     * @return An unmodifiable map.
     */
    private static Map<Integer, String> initMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(DEAD, DEAD_IMAGE);
        map.put(ALIVE, ALIVE_IMAGE);
        return Collections.unmodifiableMap(map);
    }

    public GameOfLifeCell(Group root, int row, int col, double width, double height, int state) {
        super(root, row, col, width, height, state);
        setImageView(STATE_TO_IMAGE_MAP.get(state), width, height);
    }

    /**
     * Update the ImageView object in the root node if the state of the cell changes.
     */
    @Override
    protected void updateImageView() {
        if (this.getNextState() != this.getState()) {
            this.getRoot().getChildren().remove(this.getImageView());
            this.setImageView(STATE_TO_IMAGE_MAP.get(getNextState()), this.getWidth(), this.getHeight());
            this.getRoot().getChildren().add(this.getImageView());
        }
    }
}
