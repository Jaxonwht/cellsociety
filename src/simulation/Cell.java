package simulation;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.List;

public abstract class Cell {
    private ImageView myImageView;
    private Grid myGrid;
    private int myIndexX;
    private int myIndexY;
    private double myWidth;
    private double myHeight;
    private Group myRoot;
    private int myState;
    private int myNextState;

    protected abstract List<Cell> getNeighbors();

    protected abstract void determineNextState();

    protected abstract void changeImageView();

    // Getter and setter methods for all instance variables
    protected int getNextState() {
        return myNextState;
    }

    protected void setNextState(int state) { myNextState = state; }

    protected int getState() { return myState; }

    protected void setState(int state) { myState = state; }

    protected void updateToNextState() { myState = myNextState; }

    protected int getIndexX() { return myIndexX; }

    protected void setIndexX(int index) { myIndexX = index; }

    protected int getIndexY() { return myIndexY; }

    protected void setIndexY(int index) { myIndexY = index; }

    protected ImageView getImageView() {
        return myImageView;
    }

    protected void setImageView(ImageView image) {
        myImageView = image;
    }

    protected double getWidth() {
        return myWidth;
    }

    protected void setWidth(double width) {
        myWidth = width;
    }

    protected double getHeight() {
        return myHeight;
    }

    protected void setHeight(double height) {
        myHeight = height;
    }

    protected Grid getGrid() {
        return myGrid;
    }

    protected void setGrid(Grid grid) {
        myGrid = grid;
    }

    protected Group getRoot() {
        return myRoot;
    }

    protected void setRoot(Group root) {
        myRoot = root;
    }

}
