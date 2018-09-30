package simulation;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
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
        aMap.put("Azure", Color.AZURE);
        aMap.put("Gold", Color.GOLD);
        aMap.put("Cyan", Color.CYAN);
        aMap.put("Gray", Color.GRAY);
        aMap.put("Lime", Color.LIME);
        aMap.put("Magenta", Color.MAGENTA);
        aMap.put("Pink", Color.PINK);
        aMap.put("Black", Color.BLACK);
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
        calculateLengths();
        initializeNodes();
    }

    protected abstract void calculateLengths();

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
                    if (!appearance.startsWith("Image")) {
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

    public void updateAppearance() {
        for (int i = 0; i < myGrid.getNumRow(); i++) {
            for (int j = 0 ; j < myGrid.getNumCol(); j++) {
                Node node = myNodes.get(i * myGrid.getNumCol() + j);
                int state = myGrid.item(i, j).getState();
                if (node instanceof Shape) {
                    ((Shape) node).setFill(intToPaintMap.get(state));
                }
                else if (node instanceof ImageView) {
                    ((ImageView) node).setImage(intToImageMap.get(state));
                }
            }
        }
    }

    protected abstract void addShape(int i, int j, int state);

    protected abstract void addImageView(int i, int j, int state);

    protected double getSimulationWidth() { return simulationWidth; }

    protected double getSimulationHeight() { return simulationWidth; }

    protected Map<Integer, Paint> getIntToPaintMap() { return intToPaintMap; }

    protected  Map<Integer, Image> getIntToImageMap() { return intToImageMap; }

    protected Grid getMyGrid() { return myGrid; }

    public List<Node> getMyNodes() { return myNodes; }
}
