package simulation;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.*;

/**
 * @author Haotian Wang
 * This class handles the change of appearances of nodes
 */
public class GridUI {
    private ResourceBundle myCellResources;
    private final static String DEFAULT_CELL_RESOURCE_FILE = "simulation/UI_graphic";
    private final static Map<String, Paint> stringToPaintMap;
    static {
        Map<String, Paint> aMap = new HashMap<>();
        aMap.put("White", Color.WHITE);
        aMap.put("Blue", Color.BLUE);
        aMap.put("Red", Color.RED);
        aMap.put("Green", Color.GREEN);
        aMap.put("Yellow", Color.YELLOW);
        stringToPaintMap = Collections.unmodifiableMap(aMap);
    }

    private String myCellShape;
    private Grid myGrid;
    private double simulationWidth;
    private double simulationHeight;
    private Map<Integer, Paint> intToPaintMap;
    private Map<Integer, Image> intToImageMap;
    private List<Node> myNodes;

    public GridUI(Grid grid, String cellShape) {
        myCellResources = ResourceBundle.getBundle(DEFAULT_CELL_RESOURCE_FILE);
        simulationHeight = Double.parseDouble(myCellResources.getString("HeightOfSimulation"));
        simulationWidth = Double.parseDouble(myCellResources.getString("WidthOfSimulation"));
        myNodes = new ArrayList<>();
        myGrid = grid;
        myCellShape = cellShape;
        intToImageMap = new HashMap<>();
        intToPaintMap = new HashMap<>();
        initializeNodes();
    }

    private void initializeNodes() {
        for (int i = 0; i < myGrid.getNumRow(); i++) {
            for (int j = 0 ; j < myGrid.getNumCol(); j++) {
                int state = myGrid.item(i, j).getState();
                if (intToPaintMap.containsKey(state)) {
                    addShape(i, j, state);
                }
                else if (intToImageMap.containsKey(state)) {
                    addImageView(i, j, state);
                }
                else {
                    // TODO: error handling
                    String appearance = myCellResources.getString("State" + state);
                    if (!appearance.startsWith("Image") && stringToPaintMap.containsKey(appearance)) {
                        intToPaintMap.put(state, stringToPaintMap.get(appearance));
                        addShape(i, j, state);
                    }
                    else {
                        intToImageMap.put(state, new Image(getClass().getClassLoader().getResourceAsStream(appearance.split("/")[1])));
                        addImage(i, j, state);
                    }
                }
            }
        }
    }

    private void addShape(int i, int j, int state) {
        if (myCellShape.equals("Square")) {
            Node temp = new Rectangle();
            ((Rectangle) temp).setWidth(simulationWidth / myGrid.getNumCol());
            ((Rectangle) temp).setHeight(simulationHeight / myGrid.getNumRow());
            ((Rectangle) temp).setX((j - 1) * ((Rectangle) temp).getWidth());
            ((Rectangle) temp).setY((i - 1) * ((Rectangle) temp).getHeight());
            ((Rectangle) temp).setFill(intToPaintMap.get(state));
            myNodes.add(temp);
        }
        else if (myCellShape.equals("Triangle")) {
            Node temp = new Polygon();
            double sideLength = simul
            if ((i + j) % 2 == 0) {

            }
        }
    }
}
