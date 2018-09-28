package simulation;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Haotian Wang
 */
public class SegregationCell extends Cell{
    public static final int TYPE_A = 0;
    public static final int TYPE_B = 1;
    public static final int EMPTY = 2;


    public SegregationCell(int state) {
        super(state);
    }
}
