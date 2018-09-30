package simulation;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * @author Haotian Wang
 */
public class GridUISquare extends GridUI{
    private double cellWidth;
    private double cellHeight;

    public GridUISquare(Grid grid) {
        super(grid);
    }

    @Override
    protected void calculateLengths() {
        cellHeight = getSimulationHeight() / getMyGrid().getNumRow();
        cellWidth = getSimulationWidth() / getMyGrid().getNumCol();
    }

    @Override
    protected void addShape(int i, int j, int state) {
        Node temp = new Rectangle();
        ((Rectangle) temp).setWidth(cellWidth);
        ((Rectangle) temp).setHeight(cellHeight);
        ((Rectangle) temp).setX(j * cellWidth);
        ((Rectangle) temp).setY(i * cellHeight);
        ((Rectangle) temp).setFill(getIntToPaintMap().get(state));
        getMyNodes().add(temp);
    }

    @Override
    protected void addImageView(int i, int j, int state) {
        Node temp = new ImageView(getIntToImageMap().get(state));
        ((ImageView) temp).setFitWidth(cellWidth);
        ((ImageView) temp).setFitHeight(cellHeight);
        ((ImageView) temp).setX(j * cellWidth);
        ((ImageView) temp).setY(i * cellHeight);
        getMyNodes().add(temp);
    }
}
