package simulation;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Map;

public abstract class Cell {
    private ImageView myImageView;
    private double myXPos;
    private double myYPos;
    private double myWidth;
    private double myHeight;
    private Group myRoot;
    private int myState;
    private int myNextState;

    public Cell(Group root, double x, double y, double width, double height, int state) {
        this.myRoot = root;
        this.myXPos = x;
        this.myYPos = y;
        this.myWidth = width;
        this.myHeight = height;
        this.myState = state;
    }

    /**
     * Set the ImageView of the Cell object for interaction with other JavaFx nodes.
     * @param imageFile: a String which is the name of the image file located in the resources root.
     */
    protected void setImageView(String imageFile) {
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(imageFile));
        this.myImageView = new ImageView(image);
        this.myImageView.setFitWidth(myWidth);
        this.myImageView.setFitHeight(myHeight);
        this.myImageView.setX(myXPos);
        this.myImageView.setY(myYPos);
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

    protected double getX() { return myXPos; }

    protected double getY() { return myYPos; }

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

