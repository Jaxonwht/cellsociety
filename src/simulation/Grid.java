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

        this.myNumRow = reader.getRow();
        this.myNumCol = reader.getColumn();
        this.myCells = new Cell[myNumRow][myNumCol];

        this.myWidth = reader.getWidth();
        this.myHeight = reader.getHeight();

    }

    public int getNumRow() { return myNumRow; }

    public int getNumCol() { return myNumCol; }

    public Cell item(int i, int j) {
        return myCells[i][j];
    }

    public void updateCells() {
        for (int i = 0; i < myNumRow; i++) {
            for (int j = 0; j < myNumCol; j++) {
                myCells[i][j].updateToNextState();
            }
        }
    }

    public boolean isOutOfBounds(int i, int j) {
        return i < 0 || i > myNumRow || j < 0 || j > myNumRow;
    }
}
