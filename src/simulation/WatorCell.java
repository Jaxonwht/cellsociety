package simulation;

import javafx.scene.Group;

import java.util.*;

public class WatorCell extends Cell {
    public static final int EMPTY = 0;
    public static final int FISH = 1;
    public static final int SHARK = 2;
    public static final String EMPTY_IMAGE = "empty.gif";
    public static final String FISH_IMAGE = "fish.gif";
    public static final String SHARK_IMAGE = "shark.gif";
    public static final Map<Integer, String> STATE_TO_IMAGE_MAP = initMap();
    private int mySurviveTime;

    /**
     * @return An unmodifiable map.
     */
    private static Map<Integer, String> initMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(EMPTY, EMPTY_IMAGE);
        map.put(FISH, FISH_IMAGE);
        map.put(SHARK, SHARK_IMAGE);
        return Collections.unmodifiableMap(map);
    }

    public  WatorCell(Group root, int row, int col, double width, double height, int state) {
        super(root, row, col, width, height, state);
        setImageView(STATE_TO_IMAGE_MAP.get(state), width, height);
        mySurviveTime = 0;
    }

    @Override
    protected void updateImageView() {
        if (this.getNextState() != this.getState()) {
            this.getRoot().getChildren().remove(this.getImageView());
            this.setImageView(STATE_TO_IMAGE_MAP.get(getNextState(), this.getWidth(), this.getHeight()));
        }
    }

    public int getSurviveTime() {
        return mySurviveTime;
    }

    public void setSurviveTime(int time) {
        mySurviveTime = time;
    }

}
