package simulation;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;

/**
 * @author Haotian Wang
 */
public class GridUITriangle extends GridUI{
    public GridUITriangle(Grid grid) { super(grid); }

    @Override
    protected void addShape(int i, int j, int state) {
        Node temp = new Polygon();
        double sideLength = getSimulationWidth() / (getMyGrid().getNumCol() * 0.5 + 0.5);
        double height = getSimulationHeight() / getMyGrid().getNumRow();
        if (i % 2 == 0 && j % 2 == 0) {
            ((Polygon) temp).getPoints().addAll(new Double[] {
                    j / 2 * sideLength, i * height,
                    j / 2 * sideLength + sideLength, i * height,
                    j / 2 * sideLength + sideLength / 2, i * height + height,
            });
        }
        else if (i % 2 == 0 && j % 2 == 1) {
            ((Polygon) temp).getPoints().addAll(new Double[] {
                    0.5 * sideLength + j / 2 * sideLength, height + i * height,
                    1.5 * sideLength + j / 2 * sideLength, height + i * height,
                    sideLength + j / 2 * sideLength, i * height,
            });
        }
        else if (i % 2 == 1 && j % 2 == 0) {
            ((Polygon) temp).getPoints().addAll(new Double[] {
                    j / 2 * sideLength, i * height + height,
                    j / 2 * sideLength + sideLength, i * height + height,
                    j / 2 * sideLength + sideLength / 2, i * height,
            });
        }
        else if (i % 2 == 1 && j % 2 == 1) {
            ((Polygon) temp).getPoints().addAll(new Double[] {
                    0.5 * sideLength + j / 2 * sideLength, i * height,
                    1.5 * sideLength + j / 2 * sideLength, i * height,
                    sideLength + j / 2 * sideLength, i * height + height,
            });
        }
        ((Polygon) temp).setFill(getIntToPaintMap().get(state));
        getMyNodes().add(temp);
    }

    @Override
    protected void addImageView(int i, int j, int state) {
        Node temp = new ImageView(getIntToImageMap().get(state));
        double sideLength = getSimulationWidth() / (getMyGrid().getNumCol() / 2);
        double height = getSimulationHeight() / getMyGrid().getNumRow();
        ((ImageView) temp).setFitHeight(height);
        ((ImageView) temp).setFitWidth(sideLength);
        if ((i + j) % 2 == 0) {
            ((ImageView) temp).setX(j / 2 * sideLength + ((i % 2 == 1) ? 0.5 * sideLength : 0));
            ((ImageView) temp).setY(i * height);
        }
        else {
            ((ImageView) temp).setX(j / 2 * sideLength + ((i % 2 == 0) ? 0.5 * sideLength : 0));
            ((ImageView) temp).setY(i * height);
        }
        getMyNodes().add(temp);
    }
}
