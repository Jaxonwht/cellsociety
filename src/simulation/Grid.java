package simulation;

import cell.Cell;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Haotian Wang, Julia Saveliff
 */
public class Grid {
    private static final int[][] SQUARE_DIRECT = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private static final int[][] SQUARE_ALL = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1,-1}};
    private static final int[][] HEXAGON_EVEN = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}};
    private static final int[][] HEXAGON_ODD = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {1, -1}, {1, 1}};
    private static final int[][] TRIANGLE_DIRECT_EVEN = new int[][] {{0, -1}, {0, 1}, {-1, 0}};
    private static final int[][] TRIANGLE_DIRECT_ODD = new int[][] {{0, -1}, {0, 1}, {1, 0}};
    private static final int[][] TRIANGLE_ALL_EVEN = new int[][] {{-1, -1}, {-1, 0}, {-1, 1}, {0, -2}, {0, -1}, {0, 1}, {0, 2}, {1, -2}, {1, -1}, {1, 0}, {1, 1}, {1, 2}};
    private static final int[][] TRIANGLE_ALL_ODD = new int[][] {{-1, -2}, {-1, -1}, {-1, 0}, {-1, 1}, {-1, 2}, {0, -2}, {0, -1}, {0, 1}, {0, 2}, {1, -1}, {1, 0}, {1, 1}};

    private Cell[][] myCells;
    private int myNumRow;
    private int myNumCol;
    private ReadXML myReader;
    private String myGridType;
    private String myCellshape;

    public Grid(ReadXML reader, String gridType, String cellShape) {
        myReader = reader;
        this.myNumRow = reader.getRow();
        this.myNumCol = reader.getColumn();
        this.myCells = new Cell[myNumRow][myNumCol];
        myGridType = gridType;
        myCellshape = cellShape;
        populateCells();
    }

    public void populateCells() {
        String type = myReader.getName();
        int[][] states = myReader.getCellState();
        for (int i=0; i<myNumRow; i++) {
            for (int j=0; j<myNumCol; j++) {
                int state = states[i][j];
                try {
                    Class<?> clazz = Class.forName("cell."+type+"Cell");
                    Constructor<?> constructor = clazz.getConstructor(int.class, int.class, int.class);
                    Object instance = constructor.newInstance(state, i, j);
                    myCells[i][j] = (Cell) instance;
                } catch (ClassNotFoundException e){
                    // TODO: catch exception
                    System.out.println("Exception 1 caught: "+e.getMessage());
                } catch (NoSuchMethodException ee){
                    System.out.println("Exception 2 caught: "+ee.getMessage());
                } catch (InstantiationException eee){
                    System.out.println("Exception 3 caught: "+eee.getMessage());
                } catch (IllegalAccessException eeee){
                    System.out.println("Exception 4 caught: "+eeee.getMessage());
                } catch (InvocationTargetException eeeee){
                    System.out.println("Exception 5 caught: "+eeeee.getMessage());
                }

            }
        }
    }

    /**
     * A method used to find the neighbors of a cell in a grid.
     * @param row: the row number of the cell whose neighbors are to be found.
     * @param col: the column number of the cell whose neighbors are to be found.
     * @return A List of Cell that contains the neighbors of the cell passed as parameter.+     */
    public List<Cell> getAllNeighbors(int row, int col) {
        return callGetNeighborsByType("All", row, col);
    }

    /**
     * @return neighbors a list that consists of direct neighbours (up, down,
     * left, right) that are within the grid.
     */
    public List<Cell> getDirectNeighbors(int row, int col) {
        return callGetNeighborsByType("Direct", row, col);
    }

    private List<Cell> callGetNeighborsByType(String neighborType, int row, int col) {
        try {
            Method method = getClass().getMethod("get" + neighborType +"Neighbors" + myCellshape, int.class, int.class);
            Object ret = method.invoke(this, row, col);
            return (List<Cell>) ret;
        } catch (SecurityException e) {
            // TODO: exception handling
            System.out.println("Exception 1 caught: "+e.getMessage());
        } catch (NoSuchMethodException e) {
            System.out.println("Exception 2 caught: "+e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {

        }
        return null;
    }

    private List<Cell> getNeighborsByDirection(int row, int col, int[][] Direction) {
        List<Cell> neighbors = new ArrayList<>();
        for (int[] vector : Direction) {
            int i = vector[0] + row;
            int j = vector[1] + col;
            if (myGridType.equals("Finite")) {
                if (!isOutOfBounds(i, j)) { neighbors.add(myCells[i][j]); }
            }
            else if (myGridType.equals("Toroidal")) {
                int[] wrapper = wrapAround(i, j);
                neighbors.add(myCells[wrapper[0]][wrapper[1]]);
            }
        }
        return neighbors;
    }

    public List<Cell> getAllNeighborsSquare(int row, int col) {
        return getNeighborsByDirection(row, col, SQUARE_ALL);
    }

    public List<Cell> getDirectNeighborsSquare(int row, int col) {
        return getNeighborsByDirection(row, col, SQUARE_DIRECT);
    }

    public List<Cell> getAllNeighborsHexagon(int row, int col) {
        if (col % 2 == 0) { return getNeighborsByDirection(row, col, HEXAGON_EVEN); }
        else {return getNeighborsByDirection(row, col, HEXAGON_ODD); }
    }

    public List<Cell> getDirectNeighborsHexagon(int row, int col) {
        return getAllNeighborsHexagon(row, col);
    }

    public List<Cell> getAllNeighborsTriangle(int row, int col) {
        if ((row + col) % 2 == 0) { return getNeighborsByDirection(row, col, TRIANGLE_ALL_EVEN); }
        else { return getNeighborsByDirection(row, col, TRIANGLE_ALL_ODD); }
    }

    public List<Cell> getDirectNeighborsTriangle(int row, int col) {
        if ((row + col) % 2 == 0) { return getNeighborsByDirection(row, col, TRIANGLE_DIRECT_EVEN); }
        else { return getNeighborsByDirection(row, col, TRIANGLE_DIRECT_ODD); }
    }


    public int getNumRow() { return myNumRow; }

    public int getNumCol() { return myNumCol; }

    public Cell item(int i, int j) { return myCells[i][j]; }

    private boolean isOutOfBounds(int i, int j) { return i < 0 || i >= myNumRow || j < 0 || j >= myNumRow; }

    private int[] wrapAround(int i, int j) {
        if (!isOutOfBounds(i, j)) { return new int[]{i, j}; }
        else {
            int[] ans = new int[2];
            if (i < 0) { ans[0] = myNumRow + i; }
            else if (i >= myNumRow) { ans[0] = i - myNumRow; }
            if (j < 0) { ans[1] = myNumCol + j; }
            else if (j >= myNumCol) { ans[1] = j - myNumCol; }
            return ans;
        }
    }

    public void printAllStates() {
        for (int i=0; i<myNumRow; i++) {
            for (int j = 0; j < myNumCol; j++) {
                System.out.print(this.item(i, j).getState());
            }
        }
        System.out.print("\n");
    }

    public void printAllNextStates() {
        for (int i=0; i<myNumRow; i++) {
            for (int j = 0; j < myNumCol; j++) {
                System.out.print(this.item(i, j).getNextState());
            }
        }
        System.out.print("\n");
    }

    public int getStateCount(int state) {
        int count=0;
        for (int i=0; i<myNumRow; i++) {
            for (int j = 0; j < myNumCol; j++) {
                if (myCells[i][j].getState() == state) {
                    count += 1;
                }
            }
        }
        return count;
    }

}


