package simulation;

import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Haotian Wang
 */
public abstract class Cell {
    public static final Map<Integer, Paint> STATE_TO_PAINT_MAP = new HashMap<>();
    public static final int UNINITIALIZED = -1;

    /*
    private Shape myShape;
    private double myXPos;
    private double myYPos;
    private double myWidth;
    private double myHeight;
    */
    private int rowIndex;
    private int colIndex;
    private int myState;
    private int myNextState;
    private double myOpacity; // NEW CODE

    public Cell(int state, int i, int j) {
        /*
        this.myXPos = x;
        this.myYPos = y;
        this.myWidth = width;
        this.myHeight = height;
        */
        this.rowIndex = i;
        this.colIndex = j;
        this.myState = state;
        this.myNextState = UNINITIALIZED;
        this.myOpacity = 1.0;
    }

    // Getter and setter methods for all instance variables
    protected int getNextState() {
        return myNextState;
    }

    protected void setNextState(int state) { myNextState = state; }

    protected int getState() { return myState; }

    protected void setState(int state) { myState = state; }

    protected void updateToNextState() { myState = myNextState; }

    public int getRowIndex() { return rowIndex; }

    public int getColIndex() { return colIndex; }

    protected double getOpacity() { return myOpacity; } // NEW CODE

    protected void setOpacity(double value) { myOpacity = value; } // NEW CODE
}

