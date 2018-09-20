package simulation;

public class Grid {
    private Cell[][] myCells;
    private int myNumRow;
    private int myNumCol;

    public Grid(double width, double height, int row, int col) {
        // change constructor parameters to xml reader and root
        myCells = new Cell[row][col];
        myNumRow = row;
        myNumCol = col;
    }

    public int getNumRow() { return myNumRow; }

    public int getNumCol() { return myNumCol; }

    public Cell item(int i, int j) {
        return myCells[i][j];
    }


}
