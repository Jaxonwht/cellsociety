package simulation;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Haotian Wang
 */
public abstract class Cell {
    public static final Map<Integer, Paint> STATE_TO_PAINT_MAP = new HashMap<>();
    public static final int UNINITIALIZED = -1;

    private Shape myShape;
    private double myXPos;
    private double myYPos;
    private double myWidth;
    private double myHeight;
    private int myState;
    private int myNextState;

    public Cell(double x, double y, double width, double height, int state) {
        this.myXPos = x;
        this.myYPos = y;
        this.myWidth = width;
        this.myHeight = height;
        this.myState = state;
        this.myNextState = UNINITIALIZED;
    }

    /**
     * Set the Rectangle of the Cell object for interaction with other JavaFx nodes.
     */
    protected void setRectangle(Paint paint) {
        this.myShape = new Rectangle(this.myXPos, this.myYPos, this.myWidth, this.myHeight);
        this.myShape.setFill(paint);
    }

    /**
     * Set the Circle of the Cell object for interaction with other JavaFx nodes.
     */
    protected void setCircle(Paint paint) {
        this.myShape = new Circle(this.myXPos + this.myWidth / 2, this.myYPos + this.myHeight / 2, this.myWidth / 2);
        this.myShape.setFill(paint);
    }

    /**
     * Update the color of the shape of the cell to its corresponding next state.
     */
    protected void updateShape(Map<Integer, Paint> map) {
        if (this.getState() != this.getNextState()) {
            this.getShape().setFill(map.get(this.getNextState()));
        }
    }

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

    protected Shape getShape() { return myShape; }

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
}

