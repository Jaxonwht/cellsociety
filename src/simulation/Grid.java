package simulation;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;

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
                myCells[i][j] = new GameOfLifeCell(myRoot, j*w, i*h, w, h, state);
                System.out.println("com.simulation." + type+"Cell");
//                try {
//                    Class<?> clazz = Class.forName("com.simulation." + type+"Cell");
//                    Constructor<?> constructor = clazz.getConstructor(Group.class, double.class, double.class, double.class, double.class, int.class);
//                    Object instance = constructor.newInstance(myRoot, j*w, i*h, w, h, state);
//                    Cell temp = instance;
//                    myCells[i][j] = instance;
//
//                } catch (Exception e) {
//                    // TODO: catch exception
//                }
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

}
