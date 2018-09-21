package simulation;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class GameOfLifeCell extends Cell {
    public static final int DEAD = 0;
    public static final int ALIVE = 1;
    public static final String DEAD_IMAGE = "dead.gif";
    public static final String ALIVE_IMAGE = "alive.gif";

    public GameOfLifeCell(Group root, int row, int col, double width, double height, int state) {
        super(root, row, col, width, height, state);
        if (state == DEAD) { setImageView(DEAD_IMAGE, width, height); }
        else if (state == ALIVE) { setImageView(ALIVE_IMAGE, width, height); }
    }

    @Override
    protected void updateImageView() {
        this.getRoot().getChildren().remove(this.getImageView());
        this.setImageView();
    }
}
