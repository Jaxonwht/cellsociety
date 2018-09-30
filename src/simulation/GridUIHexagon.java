package simulation;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;

/**
 * @author Haotian Wang
 */
public class GridUIHexagon extends GridUI {
    public GridUIHexagon(Grid grid) { super(grid); }

    @Override
    protected void addShape(int i, int j, int state) {
        Node temp = new Polygon();
        double height = getSimulationHeight() / (getMyGrid().getNumRow() + 1);
        double edge = height / 2 / Math.sqrt(3);
        double width = getSimulationWidth() / getMyGrid().getNumCol() - edge;
        if (j % 2 == 0) {
            ((Polygon) temp).getPoints().addAll(new Double[] {
                    edge + j * (width + edge), i * height,
                    edge + j * (width + edge) + width, i * height,
                    edge + j * (width + edge) + width + edge, i * height + height / 2,
                    edge + j * (width + edge) + width, i * height + height,
                    edge + j * (width + edge), i * height + height,
                    edge + j * (width + edge) - edge, i * height + height / 2,
            });
        }
        else if (j % 2 == 1) {
            ((Polygon) temp).getPoints().addAll(new Double[] {
                    edge + j * (width + edge), i * height + height / 2,
                    edge + j * (width + edge) + width, i * height + height / 2,
                    edge + j * (width + edge) + width + edge, i * height + height,
                    edge + j * (width + edge) + width, i * height + height * 3 / 2,
                    edge + j * (width + edge), i * height + height * 3 / 2,
                    edge + j * (width + edge) - edge, i * height + height,
            });
        }
        ((Polygon) temp).setFill(getIntToPaintMap().get(state));
        getMyNodes().add(temp);
    }

    @Override
    protected void addImageView(int i, int j, int state) {
        Node temp = new ImageView(getIntToImageMap().get(state));
        double height = getSimulationHeight() / getMyGrid().getNumRow();
        double edge = height / 2 / Math.sqrt(3);
        double width = getSimulationWidth() / getMyGrid().getNumCol() - edge;
        ((ImageView) temp).setX(edge + j * (width + edge));
        if (j % 2 == 0) {
            ((ImageView) temp).setY(i * height);
        }
        else if (j % 2 == 1) {
            ((ImageView) temp).setY(i * height + height / 2);
        }
        ((ImageView) temp).setFitWidth(width + edge);
        ((ImageView) temp).setFitHeight(height);
        getMyNodes().add(temp);
    }
}
