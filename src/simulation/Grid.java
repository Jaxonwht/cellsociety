package simulation;

import javafx.scene.Group;

public class Grid {
    private Cell[][] myCells;
    private int myNumRow;
    private int myNumCol;
    private double myWidth;
    private double myHeight;

    public Grid(Group root, ReadXML reader) {
        reader.readGrid();

        myNumRow = reader.getRow();
        myNumCol = reader.getColumn();
        myCells = new Cell[myNumRow][myNumCol];

        myWidth = reader.getWidth();
        myHeight = reader.getHeight();

    }

    public int getNumRow() { return myNumRow; }

    public int getNumCol() { return myNumCol; }

    public Cell item(int i, int j) {
        return myCells[i][j];
    }


}
