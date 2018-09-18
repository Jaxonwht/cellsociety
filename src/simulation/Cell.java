package simulation;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.List;

public abstract class Cell {
    private ImageView myImageView;
    private double myX;
    private double myY;
    private Cell[][] myGrid;
    private double myWidth;
    private double myHeight;
    private Group myRoot;
    private int myState;
    private int myNextState;

    public abstract List<Cell> getNeighbors();

    public abstract void updateNextState();

    protected abstract void updateState();

    protected abstract void changeImageView();

    public abstract int getState();

    public abstract ImageView getImageView();

}
