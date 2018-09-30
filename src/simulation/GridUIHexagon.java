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
        double width = getSimulationWidth() / getMyGrid().getNumCol();
        double height = getSimulationHeight() / getMyGrid().getNumRow();
        if (j % 2 == 0) {
            ((Polygon) temp).getPoints().addAll(new Double[] {
                    height / 2 / Math.sqrt(3) + j * (width + height / 2 / Math.sqrt(3)), i * height,
                    height / 2 / Math.sqrt(3) + j * (width + height / 2 / Math.sqrt(3)) + width, i * height,
                    height / 2 / Math.sqrt(3) + j * (width + height / 2 / Math.sqrt(3)) + width + height / 2 / Math.sqrt(3), i * height + height / 2,
                    height / 2 / Math.sqrt(3) + j * (width + height / 2 / Math.sqrt(3)) + width, i * height + height,
                    height / 2 / Math.sqrt(3) + j * (width + height / 2 / Math.sqrt(3)), i * height + height,
                    height / 2 / Math.sqrt(3) + j * (width + height / 2 / Math.sqrt(3)) - height / 2 / Math.sqrt(3), i * height + height / 2,
            });
        }
        else if (j % 2 == 1) {
            ((Polygon) temp).getPoints().addAll(new Double[] {
                    height / 2 / Math.sqrt(3) + j * (width + height / 2 / Math.sqrt(3)), i * height + height / 2,
                    height / 2 / Math.sqrt(3) + j * (width + height / 2 / Math.sqrt(3)) + width, i * height + height / 2,
                    height / 2 / Math.sqrt(3) + j * (width + height / 2 / Math.sqrt(3)) + width + height / 2 / Math.sqrt(3), i * height + height,
                    height / 2 / Math.sqrt(3) + j * (width + height / 2 / Math.sqrt(3)) + width, i * height + height * 3 / 2,
                    height / 2 / Math.sqrt(3) + j * (width + height / 2 / Math.sqrt(3)), i * height + height * 3 / 2,
                    height / 2 / Math.sqrt(3) + j * (width + height / 2 / Math.sqrt(3)) - height / 2 / Math.sqrt(3), i * height + height,
            });
        }
        ((Polygon) temp).setFill(getIntToPaintMap().get(state));
        getMyNodes().add(temp);
    }

    @Override
    protected void addImageView(int i, int j, int state) {
        Node temp = new ImageView(getIntToImageMap().get(state));
        double width = getSimulationWidth() / getMyGrid().getNumCol();
        double height = getSimulationHeight() / getMyGrid().getNumRow();
        ((ImageView) temp).setX(height / 2 / Math.sqrt(3) + j * (width + height / 2 / Math.sqrt(3)));
        if (j % 2 == 0) {
            ((ImageView) temp).setY(i * height);
        }
        else if (j % 2 == 1) {
            ((ImageView) temp).setY(i * height + height / 2);
        }
        ((ImageView) temp).setFitWidth(width + height / 2 / Math.sqrt(3));
        ((ImageView) temp).setFitHeight(height);
        getMyNodes().add(temp);
    }
}
