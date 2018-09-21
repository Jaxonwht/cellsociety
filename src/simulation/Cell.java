package simulation;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Map;

public abstract class Cell {
    private ImageView myImageView;
    private int myRow;
    private int myCol;
    private double myWidth;
    private double myHeight;
    private Group myRoot;
    private int myState;
    private int myNextState;

    public Cell(Group root, int row, int col, double width, double height, int state) {
        this.myRoot = root;
        this.myRow = row;
        this.myCol = col;
        this.myWidth = width;
        this.myHeight = height;
        this.myState = state;
        // TODO: decide if to add the ImageView node in Cell or in UIManager
    }

    /**
     * Set the ImageView of the Cell object for interaction with other JavaFx nodes.
     * @param imageFile: a String which is the name of the image file located in the resources root.
     * @param width: width of the cell object.
     * @param height: height of the cell object.
     */
    protected void setImageView(String imageFile, double width, double height) {
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(imageFile));
        this.myImageView = new ImageView(image);
        this.myImageView.setFitWidth(width);
        this.myImageView.setFitHeight(height);
        // TODO: determine the x and y positions of the cells in the plane.
    }

    protected abstract void updateImageView();

    // Getter and setter methods for all instance variables
    protected int getNextState() {
        return myNextState;
    }

    protected void setNextState(int state) { myNextState = state; }

    protected int getState() { return myState; }

    protected void setState(int state) { myState = state; }

    protected void updateToNextState() { myState = myNextState; }

    protected int getRow() { return myRow; }

    protected void setRow(int index) { myRow = index; }

    protected int getCol() { return myCol; }

    protected void setCol(int index) { myCol = index; }

    protected ImageView getImageView() {
        return myImageView;
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

    protected Group getRoot() {
        return myRoot;
    }

    protected void setRoot(Group root) {
        myRoot = root;
    }
}

