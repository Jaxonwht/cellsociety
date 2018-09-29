package simulation;

import javafx.scene.shape.Shape;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Haotian Wang, Julia Saveliff
 */
public class Grid {
    private Cell[][] myCells;
    private int myNumRow;
    private int myNumCol;
    private ReadXML myReader;
    private String myGridShape;
    private String myCellshape;

    public Grid(ReadXML reader, String gridShape, String cellShape) {
        myReader = reader;
        this.myNumRow = reader.getRow();
        this.myNumCol = reader.getColumn();
        this.myCells = new Cell[myNumRow][myNumCol];
        myGridShape = gridShape;
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
                    Class<?> clazz = Class.forName("simulation."+type+"Cell");
                    Constructor<?> constructor = clazz.getConstructor(int.class);
                    Object instance = constructor.newInstance(state);
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

    private List<Cell> callGetNeighborsByType(String neighborType, int row, int col) {
        try {
            Method method = getClass().getMethod(neighborType + myCellshape, int.class, int.class);
            Object ret = method.invoke(this, row, col);
            return (List<Cell>) ret;
        } catch (SecurityException e) {
            // TODO: exception handling
            System.out.println("Exception 1 caught: "+e.getMessage());
        } catch (NoSuchMethodException e) {
            System.out.println("Exception 1 caught: "+e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {

        }
        return null;
    }

    /**
     * A method used to find the neighbors of a cell in a grid.
     * @param row: the row number of the cell whose neighbors are to be found.
     * @param col: the column number of the cell whose neighbors are to be found.
     * @return A List of Cell that contains the neighbors of the cell passed as parameter.+     */
    public List<Cell> getAllNeighbors(int row, int col) {
        return callGetNeighborsByType("getAllNeighbors", row, col);
    }

    /**
     * @return neighbors a list that consists of direct neighbours (up, down,
     * left, right) that are within the grid.
     */
    public List<Cell> getDirectNeighbors(int row, int col) {
        return callGetNeighborsByType("getDirectNeighbors", row, col);
    }

    private List<Cell> getAllNeighborsSquare(int row, int col) {
        List<Cell> neighbors = new ArrayList<>();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (!(i == row && j == col || isOutOfBounds(i, j))) {
                    neighbors.add(myCells[i][j]);
                }
            }
        }
        return neighbors;
    }

    private List<Cell> getDirectNeighborsSquare(int row, int col) {

    }

    public int getNumRow() { return myNumRow; }

    public int getNumCol() { return myNumCol; }

    public Cell item(int i, int j) { return myCells[i][j]; }

    private boolean isOutOfBounds(int i, int j) { return i < 0 || i >= myNumRow || j < 0 || j >= myNumRow; }

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

}
