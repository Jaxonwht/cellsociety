package simulation;

import javafx.scene.Group;
import javafx.scene.shape.Shape;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Julia Saveliff
 */
public class Grid {
    private Cell[][] myCells;
    private int myNumRow;
    private int myNumCol;
    private double myWidth;
    private double myHeight;
    private ReadXML myReader;
    private Group myRoot;

    public Grid(Group root, ReadXML reader) {
        myRoot = root;
        myReader = reader;

        this.myNumRow = reader.getRow();
        this.myNumCol = reader.getColumn();
        this.myCells = new Cell[myNumRow][myNumCol];

        this.myWidth = reader.getWidth();
        this.myHeight = reader.getHeight();

    }

    public void populateCells() {
        String type = myReader.getName();

        double w = myWidth / myNumCol;
        double h = myHeight / myNumRow;
        int[][] states = myReader.getCellState();
        for (int i=0; i<myNumRow; i++) {
            for (int j=0; j<myNumCol; j++) {
                int state = states[i][j];
                // myCells[i][j] = new GameOfLifeCell(j*w, i*h, w, h, state);
                try {
                    Class<?> clazz = Class.forName("simulation."+type+"Cell");
                    Constructor<?> constructor = clazz.getConstructor(double.class, double.class, double.class, double.class, int.class);
                    Object instance = constructor.newInstance(j*w, i*h, w, h, state);
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

    public Collection<Shape> getAllShape() {
        Collection<Shape> allShape = new ArrayList<>();
        for (int i=0; i<myNumRow; i++) {
            for (int j=0; j<myNumCol; j++) {
                allShape.add(this.item(i,j).getShape());
            }
        }
        return allShape;
    }

    public int getNumRow() { return myNumRow; }

    public int getNumCol() { return myNumCol; }

    public Cell item(int i, int j) { return myCells[i][j]; }

    public boolean isOutOfBounds(int i, int j) { return i < 0 || i >= myNumRow || j < 0 || j >= myNumRow; }

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
