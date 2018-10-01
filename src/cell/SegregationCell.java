package cell;

import cell.Cell;

/**
 * @author Haotian Wang
 */
public class SegregationCell extends Cell {
    public static final int TYPE_A = 0;
    public static final int TYPE_B = 1;
    public static final int EMPTY = 2;


    public SegregationCell(int state, int i, int j) {
        super(state, i, j);
    }
}
