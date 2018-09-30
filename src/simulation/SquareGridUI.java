package simulation;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * @author Haotian Wang
 */
public class SquareGridUI extends GridUI{
    public SquareGridUI (Grid grid) { super(grid); }

    @Override
    protected void addShape(int i, int j, int state) {
        Node temp = new Rectangle();
        ((Rectangle) temp).setWidth(getSimulationWidth() / getMyGrid().getNumCol());
        ((Rectangle) temp).setHeight(getSimulationHeight() / getMyGrid().getNumRow());
        ((Rectangle) temp).setX(j * ((Rectangle) temp).getWidth());
        ((Rectangle) temp).setY(i * ((Rectangle) temp).getHeight());
        ((Rectangle) temp).setFill(getIntToPaintMap().get(state));
        getMyNodes().add(temp);
    }

    @Override
    protected void addImageView(int i, int j, int state) {
        Node temp = new ImageView(getIntToImageMap().get(state));
        ((ImageView) temp).setFitWidth(getSimulationWidth() / getMyGrid().getNumCol());
        ((ImageView) temp).setFitHeight(getSimulationHeight() / getMyGrid().getNumRow());
        ((ImageView) temp).setX(j * ((Rectangle) temp).getWidth());
        ((ImageView) temp).setY(i * ((Rectangle) temp).getHeight());
        getMyNodes().add(temp);
    }
}
