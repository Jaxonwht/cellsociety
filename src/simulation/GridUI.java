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
public abstract class GridUI {
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

    private Grid myGrid;
    private double simulationWidth;
    private double simulationHeight;
    private Map<Integer, Paint> intToPaintMap;
    private Map<Integer, Image> intToImageMap;
    private List<Node> myNodes;

    public GridUI(Grid grid) {
        myCellResources = ResourceBundle.getBundle(DEFAULT_CELL_RESOURCE_FILE);
        simulationHeight = Double.parseDouble(myCellResources.getString("HeightOfSimulation"));
        simulationWidth = Double.parseDouble(myCellResources.getString("WidthOfSimulation"));
        myNodes = new ArrayList<>();
        myGrid = grid;
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
                        addImageView(i, j, state);
                    }
                }
            }
        }
    }

    protected abstract void addShape(int i, int j, int state);

    protected abstract void addImageView(int i, int j, int state);

    private Node createRectangle(int i, int j, int state) {
        Node temp = new Rectangle();
        ((Rectangle) temp).setWidth(simulationWidth / myGrid.getNumCol());
        ((Rectangle) temp).setHeight(simulationHeight / myGrid.getNumRow());
        ((Rectangle) temp).setX(j * ((Rectangle) temp).getWidth());
        ((Rectangle) temp).setY(i * ((Rectangle) temp).getHeight());
        ((Rectangle) temp).setFill(intToPaintMap.get(state));
        return temp;
    }

    private Node createTriangle(int i, int j, int state) {
        Node temp = new Polygon();
        double sideLength = simulationWidth / (myGrid.getNumCol() / 2);
        double height = simulationHeight / myGrid.getNumRow();
        if ((i + j) % 2 == 0) {
            ((Polygon) temp).getPoints().addAll(new Double[] {
                    j / 2 * sideLength + ((i % 2 == 1) ? 0.5 * sideLength : 0), i * height,
                    j / 2 * sideLength + ((i % 2 == 1) ? 0.5 * sideLength : 0) + sideLength, i * height,
                    j / 2 * sideLength + ((i % 2 == 1) ? 0.5 * sideLength : 0) + sideLength / 2, i * height + height,
            });
        }
        else {
            ((Polygon) temp).getPoints().addAll(new Double[] {
                    j / 2 * sideLength + ((i % 2 == 0) ? 0.5 * sideLength : 0), i * height + height,
                    j / 2 * sideLength + ((i % 2 == 0) ? 0.5 * sideLength : 0) + sideLength, i * height + height,
                    j / 2 * sideLength + ((i % 2 == 0) ? 0.5 * sideLength : 0) + sideLength / 2, i * height,
            });
        }
        ((Polygon) temp).setFill(intToPaintMap.get(state));
        return temp;
    }

    protected double getSimulationWidth() { return simulationWidth; }

    protected double getSimulationHeight() { return simulationWidth; }

    protected Map<Integer, Paint> getIntToPaintMap() { return intToPaintMap; }

    protected  Map<Integer, Image> getIntToImageMap() { return intToImageMap; }

    protected Grid getMyGrid() { return myGrid; }

    protected List<Node> getMyNodes() { return myNodes; }
}
