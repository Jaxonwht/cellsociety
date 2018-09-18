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

    public abstract void determineNextState();
    
    protected abstract void changeImageView();

    // Getter and setter methods for all instance variables
    public int getNextState() {
        return myNextState;
    }

    public void setNextState(int state) {
        myNextState = state;
    }

    public int getState() {
        return myState;
    }

    public void setState(int state) {
        myState = state;
    }

    public ImageView getImageView() {
        return myImageView;
    }

    public void setImageView(ImageView image) {
        myImageView = image;
    }

    public double getWidth() {
        return myWidth;
    }

    public void setWidth(double width) {
        myWidth = width;
    }

    public double getHeight() {
        return myHeight;
    }

    public void setHeight(double height) {
        myHeight = height;
    }

    public double getX() {
        return myX;
    }

    public void setX(double x) {
        myX = x;
    }

    public double getY() {
        return myY;
    }

    public void setY(double y) {
        myY = y;
    }

    public Cell[][] getGrid() {
        return myGrid;
    }

    public void setGrid(Cell[][] grid) {
        myGrid = grid;
    }

    public Group getRoot() {
        return myRoot;
    }

    public void setRoot(Group root) {
        myRoot = root;
    }

}
